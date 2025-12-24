package com.jk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AttachCompanyPageVo {
    @Schema(description = "企业名字", example = "")
    private String companyName;
    @Schema(description = "企业id", example = "")
    private Long companyId;

}
