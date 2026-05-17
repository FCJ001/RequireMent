-- =============================================
-- 用户组管理模块 DDL
-- PostgreSQL
-- =============================================

-- 用户表
CREATE TABLE IF NOT EXISTS sys.sys_user (
    id VARCHAR(64) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(200),
    phone VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    real_name VARCHAR(100),
    department VARCHAR(200),
    role_id VARCHAR(50),
    is_enabled BOOLEAN DEFAULT true,
    created_by VARCHAR(64),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INT DEFAULT 0
);
COMMENT ON TABLE sys.sys_user IS '系统用户';
COMMENT ON COLUMN sys.sys_user.id IS '主键ID';
COMMENT ON COLUMN sys.sys_user.username IS '用户名';
COMMENT ON COLUMN sys.sys_user.email IS '邮箱';
COMMENT ON COLUMN sys.sys_user.phone IS '手机号';
COMMENT ON COLUMN sys.sys_user.status IS '状态: ACTIVE-启用, INACTIVE-禁用';

-- 用户组表
CREATE TABLE IF NOT EXISTS sys.sys_user_group (
    id VARCHAR(64) PRIMARY KEY,
    group_name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    created_by VARCHAR(64),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INT DEFAULT 0
);
COMMENT ON TABLE sys.sys_user_group IS '用户组';
COMMENT ON COLUMN sys.sys_user_group.group_name IS '用户组名称';
COMMENT ON COLUMN sys.sys_user_group.description IS '描述';

-- 用户组成员表
CREATE TABLE IF NOT EXISTS sys.sys_user_group_member (
    id VARCHAR(64) PRIMARY KEY,
    group_id VARCHAR(64) NOT NULL,
    user_id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INT DEFAULT 0
);
COMMENT ON TABLE sys.sys_user_group_member IS '用户组成员关联';
COMMENT ON COLUMN sys.sys_user_group_member.group_id IS '用户组ID';
COMMENT ON COLUMN sys.sys_user_group_member.user_id IS '用户ID';
