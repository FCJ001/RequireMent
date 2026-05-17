package com.byd.msp.requirement.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("biz.flow_definition")
@ApiModel("流程定义")
public class FlowDefinition {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("流程ID")
    private String flowId;

    @ApiModelProperty("流程名称")
    private String flowName;

    @ApiModelProperty("节点配置")
    private String nodeConfig;

    @ApiModelProperty("流转配置")
    private String transitions;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
}
