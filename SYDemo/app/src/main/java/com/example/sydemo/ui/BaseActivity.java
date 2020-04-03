package com.example.sydemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sydemo.MyApplication;
import com.example.sydemo.R;
import com.example.sydemo.presenter.BasePresenter;
import com.example.sydemo.ui.toolbar.AbstractToolbarBuilder;
import com.example.sydemo.ui.toolbar.BaseToolbarBuilder;
import com.example.sydemo.ui.toolbar.BaseToolbarProxyDao;
import com.example.sydemo.ui.toolbar.DefaultToolbarStyleHelper;
import com.example.sydemo.ui.toolbar.ToolbarDao;
import com.example.sydemo.ui.toolbar.ToolbarStyleDao;
import com.example.sydemo.ui.toolbar.ToolbarStyleHelper;
import com.example.sydemo.view.BaseView;

import java.lang.ref.WeakReference;

public abstract class  BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView<T> , ToolbarDao {
    private T mpresenter;
    private View layoutView;
    private BaseToolbarProxyDao<Toolbar> proxy;
    private ToolbarStyleDao<Toolbar> toolbarStyleHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCustomerDesnity(this);
        layoutView= getLayoutInflater().inflate(getLayoutRes(),(ViewGroup)findViewById(android.R.id.content),true);
        getProxyToolbar();
        setSupportActionBar(getToolbar());
        createToolbarHelper(proxy);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mpresenter=setPresenter();
        getLifecycle().addObserver(mpresenter);
        initView(layoutView);
        Log.i("msg1","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public abstract int getLayoutRes();
    public abstract void initView(View layoutView);

    private BaseToolbarProxyDao getProxyToolbar(){
        BaseToolbarProxyDao<Toolbar> proxy=new BaseToolbarBuilder<Toolbar>().withContext(this).withResId(getToolbarRes()).builde();
        setProxy(proxy);
        return proxy;
    }
    public  BaseToolbarProxyDao getToolbarProxy(){
        return proxy;
    }
    public int getToolbarRes(){
        return R.layout.base_basetoolbar;
}
    private void setProxy(BaseToolbarProxyDao proxy) {
        this.proxy = proxy;
    }

    public Toolbar getToolbar(){
        return proxy.getToolbar();
    }

    public void setToolbarStyleHelper(ToolbarStyleHelper<Toolbar> toolbarStyleHelper) {
        this.toolbarStyleHelper = toolbarStyleHelper;
    }
    public void createToolbarHelper(BaseToolbarProxyDao proxy){
        toolbarStyleHelper=new DefaultToolbarStyleHelper<>(proxy);
    }
    public ToolbarStyleDao<Toolbar> getToolbarStyleHelper() {
        return toolbarStyleHelper;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public View getMainView() {
        return layoutView;
    }

    @Override
    public View getToolbarParentView() {
        return getMainView();
    }
    public void hideMainView(){
        if (getMainView().getVisibility()==View.VISIBLE)
        getMainView().setVisibility(View.GONE);
    }
    public void showMainView(){
        if (getMainView().getVisibility()==View.GONE)
        getMainView().setVisibility(View.VISIBLE);
    }
}
