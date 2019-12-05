package com.ucarinc.usercenter.bxaftersale.domain;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lidinglin
 * @date 2019/11/11
 * <p>
 * Description：api调用响应结果集
 */
@Getter
@ToString
public class Result<T> implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 详细异常信息
     */
    private Throwable e;

    /**
     * 具体响应数据
     */
    private T data;


    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message, T data, Throwable e) {
        this.code = code;
        this.message = message;
        this.e = e;
        this.data = data;
    }


}
