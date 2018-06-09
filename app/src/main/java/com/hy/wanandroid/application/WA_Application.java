package com.hy.wanandroid.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by huyin on 2018/4/17.
 */

public class WA_Application extends Application {

    private static WA_Application wa_application = null;

    public WA_Application() {
    }

    public static WA_Application getInstance() {
        return wa_application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wa_application = this;

        context = getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private static Context context;

    public static Context getContext() {
        return context;
    }


}
