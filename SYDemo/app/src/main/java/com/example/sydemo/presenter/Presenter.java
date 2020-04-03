package com.example.sydemo.presenter;

import com.example.sydemo.view.BaseView;

public class Presenter<T extends BaseView> implements BasePresenter{
    protected T baseView;

    public Presenter(T baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onAny() {

    }
}
