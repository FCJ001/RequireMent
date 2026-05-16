package com.byd.msp.requirement.requirement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequirementUpdateDTO {

    @NotNull(message = "需求ID不能为空")
    @ApiModelProperty("需求ID")
    private Integer id;

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
}
