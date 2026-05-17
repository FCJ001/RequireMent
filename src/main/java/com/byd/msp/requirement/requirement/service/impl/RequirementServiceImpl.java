package com.byd.msp.requirement.requirement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.requirement.dto.RequirementAddDTO;
import com.byd.msp.requirement.requirement.dto.RequirementQueryDTO;
import com.byd.msp.requirement.requirement.dto.RequirementUpdateDTO;
import com.byd.msp.requirement.requirement.entity.Requirement;
import com.byd.msp.requirement.requirement.mapper.RequirementMapper;
import com.byd.msp.requirement.requirement.service.RequirementLogService;
import com.byd.msp.requirement.requirement.service.RequirementService;
import com.byd.msp.requirement.requirement.vo.RequirementVO;
import com.byd.msp.requirement.statemachine.NodeInfo;
import com.byd.msp.requirement.statemachine.State;
import com.byd.msp.requirement.statemachine.StateMachine;
import com.byd.msp.requirement.statemachine.StateMachineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequirementServiceImpl extends ServiceImpl<RequirementMapper, Requirement> implements RequirementService {

    @Resource
    private StateMachineFactory stateMachineFactory;

    @Resource
    private RequirementLogService requirementLogService;

    @Override
    public PageResult<RequirementVO> query(RequirementQueryDTO dto) {
        LambdaQueryWrapper<Requirement> wrapper = new LambdaQueryWrapper<Requirement>()
                .eq(StringUtils.isNotBlank(dto.getRequirementNo()), Requirement::getRequirementNo, dto.getRequirementNo())
                .eq(StringUtils.isNotBlank(dto.getRequirementType()), Requirement::getRequirementType, dto.getRequirementType())
                .eq(StringUtils.isNotBlank(dto.getStatus()), Requirement::getStatus, dto.getStatus())
                .eq(StringUtils.isNotBlank(dto.getPriority()), Requirement::getPriority, dto.getPriority())
                .orderByDesc(Requirement::getCreatedTime);

        Page<Requirement> page = this.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<RequirementVO> voList = page.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, RequirementVO.class))
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public RequirementVO add(RequirementAddDTO dto) {
        Requirement requirement = BeanUtil.copyProperties(dto, Requirement.class);
        requirement.setStatus("草稿");
        this.save(requirement);

        // 启动需求流程（领域 = requirement_pool，业务ID = 需求单号）
        try {
            StateMachine machine = stateMachineFactory.create("requirement_pool", dto.getRequirementNo());
            requirement.setFlowInstanceId(machine.getId());
            requirement.setCurrentNode("draft");
            this.updateById(requirement);

            // 记录流程启动日志
            requirementLogService.log(dto.getRequirementNo(), "流程启动",
                    "需求创建并启动流程", "system", "系统");
        } catch (Exception e) {
            // 流程启动失败，删除已保存的需求记录，保证数据一致性
            this.removeById(requirement.getId());
            requirementLogService.log(dto.getRequirementNo(), "流程启动失败",
                    e.getMessage(), "system", "系统");
            throw new RuntimeException("需求创建失败: 流程启动异常 - " + e.getMessage(), e);
        }

        return BeanUtil.copyProperties(requirement, RequirementVO.class);
    }

    @Override
    public void update(RequirementUpdateDTO dto) {
        Requirement requirement = BeanUtil.copyProperties(dto, Requirement.class);
        this.updateById(requirement);
    }

    @Override
    public void delete(Integer id) {
        this.removeById(id);
    }

    @Override
    public RequirementVO detail(Integer id) {
        Requirement requirement = this.getById(id);
        if (requirement == null) {
            return null;
        }
        return BeanUtil.copyProperties(requirement, RequirementVO.class);
    }

    @Override
    public void sendFlowEvent(String requirementNo, String event) {
        log.info("sendFlowEvent start: requirementNo={}, event={}", requirementNo, event);

        Requirement requirement = this.getOne(new LambdaQueryWrapper<Requirement>()
                .eq(Requirement::getRequirementNo, requirementNo));
        if (requirement == null) {
            throw new RuntimeException("需求不存在: " + requirementNo);
        }
        log.info("requirement found: id={}, status={}, currentNode={}, flowInstanceId={}",
                requirement.getId(), requirement.getStatus(), requirement.getCurrentNode(), requirement.getFlowInstanceId());

        // 映射事件到状态
        Map<String, String> eventStatusMap = new HashMap<>();
        eventStatusMap.put("submit", "待审批");
        eventStatusMap.put("approve", "评审中");
        eventStatusMap.put("reject", "草稿");
        eventStatusMap.put("review_pass", "验收中");
        eventStatusMap.put("review_reject", "草稿");
        eventStatusMap.put("accept", "已完成");

        try {
            log.info("Looking up Flowable process for businessId={}", requirementNo);
            StateMachine machine = stateMachineFactory.get(requirementNo);
            if (machine == null) {
                log.warn("Flowable process not found for {}, trying to create one", requirementNo);
                // 流程不存在，尝试重新创建（兼容旧数据）
                machine = stateMachineFactory.create("requirement_pool", requirementNo);
                requirement.setFlowInstanceId(machine.getId());
                requirement.setCurrentNode("draft");
                this.updateById(requirement);
                log.info("Flowable process created: machineId={}", machine.getId());
            }

            log.info("Sending event {} to machine {}", event, machine.getId());
            machine.sendEvent(event);
            log.info("Event {} sent successfully", event);

            // 更新需求状态和当前节点
            State currentState = machine.getState();
            log.info("Current state after event: id={}, name={}", currentState.getId(), currentState.getName());
            String newStatus = eventStatusMap.getOrDefault(event, currentState.getName());
            requirement.setStatus(newStatus);
            requirement.setCurrentNode(currentState.getId());
            this.updateById(requirement);

            // 记录操作日志
            requirementLogService.log(requirementNo, event, "流程事件触发: " + event,
                    "system", "系统");

        } catch (Exception e) {
            log.error("sendFlowEvent failed: requirementNo={}, event={}, error={}",
                    requirementNo, event, e.getMessage(), e);
            try {
                requirementLogService.log(requirementNo, "流程操作失败",
                        event + ": " + e.getMessage(), "system", "系统");
            } catch (Exception logEx) {
                log.error("Failed to log error: {}", logEx.getMessage());
            }
            throw new RuntimeException("流程操作失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getFlowState(String requirementNo) {
        Requirement requirement = this.getOne(new LambdaQueryWrapper<Requirement>()
                .eq(Requirement::getRequirementNo, requirementNo));
        if (requirement == null) {
            throw new RuntimeException("需求不存在: " + requirementNo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("requirementNo", requirementNo);
        result.put("status", requirement.getStatus());
        result.put("currentNode", requirement.getCurrentNode());
        result.put("flowInstanceId", requirement.getFlowInstanceId());

        try {
            StateMachine machine = stateMachineFactory.get(requirementNo);
            if (machine != null) {
                State currentState = machine.getState();
                result.put("stateId", currentState.getId());
                result.put("stateName", currentState.getName());

                List<NodeInfo> nodes = machine.getNodeIds();
                // 找到当前节点和可触发的下一个节点
                for (NodeInfo node : nodes) {
                    if (node.getNodeId().equals(currentState.getId()) && node.getTransitions() != null) {
                        List<String> availableEvents = node.getTransitions().stream()
                                .map(NodeInfo.Transition::getEvent)
                                .filter(e -> e != null)
                                .collect(Collectors.toList());
                        result.put("availableEvents", availableEvents);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }

        return result;
    }
}
