package com.byd.msp.requirement.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleQueryDTO {

    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}
