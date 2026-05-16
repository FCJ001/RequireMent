package com.byd.msp.requirement.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byd.msp.requirement.requirement.entity.RequirementLog;

import java.util.List;

public interface RequirementLogService extends IService<RequirementLog> {

    List<RequirementLog> listByRequirementNo(String requirementNo);

    void log(String requirementNo, String action, String content, String operatorId, String operatorName);
}
