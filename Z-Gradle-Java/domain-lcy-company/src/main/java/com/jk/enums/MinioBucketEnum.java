package com.jk.enums;

public enum MinioBucketEnum {
    /**
     * 公共桶
     */
    PUBLIC("jkstack-poc"),

    ;

    public final String value;

    MinioBucketEnum(String value) {
        this.value = value;
    }
}
