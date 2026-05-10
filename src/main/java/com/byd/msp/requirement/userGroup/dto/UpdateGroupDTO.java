package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新用户组请求")
public class UpdateGroupDTO {

    @ApiModelProperty(value = "用户组ID", required = true)
    private String id;

    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("描述")
    private String description;
}
