package com.jk.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResourceUploadVo {
    @Schema(name = "资源路径,文本资源的时候 直接存储 文本内容", example = "")
    private String path;

    public ResourceUploadVo(String path) {
        this.path = path;
    }
}
