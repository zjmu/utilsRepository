package com.ucar.train.common.util;

import com.google.gson.Gson;


public class GsonUtils {

    /**
     * 引用gson对象
     */
    private static final Gson GSON = new Gson();

    /**
     * 对象JSON化
     *
     * @param t 对象
     * @return
     */
    public static String toJson(Object t) {
        return GSON.toJson(t);
    }

    /**
     * 将Json字符串 转化为 对象
     *
     * @param json  json 字符串
     * @param clazz 对象
     * @param <T>   对象类型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

}
