-- =============================================
-- 需求模块 DDL
-- PostgreSQL
-- =============================================

-- 需求数据表
CREATE TABLE IF NOT EXISTS requirement (
    id SERIAL PRIMARY KEY,
    requirement_no VARCHAR(100) NOT NULL UNIQUE,
    requirement_type VARCHAR(50),
    requirement_name VARCHAR(500),
    status VARCHAR(50),
    priority VARCHAR(50),
    description TEXT,
    data JSONB,
    handler_id VARCHAR(50),
    flow_instance_id VARCHAR(100),
    current_node VARCHAR(100),
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INT DEFAULT 0
);
COMMENT ON TABLE requirement IS '需求';
COMMENT ON COLUMN requirement.id IS '主键ID';
COMMENT ON COLUMN requirement.requirement_no IS '需求单号';
COMMENT ON COLUMN requirement.requirement_type IS '需求类型：IDC/IHC/Cloud/通用';
COMMENT ON COLUMN requirement.requirement_name IS '需求名称';
COMMENT ON COLUMN requirement.status IS '状态';
COMMENT ON COLUMN requirement.priority IS '优先级';
COMMENT ON COLUMN requirement.description IS '描述';
COMMENT ON COLUMN requirement.data IS '业务数据';
COMMENT ON COLUMN requirement.handler_id IS '处理人ID';
COMMENT ON COLUMN requirement.flow_instance_id IS '流程实例ID';
COMMENT ON COLUMN requirement.current_node IS '当前节点';
CREATE INDEX IF NOT EXISTS idx_requirement_no ON requirement(requirement_no);
CREATE INDEX IF NOT EXISTS idx_requirement_status ON requirement(status);

-- 需求日志表
CREATE TABLE IF NOT EXISTS requirement_log (
    id SERIAL PRIMARY KEY,
    requirement_no VARCHAR(100) NOT NULL,
    action VARCHAR(50),
    content TEXT,
    operator_id VARCHAR(50),
    operator_name VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE requirement_log IS '需求日志';
COMMENT ON COLUMN requirement_log.id IS '主键ID';
COMMENT ON COLUMN requirement_log.requirement_no IS '需求单号';
COMMENT ON COLUMN requirement_log.action IS '操作类型';
COMMENT ON COLUMN requirement_log.content IS '操作内容';
COMMENT ON COLUMN requirement_log.operator_id IS '操作人';
COMMENT ON COLUMN requirement_log.operator_name IS '操作人姓名';
CREATE INDEX IF NOT EXISTS idx_req_log_requirement_no ON requirement_log(requirement_no);
