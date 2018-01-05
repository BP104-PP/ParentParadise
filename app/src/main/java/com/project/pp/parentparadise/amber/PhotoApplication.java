package com.project.pp.parentparadise.amber;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class PhotoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(getApplicationContext());
    }
}

