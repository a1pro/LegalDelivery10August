package com.aetherti;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static boolean sIsChatActivityOpen = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}