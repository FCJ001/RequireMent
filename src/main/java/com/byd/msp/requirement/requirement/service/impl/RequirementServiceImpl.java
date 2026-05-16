package com.byd.msp.requirement.requirement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.requirement.dto.RequirementAddDTO;
import com.byd.msp.requirement.requirement.dto.RequirementQueryDTO;
import com.byd.msp.requirement.requirement.dto.RequirementUpdateDTO;
import com.byd.msp.requirement.requirement.entity.Requirement;
import com.byd.msp.requirement.requirement.mapper.RequirementMapper;
import com.byd.msp.requirement.requirement.service.RequirementService;
import com.byd.msp.requirement.requirement.vo.RequirementVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequirementServiceImpl extends ServiceImpl<RequirementMapper, Requirement> implements RequirementService {

    @Override
    public PageResult<RequirementVO> query(RequirementQueryDTO dto) {
        LambdaQueryWrapper<Requirement> wrapper = new LambdaQueryWrapper<Requirement>()
                .eq(StringUtils.isNotBlank(dto.getRequirementNo()), Requirement::getRequirementNo, dto.getRequirementNo())
                .eq(StringUtils.isNotBlank(dto.getRequirementType()), Requirement::getRequirementType, dto.getRequirementType())
                .eq(StringUtils.isNotBlank(dto.getStatus()), Requirement::getStatus, dto.getStatus())
                .eq(StringUtils.isNotBlank(dto.getPriority()), Requirement::getPriority, dto.getPriority())
                .orderByDesc(Requirement::getCreatedTime);

        Page<Requirement> page = this.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<RequirementVO> voList = page.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, RequirementVO.class))
                .collect(Collectors.toList());

        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void add(RequirementAddDTO dto) {
        Requirement requirement = BeanUtil.copyProperties(dto, Requirement.class);
        this.save(requirement);
    }

    @Override
    public void update(RequirementUpdateDTO dto) {
        Requirement requirement = BeanUtil.copyProperties(dto, Requirement.class);
        this.updateById(requirement);
    }

    @Override
    public void delete(Integer id) {
        this.removeById(id);
    }

    @Override
    public RequirementVO detail(Integer id) {
        Requirement requirement = this.getById(id);
        if (requirement == null) {
            return null;
        }
        return BeanUtil.copyProperties(requirement, RequirementVO.class);
    }
}
