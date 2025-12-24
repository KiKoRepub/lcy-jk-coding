package org.lcy.common;

import lombok.Data;

@Data
public class ResultBean<T> {

    private int code;
    private String message;
    private T data;


    public ResultBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(200, "Success", data);
    }

    public static <T> ResultBean<T> error(String message) {
        return new ResultBean<>(500, message, null);
    }
}
