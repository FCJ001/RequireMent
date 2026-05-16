-- =============================================
-- 流程模块 DDL
-- PostgreSQL
-- =============================================

-- 流程定义表
CREATE TABLE IF NOT EXISTS flow_definition (
    id SERIAL PRIMARY KEY,
    flow_id VARCHAR(100) NOT NULL UNIQUE,
    flow_name VARCHAR(200),
    node_config JSONB,
    transitions JSONB,
    is_enabled BOOLEAN DEFAULT true,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE flow_definition IS '流程定义';
COMMENT ON COLUMN flow_definition.id IS '主键ID';
COMMENT ON COLUMN flow_definition.flow_id IS '流程ID';
COMMENT ON COLUMN flow_definition.flow_name IS '流程名称';
COMMENT ON COLUMN flow_definition.node_config IS '节点配置';
COMMENT ON COLUMN flow_definition.transitions IS '流转配置';
COMMENT ON COLUMN flow_definition.is_enabled IS '是否启用';

-- 流程实例表
CREATE TABLE IF NOT EXISTS flow_instance (
    id SERIAL PRIMARY KEY,
    instance_id VARCHAR(100) NOT NULL UNIQUE,
    flow_id VARCHAR(100) NOT NULL,use
    requirement_no VARCHAR(100),
    current_node VARCHAR(100),
    status VARCHAR(50),
    tasks JSONB,
    start_user_id VARCHAR(50),
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP
);
COMMENT ON TABLE flow_instance IS '流程实例';
COMMENT ON COLUMN flow_instance.id IS '主键ID';
COMMENT ON COLUMN flow_instance.instance_id IS '实例ID';
COMMENT ON COLUMN flow_instance.flow_id IS '流程ID';
COMMENT ON COLUMN flow_instance.requirement_no IS '需求单号';
COMMENT ON COLUMN flow_instance.current_node IS '当前节点';
COMMENT ON COLUMN flow_instance.status IS '状态';
COMMENT ON COLUMN flow_instance.tasks IS '待办任务列表';
COMMENT ON COLUMN flow_instance.start_user_id IS '发起人';
COMMENT ON COLUMN flow_instance.start_time IS '开始时间';
COMMENT ON COLUMN flow_instance.end_time IS '结束时间';
CREATE INDEX IF NOT EXISTS idx_fi_requirement_no ON flow_instance(requirement_no);
CREATE INDEX IF NOT EXISTS idx_fi_status ON flow_instance(status);
