package com.example.sydemo.ui.bilibilhome;

import com.example.sydemo.presenter.BasePresenter;
import com.example.sydemo.view.BaseView;

public interface BilibiliHomeContract {
    public interface IBilibiliHomeView<T extends BasePresenter> extends BaseView<T> {
        public void show();
    }
    public  interface IBilibiliHomePresenter<T extends BaseView> extends BasePresenter{

    }
}
