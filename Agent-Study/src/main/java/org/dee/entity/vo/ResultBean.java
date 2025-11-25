package org.dee.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.dee.enums.ErrorCodeEnum;

@Data
public class ResultBean<T> {

    @Schema(description = "状态码")
    private int code;
    @Schema(description = "返回信息")
    private String message;
    @Schema(description = "返回数据")
    private T data;


    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(200, "success", data);
    }
    public static <T> ResultBean<T> success(String message, T data) {
        return new ResultBean<>(200, message, data);
    }

    public static <T> ResultBean<T> error(ErrorCodeEnum codeEnum) {
        return new ResultBean<>(codeEnum.getCode(), "Error", null);
    }

    public static <T> ResultBean<T> error(ErrorCodeEnum codeEnum, String message) {
        return new ResultBean<>(codeEnum.getCode(), message, null);
    }

    public static <T> ResultBean<T> error(ErrorCodeEnum codeEnum,String message,T data) {
        return new ResultBean<>(codeEnum.getCode(), message, null);
    }

    public ResultBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
