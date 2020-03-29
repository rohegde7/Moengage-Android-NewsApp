package com.rohegde7.moengage;

import android.app.Application;
import android.content.Context;

public class MoEngageApplication extends Application {
    private static MoEngageApplication mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = this;
    }
}
