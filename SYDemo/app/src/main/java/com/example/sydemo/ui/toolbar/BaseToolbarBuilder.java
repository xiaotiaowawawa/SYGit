package com.example.sydemo.ui.toolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;

import com.example.sydemo.R;
import com.example.sydemo.ui.ButtonOnclick;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

public class BaseToolbarBuilder<T extends View> extends AbstractToolbarBuilder<T> implements BaseToolbarProxyDao<T> {
    @NotNull
    private T baseToolbar;
    @NotNull
    private ToolbarDao context;
    private int resId;
    @NotNull
    private View parentView;
    Map<Integer,View>viewMap=new WeakHashMap<Integer,View>();

    @Override
    public BaseToolbarBuilder<T> withContext(ToolbarDao context) {
        this.context=context;
        return this;
    }

    @Override
    public BaseToolbarBuilder<T>  withResId(int resId) {
        this.resId=resId;
        return this;
    }


    private BaseToolbarProxyDao  regiestOnclickprivate(int viewId,final ButtonOnclick onclick) {
        getView(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.onclick(v);
                }
            });
        return this;
    }

    @Override
    public BaseToolbarProxyDao regiestOnclick(int viewId, ButtonOnclick onclick) {
        return regiestOnclickprivate(viewId,onclick);
    }

    @Override
    public T getToolbar() {
        return getToolbarView();
    }

    @Override
    public View getView(int viewId) {
        return getViewprivate(viewId);
    }

    private T getToolbarView() {
        if (resId==0){
            throw new NullPointerException("没有设置toolbar的id");
        }
        return baseToolbar;
    }
    private View getViewprivate(int viewId){
        if (resId==0){
            throw new NullPointerException("没有设置toolbar的id");
        }
        View v;
        if (viewMap.containsKey(viewId)){
           if ((v=viewMap.get(viewId))!=null)
            return v;
        }
        if ((v = baseToolbar.findViewById(viewId)) == null) {
              return null;
        }
        viewMap.put(viewId, v);
        return v;
    }
    @Override
    public BaseToolbarProxyDao<T>  builde() {
        if (resId==0){
            throw new NullPointerException("没有设置toolbar的id");
        }
        parentView=context.getToolbarParentView();
        baseToolbar=(T) LayoutInflater.from(context.getContext()).inflate(resId,(ViewGroup) parentView,false);
        ((ViewGroup) parentView).addView(baseToolbar,0);
        return this;
    }
    @Override
    public BaseToolbarBuilder<T>  setParentView(@NotNull View parentView) {
        this.parentView=parentView;
        return this;
    }
   private class BaseToolbarProxy<T> implements BaseToolbarProxyDao<T>{
        @Override
        public BaseToolbarProxyDao regiestOnclick(int viewId,final ButtonOnclick onclick) {
            return regiestOnclick(viewId,onclick);
        }
        @Override
        public T getToolbar() {
            return (T)baseToolbar;
        }

        @Override
        public View getView(int viewId) {
            return getView(viewId);
        }
    }
}
