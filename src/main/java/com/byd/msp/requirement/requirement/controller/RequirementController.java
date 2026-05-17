package com.byd.msp.requirement.requirement.controller;

import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.requirement.dto.FlowEventDTO;
import com.byd.msp.requirement.requirement.dto.RequirementAddDTO;
import com.byd.msp.requirement.requirement.dto.RequirementQueryDTO;
import com.byd.msp.requirement.requirement.dto.RequirementUpdateDTO;
import com.byd.msp.requirement.requirement.entity.RequirementLog;
import com.byd.msp.requirement.requirement.service.RequirementLogService;
import com.byd.msp.requirement.requirement.service.RequirementService;
import com.byd.msp.requirement.requirement.vo.RequirementVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requirement")
@Api(tags = "需求管理")
public class RequirementController {

    @Resource
    private RequirementService requirementService;

    @Resource
    private RequirementLogService requirementLogService;

    // ========== 通用 CRUD（不区分流程） ==========

    @PostMapping("/query")
    @ApiOperation("分页查询需求")
    public Result<PageResult<RequirementVO>> query(@RequestBody RequirementQueryDTO dto) {
        return Result.success(requirementService.query(dto));
    }

    @PutMapping("/update")
    @ApiOperation("更新需求")
    public Result<Void> update(@RequestBody RequirementUpdateDTO dto) {
        requirementService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除需求")
    public Result<Void> delete(@PathVariable Integer id) {
        requirementService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("需求详情")
    public Result<RequirementVO> detail(@PathVariable Integer id) {
        return Result.success(requirementService.detail(id));
    }

    @GetMapping("/logs")
    @ApiOperation("需求日志列表")
    public Result<List<RequirementLog>> logs(@RequestParam String requirementNo) {
        return Result.success(requirementLogService.listByRequirementNo(requirementNo));
    }

    // ========== 流程操作（按 processKey 区分） ==========

    @PostMapping("/{processKey}/add")
    @ApiOperation("新增需求（指定流程: requirement_pool / FL25103101）")
    public Result<RequirementVO> add(@PathVariable String processKey, @RequestBody RequirementAddDTO dto) {
        try {
            return Result.success(requirementService.add(processKey, dto));
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/{processKey}/flow/send")
    @ApiOperation("触发需求流程事件")
    public Result<String> sendFlowEvent(@PathVariable String processKey, @RequestBody FlowEventDTO dto) {
        try {
            requirementService.sendFlowEvent(dto.getRequirementNo(), dto.getEvent());
            return Result.success("流程事件 " + dto.getEvent() + " 已触发");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/{processKey}/flow/state")
    @ApiOperation("查询需求流程状态")
    public Result<Map<String, Object>> getFlowState(@PathVariable String processKey, @RequestParam String requirementNo) {
        try {
            return Result.success(requirementService.getFlowState(requirementNo));
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
