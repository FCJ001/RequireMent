package com.byd.msp.requirement.requirement.controller;

import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.common.result.Result;
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

@RestController
@RequestMapping("/requirement")
@Api(tags = "需求管理")
public class RequirementController {

    @Resource
    private RequirementService requirementService;

    @Resource
    private RequirementLogService requirementLogService;

    @PostMapping("/add")
    @ApiOperation("新增需求")
    public Result<Void> add(@RequestBody RequirementAddDTO dto) {
        requirementService.add(dto);
        return Result.success();
    }

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

    @GetMapping("/{requirementNo}/logs")
    @ApiOperation("需求日志列表")
    public Result<List<RequirementLog>> logs(@PathVariable String requirementNo) {
        return Result.success(requirementLogService.listByRequirementNo(requirementNo));
    }
}
