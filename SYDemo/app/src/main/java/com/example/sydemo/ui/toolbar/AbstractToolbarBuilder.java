package com.example.sydemo.ui.toolbar;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.sydemo.ui.ButtonOnclick;

public abstract class AbstractToolbarBuilder<T> {
    public abstract AbstractToolbarBuilder withContext(ToolbarDao context);
    public abstract AbstractToolbarBuilder setParentView(View parentView);
    public abstract AbstractToolbarBuilder withResId(int resId);
 //   public abstract AbstractToolbarBuilder regiestOnclick(int viewId , ButtonOnclick onclick);
    //public abstract T getToolbarView();
    public abstract BaseToolbarProxyDao<T> builde();

}
