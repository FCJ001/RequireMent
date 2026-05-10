package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("添加组成员请求")
public class AddMemberDTO {

    @ApiModelProperty(value = "用户组ID", required = true)
    private String groupId;

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;
}
