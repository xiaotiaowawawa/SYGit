package com.example.sydemo.ui.myui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChildHelper;

public class Linerlayout extends LinearLayout {
    public Linerlayout(Context context) {
        super(context);
    }

    public Linerlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Linerlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getPaddingLeft() {

        return super.getPaddingLeft();
    }
}
