package com.byd.msp.requirement.userGroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.userGroup.dto.AddGroupDTO;
import com.byd.msp.requirement.userGroup.dto.AddMemberDTO;
import com.byd.msp.requirement.userGroup.dto.GroupQueryDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateGroupDTO;
import com.byd.msp.requirement.userGroup.entity.SysUserGroup;
import com.byd.msp.requirement.userGroup.vo.UserGroupVO;

public interface SysUserGroupService extends IService<SysUserGroup> {

    PageResult<UserGroupVO> query(GroupQueryDTO dto);

    void add(AddGroupDTO dto);

    void update(UpdateGroupDTO dto);

    void delete(String id);

    UserGroupVO detail(String id);

    void addMember(AddMemberDTO dto);

    void removeMember(String groupId, String userId);
}
