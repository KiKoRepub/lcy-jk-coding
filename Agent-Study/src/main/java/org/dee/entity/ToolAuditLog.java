package org.dee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 工具审计日志实体类
 */
@Data
@TableName("tool_audit_log")
public class ToolAuditLog {

    @TableId(type = IdType.AUTO)
    @Schema(description = "日志ID")
    private Integer id;

    @TableField("conversation_id")
    @Schema(description = "对话ID")
    private String conversationId;

    @TableField("request_id")
    @Schema(description = "请求ID")
    private String requestId;

    @TableField("tool_name")
    @Schema(description = "工具名称")
    private String toolName;

    @TableField("method_name")
    @Schema(description = "方法名称")
    private String methodName;

    @TableField("status")
    @Schema(description = "执行状态: STARTED, COMPLETED, FAILED")
    private String status;

    @TableField("parameters")
    @Schema(description = "工具参数(JSON格式)")
    private String parameters;

    @TableField("result")
    @Schema(description = "执行结果(JSON格式)")
    private String result;

    @TableField("error_message")
    @Schema(description = "错误信息")
    private String errorMessage;

    @TableField("execution_time")
    @Schema(description = "执行耗时(毫秒)")
    private Long executionTime;

    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @TableField("start_at")
    @Schema(description = "开始时间")
    private LocalDateTime startAt;

    @TableField("end_at")
    @Schema(description = "结束时间")
    private LocalDateTime endAt;

    @TableField("user_id")
    @Schema(description = "用户ID")
    private String userId;

    @TableField("ip_address")
    @Schema(description = "IP地址")
    private String ipAddress;
}
