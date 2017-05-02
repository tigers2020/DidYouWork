package com.androidnerdcolony.didyouwork;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by tiger on 5/1/2017.
 */

public class DYWApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
