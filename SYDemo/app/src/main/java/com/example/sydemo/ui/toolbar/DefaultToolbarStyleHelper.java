package com.example.sydemo.ui.toolbar;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.sydemo.R;

public class DefaultToolbarStyleHelper<T extends Toolbar> extends ToolbarStyleHelper<T>{

    public DefaultToolbarStyleHelper(BaseToolbarProxyDao<T> proxyDao) {
        super(proxyDao);
    }

    @Override
    public ToolbarStyleDao<T> setHeight(int viewId, int height) {
        return super.setHeight(viewId, height);
    }

    @Override
    public ToolbarStyleDao<T> setbackgroundColor(int viewId, int color) {
        return super.setbackgroundColor(viewId, color);
    }

    @Override
    public ToolbarStyleDao<T> setWidth(int viewId, int width) {
        return super.setWidth(viewId, width);
    }
    @Override
    public ToolbarStyleDao<T> showLoading() {
        View v=  super.proxyDao.getView(R.id.toolbar_progress);
        if (v==null){
            return this;
        }
        if (v.getVisibility()==View.GONE){
            v.setVisibility(View.VISIBLE);
        }
        return super.showLoading();
    }
    @Override
    public ToolbarStyleDao<T> hideLoading() {
        View v=  super.proxyDao.getView(R.id.toolbar_progress);
        if (v==null){
            return this;
        }
        if (v.getVisibility()==View.VISIBLE){
            v.setVisibility(View.GONE);
        }
        return super.hideLoading();
    }

}
