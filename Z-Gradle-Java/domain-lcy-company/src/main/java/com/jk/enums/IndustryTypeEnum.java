package com.jk.enums;

public enum IndustryTypeEnum {


    CONSTRUCTION("construction", "建筑业"),
    MANUFACTURING("manufacturing", "制造业"),
    IT("it", "信息技术业"),
    HEALTHCARE("healthcare", "医疗保健业"),
    EDUCATION("education", "教育业");

    public final String value;
    public final String description;

    IndustryTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
