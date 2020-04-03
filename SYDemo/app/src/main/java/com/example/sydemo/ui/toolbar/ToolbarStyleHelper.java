package com.example.sydemo.ui.toolbar;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;

public class ToolbarStyleHelper<T extends Toolbar> implements ToolbarStyleDao<T> {
    protected BaseToolbarProxyDao<T> proxyDao;
    public ToolbarStyleHelper(BaseToolbarProxyDao<T> proxyDao) {
        this.proxyDao = proxyDao;
    }
    public ToolbarStyleDao<T> setHeight(int viewId,int height){
        View v=proxyDao.getView(viewId);
        ViewGroup.LayoutParams layoutParams=v.getLayoutParams();
        layoutParams.height=height;
        v.setLayoutParams(layoutParams);
        return this;
    }
    public  ToolbarStyleDao<T>  setbackgroundColor(int viewId,@ColorInt int color){
        View v=proxyDao.getView(viewId);
        v.setBackgroundColor(color);
        return this;
    }

    @Override
    public ToolbarStyleDao<T> setWidth(int viewId, int width) {
        View v=proxyDao.getView(viewId);
        ViewGroup.LayoutParams layoutParams=v.getLayoutParams();
        layoutParams.width=width;
        v.setLayoutParams(layoutParams);
        return this;
    }

    @Override
    public ToolbarStyleDao<T> showLoading() {
        return this;
    }

    @Override
    public ToolbarStyleDao<T> hideLoading() {
        return this;
    }
}
