package com.jk.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ResourcePageDTO {

    @Min(value = 1, message = "页码最小为1")
    @Schema(name = "页码")
    private Integer pageNum = 1;
    @Min(value = 1, message = "每页数量最小为1")
    @Schema(name = "每页数量")
    private Integer pageSize = 10;

    @Size(max = 50, message = "名称长度不能超过50个字符")
    @Schema(name = "所属企业名称", example = "")
    private String companyName;



}
