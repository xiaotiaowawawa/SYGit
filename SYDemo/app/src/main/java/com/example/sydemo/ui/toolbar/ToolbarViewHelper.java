package com.example.sydemo.ui.toolbar;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

public  class ToolbarViewHelper <T extends Toolbar> implements ToolbarViewHelperDao<T>{
    protected BaseToolbarProxyDao<T> proxyDao;
    public ToolbarViewHelper(BaseToolbarProxyDao<T> proxyDao) {
        this.proxyDao = proxyDao;
    }
    @Override
    public void addViewById(int id, View v) {
       ViewGroup parentView=(ViewGroup) proxyDao.getView(id);
       parentView.addView(v);
    }
}
