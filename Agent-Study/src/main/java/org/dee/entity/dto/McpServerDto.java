package org.dee.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dee.enums.McpServerTypeEnum;

import javax.validation.constraints.NotNull;

/**
 * MCP服务器数据传输对象
 * 用于接收前端请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "MCP服务器请求参数")
public class McpServerDto {

    @Schema(description = "服务器ID（更新时必填）")
    private Integer id;

    @NotNull(message = "服务器名称不能为空")
    @Schema(description = "MCP服务器名称", required = true, example = "高德地图MCP服务")
    private String serverName;

    @Schema(description = "MCP服务器URL", example = "https://mcp.amap.com")
    private String serverUrl;

    @Schema(description = "服务器描述", example = "提供地图相关功能的MCP服务")
    private String description;

    @Schema(description = "SSE/HTTP端点路径", example = "/sse?key=YOUR_API_KEY")
    private String endpoint;

    @NotNull(message = "服务器类型不能为空")
    @Schema(description = "服务器类型：STDIO, HTTP, SSE", required = true, example = "SSE")
    private McpServerTypeEnum type;

    @Schema(description = "STDIO类型的JSON配置内容", example = "{\"command\":\"npx.cmd\",\"args\":[\"chrome-devtools-mcp@latest\"]}")
    private String jsonContent;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

}
