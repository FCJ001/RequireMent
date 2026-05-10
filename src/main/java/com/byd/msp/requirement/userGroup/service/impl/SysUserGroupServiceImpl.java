package com.byd.msp.requirement.userGroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.userGroup.dto.AddGroupDTO;
import com.byd.msp.requirement.userGroup.dto.AddMemberDTO;
import com.byd.msp.requirement.userGroup.dto.GroupQueryDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateGroupDTO;
import com.byd.msp.requirement.userGroup.entity.SysUserGroup;
import com.byd.msp.requirement.userGroup.entity.SysUserGroupMember;
import com.byd.msp.requirement.userGroup.mapper.SysUserGroupMapper;
import com.byd.msp.requirement.userGroup.mapper.SysUserGroupMemberMapper;
import com.byd.msp.requirement.userGroup.service.SysUserGroupService;
import com.byd.msp.requirement.userGroup.vo.UserGroupVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements SysUserGroupService {

    @Resource
    private SysUserGroupMemberMapper memberMapper;

    @Override
    public PageResult<UserGroupVO> query(GroupQueryDTO dto) {
        LambdaQueryWrapper<SysUserGroup> wrapper = new LambdaQueryWrapper<SysUserGroup>()
                .like(StringUtils.isNotBlank(dto.getGroupName()), SysUserGroup::getGroupName, dto.getGroupName())
                .orderByDesc(SysUserGroup::getCreatedTime);

        Page<SysUserGroup> page = this.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<UserGroupVO> voList = page.getRecords().stream()
                .map(entity -> {
                    UserGroupVO vo = BeanUtil.copyProperties(entity, UserGroupVO.class);
                    Long count = memberMapper.selectCount(
                            new LambdaQueryWrapper<SysUserGroupMember>()
                                    .eq(SysUserGroupMember::getGroupId, entity.getId()));
                    vo.setMemberCount(count.intValue());
                    return vo;
                })
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void add(AddGroupDTO dto) {
        SysUserGroup group = BeanUtil.copyProperties(dto, SysUserGroup.class);
        this.save(group);
    }

    @Override
    public void update(UpdateGroupDTO dto) {
        SysUserGroup group = BeanUtil.copyProperties(dto, SysUserGroup.class);
        this.updateById(group);
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    public UserGroupVO detail(String id) {
        SysUserGroup group = this.getById(id);
        if (group == null) {
            return null;
        }
        UserGroupVO vo = BeanUtil.copyProperties(group, UserGroupVO.class);
        Long count = memberMapper.selectCount(
                new LambdaQueryWrapper<SysUserGroupMember>()
                        .eq(SysUserGroupMember::getGroupId, id));
        vo.setMemberCount(count.intValue());
        return vo;
    }

    @Override
    public void addMember(AddMemberDTO dto) {
        SysUserGroupMember member = BeanUtil.copyProperties(dto, SysUserGroupMember.class);
        memberMapper.insert(member);
    }

    @Override
    public void removeMember(String groupId, String userId) {
        memberMapper.delete(new LambdaQueryWrapper<SysUserGroupMember>()
                .eq(SysUserGroupMember::getGroupId, groupId)
                .eq(SysUserGroupMember::getUserId, userId));
    }
}
