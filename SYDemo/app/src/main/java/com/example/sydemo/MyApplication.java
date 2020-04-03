package com.example.sydemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    public static Context context;
    public static int designWidth=360;
    public static void setCustomerDesnity(Context activity){
        final DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        final int targetDesnity=displayMetrics.widthPixels/designWidth;
        final float scaleDesnity=targetDesnity*(displayMetrics.scaledDensity/displayMetrics.density);
        final DisplayMetrics displayMetrics1=activity.getResources().getDisplayMetrics();
        displayMetrics1.density=targetDesnity;
        displayMetrics1.densityDpi=160*targetDesnity;
        displayMetrics1.scaledDensity=scaleDesnity;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        Fresco.initialize(this);
    }

    @Override
    public void onTerminate() {
        Log.i("msg1","onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        Log.i("msg1","onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.i("msg1","onTrimMemory"+level);
        super.onTrimMemory(level);
    }

}
