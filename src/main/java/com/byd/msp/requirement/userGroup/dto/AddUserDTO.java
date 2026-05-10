package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增用户请求")
public class AddUserDTO {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;
}
