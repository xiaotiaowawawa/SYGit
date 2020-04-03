package com.example.sydemo.presenter;

import com.example.sydemo.ui.bilibilhome.BilibiliHomeContract;
import com.example.sydemo.view.BaseView;

public class BilibiliHomePresenter<T extends BilibiliHomeContract.IBilibiliHomeView> extends Presenter<T> implements BilibiliHomeContract.IBilibiliHomePresenter<T>{
    public BilibiliHomePresenter(T baseView) {
        super(baseView);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
