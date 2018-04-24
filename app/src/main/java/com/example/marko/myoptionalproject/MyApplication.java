package com.example.marko.myoptionalproject;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

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
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }

    public static Context getContext(){
        return context;
    }
}
