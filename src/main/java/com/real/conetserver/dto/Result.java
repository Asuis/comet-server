package com.real.conetserver.dto;

/**
 * @author asuis
 * @date
 * 用于结果传输
 * code表示执行结果的状态码 具体参考ResultCode常量类 http status code
 * data 表示传输的数据
 * msg 报错提示信息
 */
public class Result<T> {

    private Integer code;
    private T data;
    private String msg;

    public Result() {
        this.code = ResultCode.FAIL;
    }

    public Result(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
