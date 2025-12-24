package org.lcy.flowable.config;

import java.io.Serializable;
import lombok.Data;
import org.lcy.flowable.enums.FormCategoryEnum;

/**
 * FormConfig 表单配置.
 */
@Data
public class FormConfig implements Serializable {


    /**
     * 分组名称.
     */
    private String group;

    /**
     * 分组权重.
     */
    private Double weight;

    /**
     * 分类：评分SCORE、评语COMMENT.
     */
    private FormCategoryEnum category;

}
