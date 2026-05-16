package com.byd.msp.requirement.auth.controller;

import com.byd.msp.requirement.auth.dto.RoleAddDTO;
import com.byd.msp.requirement.auth.dto.RoleQueryDTO;
import com.byd.msp.requirement.auth.dto.RoleUpdateDTO;
import com.byd.msp.requirement.auth.service.RoleService;
import com.byd.msp.requirement.auth.vo.RoleVO;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/role")
@Api(tags = "角色管理")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation("新增角色")
    public Result<Void> add(@RequestBody RoleAddDTO dto) {
        roleService.add(dto);
        return Result.success();
    }

    @PostMapping("/query")
    @ApiOperation("分页查询角色")
    public Result<PageResult<RoleVO>> query(@RequestBody RoleQueryDTO dto) {
        return Result.success(roleService.query(dto));
    }

    @PutMapping("/update")
    @ApiOperation("更新角色")
    public Result<Void> update(@RequestBody RoleUpdateDTO dto) {
        roleService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    public Result<Void> delete(@PathVariable String id) {
        roleService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("角色详情")
    public Result<RoleVO> detail(@PathVariable String id) {
        return Result.success(roleService.detail(id));
    }
}
