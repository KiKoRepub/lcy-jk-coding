package com.jk.enums;



import java.util.List;

public enum ResourceTypeEnum {

    SHEET(new String[]{"csv", "xls", "xlsx"}, MinioBucketEnum.PUBLIC),
    PICTURE(new String[]{"png", "jpg"}, MinioBucketEnum.PUBLIC),
    TEXT(new String[]{"txt", "json", "md"}, null),
    VIDEO(new String[]{"mp4", "m3u8"}, MinioBucketEnum.PUBLIC);

    public final String[] suffixes;
    public final MinioBucketEnum bucketEnum;


    ResourceTypeEnum(String[] suffixes, MinioBucketEnum bucketEnum) {
        this.suffixes = suffixes;
        this.bucketEnum = bucketEnum;
    }

    public static ResourceTypeEnum getType(String type) {
        for (ResourceTypeEnum value : ResourceTypeEnum.values()) {
            if (value.name().equals(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("未知的资源类型: " + type);
    }

    public static ResourceTypeEnum getTypeBySuffix(String suffix) {
        for (ResourceTypeEnum value : ResourceTypeEnum.values()) {
            for (String s : value.suffixes) {
                if (s.equalsIgnoreCase(suffix)) {
                    return value;
                }
            }
        }
        throw new IllegalArgumentException("未知的资源后缀: " + suffix);
    }
}
