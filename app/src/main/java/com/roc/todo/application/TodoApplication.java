package com.roc.todo.application;

import android.app.Application;

/**
 * Created by 赖鹏旭 on 2018/3/13.
 * 待办事项App
 */
public class TodoApplication extends Application {

    private static TodoApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static TodoApplication getInstance() {
        return mInstance;
    }
}
