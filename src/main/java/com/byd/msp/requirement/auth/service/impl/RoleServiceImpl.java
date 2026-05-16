package com.byd.msp.requirement.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.auth.dto.RoleAddDTO;
import com.byd.msp.requirement.auth.dto.RoleQueryDTO;
import com.byd.msp.requirement.auth.dto.RoleUpdateDTO;
import com.byd.msp.requirement.auth.entity.Role;
import com.byd.msp.requirement.auth.mapper.RoleMapper;
import com.byd.msp.requirement.auth.service.RoleService;
import com.byd.msp.requirement.auth.vo.RoleVO;
import com.byd.msp.requirement.common.model.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public PageResult<RoleVO> query(RoleQueryDTO dto) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(dto.getRoleName()), Role::getRoleName, dto.getRoleName())
                .like(StringUtils.isNotBlank(dto.getRoleCode()), Role::getRoleCode, dto.getRoleCode())
                .eq(dto.getIsEnabled() != null, Role::getIsEnabled, dto.getIsEnabled())
                .orderByDesc(Role::getCreatedTime);

        Page<Role> page = this.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<RoleVO> voList = page.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, RoleVO.class))
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void add(RoleAddDTO dto) {
        Role role = BeanUtil.copyProperties(dto, Role.class);
        if (role.getIsEnabled() == null) {
            role.setIsEnabled(true);
        }
        this.save(role);
    }

    @Override
    public void update(RoleUpdateDTO dto) {
        Role role = BeanUtil.copyProperties(dto, Role.class);
        this.updateById(role);
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    public RoleVO detail(String id) {
        Role role = this.getById(id);
        if (role == null) {
            return null;
        }
        return BeanUtil.copyProperties(role, RoleVO.class);
    }
}
