
CREATE TABLE `role` (
                        `id` BIGINT NOT NULL COMMENT '角色ID',
                        `role_name` VARCHAR(100) NOT NULL COMMENT '角色名称',
                        `description` VARCHAR(500) DEFAULT NULL COMMENT '角色描述',
                        PRIMARY KEY (`id`),
                        KEY `idx_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';


CREATE TABLE `user` (
                        `id` BIGINT NOT NULL COMMENT '用户ID',
                        `username` VARCHAR(100) NOT NULL COMMENT '用户名',
                        `password` VARCHAR(255) NOT NULL COMMENT '密码（加密后）',
                        `enabled` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '是否启用（1-启用，0-禁用）',
                        `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_username` (`username`),
                        KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `user_role` (
                             `user_id` BIGINT NOT NULL COMMENT '用户ID',
                             `role_id` BIGINT NOT NULL COMMENT '角色ID',
                             `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`user_id`, `role_id`),
                             KEY `idx_role_id` (`role_id`),
                             KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

