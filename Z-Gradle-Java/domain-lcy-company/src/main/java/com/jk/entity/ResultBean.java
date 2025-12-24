package com.jk.entity;

import com.jk.enums.ErrorCodeEnum;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = -5923033411371470574L;

    private static final String urlMsg = "url error";

    private String message = "success";

    private int code = ErrorCodeEnum.SUCCESS.getCode();

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        if (data instanceof String ) {
            this.message = (String) data;
            this.code = ErrorCodeEnum.FAIL.getCode();
        } else {
            this.data = data;
        }
    }

    public ResultBean(T data, ErrorCodeEnum code) {
        this.data = data;
        this.code = code.getCode();
    }

    public ResultBean(Throwable e) {
        super();
        this.message = e.toString();
        this.code = ErrorCodeEnum.FAIL.getCode();
    }

    public ResultBean(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public ResultBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public ResultBean setData(T data) {
        this.data = data;
        return this;
    }

    public ResultBean setURLError() {
        this.code = ErrorCodeEnum.URL_ERROR.getCode();
        ;
        this.message = urlMsg;
        return this;
    }

    public void setParamError(String message) {
        this.code = ErrorCodeEnum.PARAM_ERROR.getCode();
        this.message = message;
    }

    public void setBusinessError(String message) {
        this.code = ErrorCodeEnum.FAIL.getCode();
        this.message = message;
    }

    public void setUnknownError(String message) {
        this.code = ErrorCodeEnum.UNKNOWN_ERROR.getCode();
        this.message = message;
    }

    public ResultBean setError(String message) {
        this.code = ErrorCodeEnum.FAIL.getCode();
        this.message = message;
        return this;
    }

    public ResultBean setNoUser(String message) {
        this.code = ErrorCodeEnum.NO_USER.getCode();
        ;
        this.message = message;
        return this;
    }

    public ResultBean setServiceError(String message) {
        this.code = ErrorCodeEnum.SERVICE_ERROR.getCode();
        this.message = message;
        return this;
    }

    public ResultBean serauthorror(String message) {
        this.code = ErrorCodeEnum.AUTH_ERROR.getCode();
        this.message = message;
        return this;
    }

    public ResultBean serSentinelError(String message) {
        this.code = ErrorCodeEnum.SENTINEL_ERROR.getCode();
        this.message = message;
        return this;
    }
}