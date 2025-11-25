package org.dee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sql_tool")
public class SQLTool {

    @TableId(type = IdType.AUTO)
    @Schema(description = "工具ID")
    private Integer id;

    @TableField("tool_name")
    @Schema(description = "工具名称")
    private String toolName;

    @TableField("description")
    @Schema(description = "工具描述")
    private String description;

    @TableField("class_name")
    @Schema(description = "工具类的完整类名(MCP对应 ServerName,USER 对应 用户id )")
    private String className;


    @TableField("input_schema")
    @Schema(description = "工具参数定义(JSON格式)")
    private String inputSchema;

    @TableField("enabled")
    @Schema(description = "是否启用: 1-启用, 0-禁用")
    private Integer enabled;

    @TableField("category")
    @Schema(description = "工具分类")
    private String category;

    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
