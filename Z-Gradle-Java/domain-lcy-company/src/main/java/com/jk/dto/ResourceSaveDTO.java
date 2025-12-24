package com.jk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResourceSaveDTO {
    @Schema(description = "主键ID", example = "")
    private Long id;
    @Schema(description = "资源名", example = "")
    private String name;
    @Schema(description = "资源分类", example = "")
    private String resourceType;
    @Schema(description = "行业分类", example = "")
    private String industryType;
    @Schema(description = "工程分类", example = "")
    private String projectType;
    @Schema(description = "所属企业id", example = "")
    private Long companyId;

    @Schema(description = "资源路径", example = "")
    private String path;


}
