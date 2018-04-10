package com.roc.todo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.roc.todo.application.TodoApplication;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-4-5
 * Time: 下午7:38
 * SharedPreferences 工具类
 */
public class SPUtils {

    private static final String FILE_NAME = "todo_list";

    private SPUtils() {
    }

    /**
     * 获取SharedPreferences
     *
     * @return
     */
    private static SharedPreferences getSharedPreferences() {
        return TodoApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 添加操作
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * 获取操作
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static Map<String,?> getAll() {
        return getSharedPreferences().getAll();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }
}
