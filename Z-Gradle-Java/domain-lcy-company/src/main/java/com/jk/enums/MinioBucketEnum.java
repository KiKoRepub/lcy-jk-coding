package com.jk.enums;

public enum MinioBucketEnum {
    /**
     * 公共桶
     */
    PUBLIC("public"),

    /**
     * 项目资源桶
     */
    DPA("jkstack-ndpa"),

    ;

    public final String value;

    MinioBucketEnum(String value) {
        this.value = value;
    }
}
