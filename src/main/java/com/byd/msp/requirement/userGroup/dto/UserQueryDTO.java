package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户分页查询请求")
public class UserQueryDTO {

    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty("用户名(模糊查询)")
    private String username;

    @ApiModelProperty("状态: ACTIVE-启用, INACTIVE-禁用")
    private String status;

    @ApiModelProperty("姓名(模糊查询)")
    private String realName;

    @ApiModelProperty("部门(模糊查询)")
    private String department;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}
