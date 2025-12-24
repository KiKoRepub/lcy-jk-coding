package com.jk.enums;

public enum FilePathEnum {


    SHEET("/sheet"),
    PICTURE("/picture"),
    TEXT("/text"),
    VIDEO("/video");

    public final String path;

    FilePathEnum(String path) {
        this.path = path;
    }

    public static String getFilePath(String fileType) {
        for (FilePathEnum value : FilePathEnum.values()) {
            if (value.name().toLowerCase().equals(fileType)) {
                return value.path;
            }
        }
        throw new IllegalArgumentException("未知的文件类型: " + fileType);
    }
}
