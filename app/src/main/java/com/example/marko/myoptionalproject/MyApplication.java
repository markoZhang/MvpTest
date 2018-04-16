package com.example.marko.myoptionalproject;

import android.app.Application;
import android.content.Context;

/**
 * @author Marko
 * @date 2018/4/16
 */

public class MyApplication extends Application{

    private static MyApplication instance = null;
    private static Context context;

    public static synchronized MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance != null){
            instance = this;
        }
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
