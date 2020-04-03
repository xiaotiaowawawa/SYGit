package com.example.sydemo.presenter;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BasePresenter extends LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() ;

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart();

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() ;

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause();

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop();

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() ;
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onAny() ;


}
