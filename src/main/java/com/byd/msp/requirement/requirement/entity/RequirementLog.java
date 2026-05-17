package com.byd.msp.requirement.requirement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("biz.requirement_log")
@ApiModel("需求日志")
public class RequirementLog {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("操作类型")
    private String action;

    @ApiModelProperty("操作内容")
    private String content;

    @ApiModelProperty("操作人")
    private String operatorId;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;
}
