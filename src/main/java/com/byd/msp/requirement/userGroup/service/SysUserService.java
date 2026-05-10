package com.byd.msp.requirement.userGroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.userGroup.dto.AddUserDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateUserDTO;
import com.byd.msp.requirement.userGroup.dto.UserQueryDTO;
import com.byd.msp.requirement.userGroup.entity.SysUser;
import com.byd.msp.requirement.userGroup.vo.UserVO;

public interface SysUserService extends IService<SysUser> {

    PageResult<UserVO> query(UserQueryDTO dto);

    void add(AddUserDTO dto);

    void update(UpdateUserDTO dto);

    void delete(String id);

    UserVO detail(String id);
}
