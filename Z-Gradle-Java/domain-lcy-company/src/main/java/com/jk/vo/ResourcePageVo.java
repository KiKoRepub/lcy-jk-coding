package com.jk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResourcePageVo {
    @Schema(description = "主键ID", example = "")
    private Long id;
    @Schema(description = "资源名称", example = "")
    private String name;
    @Schema(description = "资源类型", example = "")
    private String resourceType;
    @Schema(description = "行业类型", example = "")
    private String industryType;
    @Schema(description = "项目类型", example = "")
    private String projectType;
    @Schema(description = "所属企业名称", example = "")
    private String companyName;
    @Schema(description = "资源路径", example = "")
    private String path;
}
