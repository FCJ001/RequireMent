package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新用户请求")
public class UpdateUserDTO {

    @ApiModelProperty(value = "用户ID", required = true)
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("状态: ACTIVE-启用, INACTIVE-禁用")
    private String status;
}
