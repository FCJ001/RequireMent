package com.byd.msp.requirement.userGroup.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("用户组视图")
public class UserGroupVO {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("成员数量")
    private Integer memberCount;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdTime;
}
