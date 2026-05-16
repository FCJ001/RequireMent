-- =============================================
-- 角色表（用户权限模块）
-- PostgreSQL
-- =============================================

CREATE TABLE IF NOT EXISTS role (
    id VARCHAR(50) PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL,
    role_code VARCHAR(100),
    org_id VARCHAR(50),
    org_name VARCHAR(200),
    permissions JSONB,
    is_enabled BOOLEAN DEFAULT true,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE role IS '角色';
COMMENT ON COLUMN role.id IS '角色ID';
COMMENT ON COLUMN role.role_name IS '角色名称';
COMMENT ON COLUMN role.role_code IS '角色编码';
COMMENT ON COLUMN role.org_id IS '组织ID';
COMMENT ON COLUMN role.org_name IS '组织名称';
COMMENT ON COLUMN role.permissions IS '权限列表';
COMMENT ON COLUMN role.is_enabled IS '是否启用';
