package com.jk.enums;

public enum ProjectTypeEnum {
    HARDWARE("hardware", "硬件产品"),
    SOFTWARE("software", "软件产品"),
    SERVICE("service", "服务产品");

    public final String value;
    public final String description;

    ProjectTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
