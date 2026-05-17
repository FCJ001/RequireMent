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
@TableName("sys.sys_user_group_member")
@ApiModel("用户组成员")
public class SysUserGroupMember extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("用户组ID")
    private String groupId;

    @ApiModelProperty("用户ID")
    private String userId;
}
