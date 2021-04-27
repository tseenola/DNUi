package com.tenseenola.dnui;

import android.app.Application;

/**
 * Created by lenovo on 2021/4/7.
 * 描述：
 */
public class MyApplication extends Application {
    public static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
