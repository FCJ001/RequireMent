-- 为已有 sys_user 表新增字段
-- 来自 users 表合并

ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS real_name VARCHAR(100);
COMMENT ON COLUMN sys_user.real_name IS '姓名';

ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS department VARCHAR(200);
COMMENT ON COLUMN sys_user.department IS '部门';

ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS role_id VARCHAR(50);
COMMENT ON COLUMN sys_user.role_id IS '角色ID';

ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS is_enabled BOOLEAN DEFAULT true;
COMMENT ON COLUMN sys_user.is_enabled IS '是否启用';
