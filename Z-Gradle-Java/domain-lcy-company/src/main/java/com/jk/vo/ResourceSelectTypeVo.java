package com.jk.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ResourceSelectTypeVo {
    @Schema(name = "文件类型", example = "")
    private List<String> fileTypeList;
    @Schema(name = "行业分类", example = "")
    private List<String> industryTypeList;
    @Schema(name = "工程分类", example = "")
    private List<String> projectTypeList;

    public ResourceSelectTypeVo(List<String> fileTypeList,List<String> industryTypeList, List<String> projectTypeList) {
        this.fileTypeList = fileTypeList;
        this.industryTypeList = industryTypeList;
        this.projectTypeList = projectTypeList;
    }

}