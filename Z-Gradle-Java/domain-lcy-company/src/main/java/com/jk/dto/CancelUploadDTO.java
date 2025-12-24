package com.jk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CancelUploadDTO {

    @Schema(description = "资源路径", example = "")
    private String path;

    @Schema(description = "资源类型", example = "")
    private String type;
}
