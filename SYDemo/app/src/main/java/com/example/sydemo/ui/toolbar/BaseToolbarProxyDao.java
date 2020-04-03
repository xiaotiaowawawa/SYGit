package com.example.sydemo.ui.toolbar;

import android.view.View;

import com.example.sydemo.ui.ButtonOnclick;

public interface BaseToolbarProxyDao<T >{
    public BaseToolbarProxyDao regiestOnclick(int viewId,final ButtonOnclick onclick);
    public T getToolbar();
    public View getView(int viewId);
}
