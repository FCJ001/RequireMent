package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增用户组请求")
public class AddGroupDTO {

    @ApiModelProperty(value = "用户组名称", required = true)
    private String groupName;

    @ApiModelProperty("描述")
    private String description;
}
