package com.byd.msp.requirement.requirement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequirementQueryDTO {

    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("需求类型")
    private String requirementType;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("优先级")
    private String priority;
}
