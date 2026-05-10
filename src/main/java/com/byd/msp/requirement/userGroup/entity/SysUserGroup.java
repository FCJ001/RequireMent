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
@TableName("sys_user_group")
@ApiModel("用户组")
public class SysUserGroup extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("用户组名称")
    private String groupName;

    @ApiModelProperty("描述")
    private String description;
}
