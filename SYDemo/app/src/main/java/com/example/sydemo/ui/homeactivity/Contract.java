package com.example.sydemo.ui.homeactivity;

import com.example.sydemo.presenter.BasePresenter;
import com.example.sydemo.view.BaseView;

public interface Contract {
    public interface MainView<T extends BasePresenter> extends BaseView<T> {

    }
    public  interface MainPresenter<T extends BaseView> extends BasePresenter{

    }
}
