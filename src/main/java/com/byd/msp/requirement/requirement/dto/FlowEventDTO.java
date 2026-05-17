package com.byd.msp.requirement.requirement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FlowEventDTO {

    @NotBlank(message = "需求单号不能为空")
    @ApiModelProperty("需求单号")
    private String requirementNo;

    @NotBlank(message = "事件不能为空")
    @ApiModelProperty("事件: submit/approve/reject/review_pass/review_reject/accept")
    private String event;
}
