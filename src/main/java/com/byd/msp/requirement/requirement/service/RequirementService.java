package com.byd.msp.requirement.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.requirement.dto.RequirementAddDTO;
import com.byd.msp.requirement.requirement.dto.RequirementQueryDTO;
import com.byd.msp.requirement.requirement.dto.RequirementUpdateDTO;
import com.byd.msp.requirement.requirement.entity.Requirement;
import com.byd.msp.requirement.requirement.vo.RequirementVO;

import java.util.Map;

public interface RequirementService extends IService<Requirement> {

    PageResult<RequirementVO> query(RequirementQueryDTO dto);

    RequirementVO add(RequirementAddDTO dto);

    void update(RequirementUpdateDTO dto);

    void delete(Integer id);

    RequirementVO detail(Integer id);

    /**
     * 触发需求流程事件
     * @param requirementNo 需求单号
     * @param event 事件名称（如 submit/approve/reject）
     */
    void sendFlowEvent(String requirementNo, String event);

    /**
     * 获取需求流程状态
     * @param requirementNo 需求单号
     * @return 包含当前状态、可用操作等信息
     */
    Map<String, Object> getFlowState(String requirementNo);
}
