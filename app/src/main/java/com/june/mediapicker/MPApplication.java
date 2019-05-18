package com.june.mediapicker;

import android.app.Application;

import timber.log.Timber;

public class MPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
