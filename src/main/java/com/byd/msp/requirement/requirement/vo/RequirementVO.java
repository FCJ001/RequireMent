package com.byd.msp.requirement.requirement.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequirementVO {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("需求类型")
    private String requirementType;

    @ApiModelProperty("需求名称")
    private String requirementName;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("优先级")
    private String priority;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("业务数据")
    private String data;

    @ApiModelProperty("处理人ID")
    private String handlerId;

    @ApiModelProperty("流程实例ID")
    private String flowInstanceId;

    @ApiModelProperty("当前节点")
    private String currentNode;

    @ApiModelProperty("创建者ID")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新者ID")
    private String updatedBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
}
