package com.byd.msp.requirement.requirement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequirementAddDTO {

    @NotBlank(message = "需求单号不能为空")
    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("需求类型：IDC/IHC/Cloud/通用")
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

    @ApiModelProperty("创建动作: CRequirementAdd:STAGE(暂存/草稿) / CRequirementAdd:SUBMIT(提交/需求评估)，仅 FL25103101 流程使用")
    private String action;

    @ApiModelProperty("流程实例ID")
    private String flowInstanceId;

    @ApiModelProperty("当前节点")
    private String currentNode;
}
