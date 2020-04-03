package com.example.sydemo.ui.toolbar;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;

public interface ToolbarStyleDao<T extends Toolbar> {
    public ToolbarStyleDao<T> setHeight(int viewId,int height);
    public ToolbarStyleDao<T> setbackgroundColor(int viewId,@ColorInt int color);
    public ToolbarStyleDao<T> setWidth(int viewId,int width);
    public ToolbarStyleDao<T> showLoading();
    public ToolbarStyleDao<T> hideLoading();
}
