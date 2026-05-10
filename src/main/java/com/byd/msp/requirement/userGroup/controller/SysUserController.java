package com.byd.msp.requirement.userGroup.controller;

import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.userGroup.dto.AddUserDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateUserDTO;
import com.byd.msp.requirement.userGroup.dto.UserQueryDTO;
import com.byd.msp.requirement.userGroup.service.SysUserService;
import com.byd.msp.requirement.userGroup.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/add")
    @ApiOperation("新增用户")
    public Result<String> add(@RequestBody AddUserDTO dto) {
        sysUserService.add(dto);
        return Result.success();
    }

    @PostMapping("/query")
    @ApiOperation("分页查询用户")
    public Result<PageResult<UserVO>> query(@RequestBody UserQueryDTO dto) {
        return Result.success(sysUserService.query(dto));
    }

    @PutMapping("/update")
    @ApiOperation("更新用户")
    public Result<Void> update(@RequestBody UpdateUserDTO dto) {
        sysUserService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Void> delete(@PathVariable String id) {
        sysUserService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("用户详情")
    public Result<UserVO> detail(@PathVariable String id) {
        return Result.success(sysUserService.detail(id));
    }
}
