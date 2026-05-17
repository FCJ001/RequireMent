package com.byd.msp.requirement.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys.role")
@ApiModel("角色")
public class Role {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("角色ID")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("组织ID")
    private String orgId;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("权限列表")
    private String permissions;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
}
