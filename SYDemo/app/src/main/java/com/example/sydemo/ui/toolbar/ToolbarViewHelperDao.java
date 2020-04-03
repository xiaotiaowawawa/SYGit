package com.example.sydemo.ui.toolbar;


import android.view.View;

import androidx.appcompat.widget.Toolbar;

public interface ToolbarViewHelperDao<T extends Toolbar> {

    public void addViewById(int id,View v);
}
