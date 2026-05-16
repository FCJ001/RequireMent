package com.byd.msp.requirement.requirement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.requirement.entity.RequirementLog;
import com.byd.msp.requirement.requirement.mapper.RequirementLogMapper;
import com.byd.msp.requirement.requirement.service.RequirementLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequirementLogServiceImpl extends ServiceImpl<RequirementLogMapper, RequirementLog> implements RequirementLogService {

    @Override
    public List<RequirementLog> listByRequirementNo(String requirementNo) {
        LambdaQueryWrapper<RequirementLog> wrapper = new LambdaQueryWrapper<RequirementLog>()
                .eq(RequirementLog::getRequirementNo, requirementNo)
                .orderByDesc(RequirementLog::getCreatedTime);
        return this.list(wrapper);
    }

    @Override
    public void log(String requirementNo, String action, String content, String operatorId, String operatorName) {
        RequirementLog log = new RequirementLog();
        log.setRequirementNo(requirementNo);
        log.setAction(action);
        log.setContent(content);
        log.setOperatorId(operatorId);
        log.setOperatorName(operatorName);
        log.setCreatedTime(LocalDateTime.now());
        this.save(log);
    }
}
