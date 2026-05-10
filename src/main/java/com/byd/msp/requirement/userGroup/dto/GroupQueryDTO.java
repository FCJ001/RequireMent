package com.byd.msp.requirement.userGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户组分页查询请求")
public class GroupQueryDTO {

    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty("用户组名称(模糊查询)")
    private String groupName;
}
