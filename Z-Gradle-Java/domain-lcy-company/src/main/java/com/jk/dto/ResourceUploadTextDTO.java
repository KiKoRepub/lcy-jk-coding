package com.jk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class ResourceUploadTextDTO {

    @NotNull(message = "内容不能为空")
    @Schema(description = "素材的文字内容")
    String content;
    @NotNull(message = "类型不能为空")
    @Schema(description = "素材的Minio类型")
    String type;
}
