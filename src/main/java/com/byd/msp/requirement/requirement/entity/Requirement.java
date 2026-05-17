package com.byd.msp.requirement.requirement.entity;

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
@TableName("biz.requirement")
@ApiModel("需求")
public class Requirement extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("需求类型：IDC/IHC/Cloud/通用")
    private String requirementType;

    @ApiModelProperty("需求名称")
    private String requirementName;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("优先级")
    private String priority;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("业务数据")
    private String data;

    @ApiModelProperty("处理人ID")
    private String handlerId;

    @ApiModelProperty("流程实例ID")
    private String flowInstanceId;

    @ApiModelProperty("当前节点")
    private String currentNode;
}
