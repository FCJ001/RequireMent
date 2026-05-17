-- =============================================
-- 创建数据库 Schema
-- PostgreSQL
-- =============================================

-- 系统/权限模块
CREATE SCHEMA IF NOT EXISTS sys;
COMMENT ON SCHEMA sys IS '系统管理：用户、用户组、角色';

-- 业务模块
CREATE SCHEMA IF NOT EXISTS biz;
COMMENT ON SCHEMA biz IS '业务模块：需求、流程定义';

-- 工作流引擎
CREATE SCHEMA IF NOT EXISTS wf;
COMMENT ON SCHEMA wf IS 'Flowable 工作流引擎表';
