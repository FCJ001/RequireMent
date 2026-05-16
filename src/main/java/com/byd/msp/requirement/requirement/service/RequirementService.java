package com.byd.msp.requirement.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.requirement.dto.RequirementAddDTO;
import com.byd.msp.requirement.requirement.dto.RequirementQueryDTO;
import com.byd.msp.requirement.requirement.dto.RequirementUpdateDTO;
import com.byd.msp.requirement.requirement.entity.Requirement;
import com.byd.msp.requirement.requirement.vo.RequirementVO;

public interface RequirementService extends IService<Requirement> {

    PageResult<RequirementVO> query(RequirementQueryDTO dto);

    void add(RequirementAddDTO dto);

    void update(RequirementUpdateDTO dto);

    void delete(Integer id);

    RequirementVO detail(Integer id);
}
