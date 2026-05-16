package com.byd.msp.requirement.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.auth.dto.RoleAddDTO;
import com.byd.msp.requirement.auth.dto.RoleQueryDTO;
import com.byd.msp.requirement.auth.dto.RoleUpdateDTO;
import com.byd.msp.requirement.auth.entity.Role;
import com.byd.msp.requirement.auth.vo.RoleVO;
import com.byd.msp.requirement.common.model.PageResult;

public interface RoleService extends IService<Role> {

    PageResult<RoleVO> query(RoleQueryDTO dto);

    void add(RoleAddDTO dto);

    void update(RoleUpdateDTO dto);

    void delete(String id);

    RoleVO detail(String id);
}
