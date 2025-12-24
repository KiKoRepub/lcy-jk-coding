package com.jk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("company_resource")
public class CompanyResource {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(name = "主键ID", example = "")
    private Long id;
    @TableField("company_id")
    @Schema(name = "所属企业id", example = "")
    private Long companyId;
    @TableField("company_name")
    @Schema(name  = "所属企业名称", example = "")
    private String companyName;
    @TableField("name")
    @Schema(name  = "资源名称", example = "")
    private String name;
    @TableField("resource_type")
    @Schema(name  = "资源类型", example = "")
    private String resourceType; // 关联 ResourceTypeEnum
    @TableField("industry_type")
    @Schema(name  = "行业类型", example = "")
    private String industryType; // 关联 IndustryTypeEnum
    @TableField("project_type")
    @Schema(name  = "工程类型", example = "")
    private String projectType; // 关联 ProjectTypeEnum

    @TableField("path")
    @Schema(name  = "资源路径", example = "")
    private String path;

    @TableField("create_time")
    @Schema(name  = "创建时间", example = "")
    private Long createTime;
    @TableField("create_user")
    @Schema(name  = "创建用户", example = "")
    private String createUser;

    @TableField("update_time")
    @Schema(name  = "更新时间", example = "")
    private Long updateTime;
    @TableField("update_user")
    @Schema(name  = "更新用户", example = "")
    private String updateUser;

    @TableField("deleted")
    @Schema(name  = "删除标志", example = "0")
    private int deleted;

}
/*
CREATE TABLE `company_resource` (
  `id`                BIGINT       NOT NULL COMMENT '主键ID（雪花ID）',
  `company_id`        BIGINT       DEFAULT NULL COMMENT '所属企业id',
  `company_name`      VARCHAR(255) DEFAULT NULL COMMENT '所属企业名称',
  `name`              VARCHAR(255) NOT NULL COMMENT '资源名称',
  `resource_type`     VARCHAR(100) DEFAULT NULL COMMENT '资源类型，关联 ResourceTypeEnum',
  `industry_type`     VARCHAR(100) DEFAULT NULL COMMENT '行业类型，关联 IndustryTypeEnum',
  `project_type`      VARCHAR(100) DEFAULT NULL COMMENT '工程类型，关联 ProjectTypeEnum',
  `path`              VARCHAR(1024) DEFAULT NULL COMMENT '资源路径（支持长路径/URL）',
  `create_time`       BIGINT       NOT NULL COMMENT '创建时间（毫秒时间戳）',
  `create_user`       VARCHAR(100) DEFAULT NULL COMMENT '创建用户',
  `update_time`       BIGINT       NOT NULL COMMENT '更新时间（毫秒时间戳）',
  `update_user`       VARCHAR(100) DEFAULT NULL COMMENT '更新用户',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业资源表';
 */