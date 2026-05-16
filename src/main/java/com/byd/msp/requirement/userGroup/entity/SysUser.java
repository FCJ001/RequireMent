package com.byd.msp.requirement.userGroup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.byd.msp.requirement.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@ApiModel("系统用户")
public class SysUser extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("状态: ACTIVE-启用, INACTIVE-禁用")
    private String status;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;
}
