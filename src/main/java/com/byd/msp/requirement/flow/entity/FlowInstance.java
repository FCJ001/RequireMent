package com.byd.msp.requirement.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("flow_instance")
@ApiModel("流程实例")
public class FlowInstance {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("实例ID")
    private String instanceId;

    @ApiModelProperty("流程ID")
    private String flowId;

    @ApiModelProperty("需求单号")
    private String requirementNo;

    @ApiModelProperty("当前节点")
    private String currentNode;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("待办任务列表")
    private String tasks;

    @ApiModelProperty("发起人")
    private String startUserId;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
}
