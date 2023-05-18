package com.example.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录的Id
 *
 * @author Ustinain
 * @Time 2023/5/3  0:21
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
