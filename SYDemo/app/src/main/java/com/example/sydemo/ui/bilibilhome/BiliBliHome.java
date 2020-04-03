package com.example.sydemo.ui.bilibilhome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sydemo.R;
import com.example.sydemo.presenter.BilibiliHomePresenter;
import com.example.sydemo.ui.BaseActivity;
import com.example.sydemo.ui.toolbar.ToolbarViewHelper;
import com.example.sydemo.ui.toolbar.ToolbarViewHelperDao;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

public class BiliBliHome extends BaseActivity<BilibiliHomePresenter> implements BilibiliHomeContract.IBilibiliHomeView<BilibiliHomePresenter>{
    @Override
    public int getLayoutRes() {
        return R.layout.activity_bili_bli_home;
    }
    private AlertDialog.Builder builder1;
    @Override
    public void initView(View layoutView) {
      //  ToolbarViewHelperDao<Toolbar> toolbarViewHelper=new ToolbarViewHelper<>(getProxyToolbar());
       SimpleDraweeView simpleDraweeView=(SimpleDraweeView)getToolbarProxy().getView(R.id.drawee_img);
        simpleDraweeView.setImageURI("https://c-ssl.duitang.com/uploads/item/201704/10/20170410095843_SEvMy.jpeg");
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable())
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        builder1=new AlertDialog.Builder(this,R.style.Translucent_NoTitle).setView(LayoutInflater.from(this).inflate(R.layout.base_dialog,null,true));
        builder1.create().show();
    }

    @Override
    public BilibiliHomePresenter setPresenter() {
        return new BilibiliHomePresenter(this);
    }

    @Override
    public void show() {

    }

    @Override
    public int getToolbarRes() {
        return R.layout.bilibilihome_toolbar;
    }
    @Override
    public View getToolbarParentView() {
        return getMainView().findViewById(R.id.home_main);
    }
}
