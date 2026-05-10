package com.byd.msp.requirement.userGroup.controller;

import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.userGroup.dto.AddGroupDTO;
import com.byd.msp.requirement.userGroup.dto.AddMemberDTO;
import com.byd.msp.requirement.userGroup.dto.GroupQueryDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateGroupDTO;
import com.byd.msp.requirement.userGroup.service.SysUserGroupService;
import com.byd.msp.requirement.userGroup.vo.UserGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/group")
@Api(tags = "用户组管理")
public class SysUserGroupController {

    @Resource
    private SysUserGroupService sysUserGroupService;

    @PostMapping("/add")
    @ApiOperation("新增用户组")
    public Result<String> add(@RequestBody AddGroupDTO dto) {
        sysUserGroupService.add(dto);
        return Result.success();
    }

    @PostMapping("/query")
    @ApiOperation("分页查询用户组")
    public Result<PageResult<UserGroupVO>> query(@RequestBody GroupQueryDTO dto) {
        return Result.success(sysUserGroupService.query(dto));
    }

    @PutMapping("/update")
    @ApiOperation("更新用户组")
    public Result<Void> update(@RequestBody UpdateGroupDTO dto) {
        sysUserGroupService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户组")
    public Result<Void> delete(@PathVariable String id) {
        sysUserGroupService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("用户组详情")
    public Result<UserGroupVO> detail(@PathVariable String id) {
        return Result.success(sysUserGroupService.detail(id));
    }

    @PostMapping("/member/add")
    @ApiOperation("添加组成员")
    public Result<String> addMember(@RequestBody AddMemberDTO dto) {
        sysUserGroupService.addMember(dto);
        return Result.success();
    }

    @DeleteMapping("/member/remove")
    @ApiOperation("移除组成员")
    public Result<Void> removeMember(@RequestParam String groupId, @RequestParam String userId) {
        sysUserGroupService.removeMember(groupId, userId);
        return Result.success();
    }
}
