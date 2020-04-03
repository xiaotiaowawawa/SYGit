package com.example.sydemo.view;
import android.content.Context;

import com.example.sydemo.presenter.BasePresenter;
public interface BaseView <T extends BasePresenter>{
  public T setPresenter();
  public Context getContext();
}
