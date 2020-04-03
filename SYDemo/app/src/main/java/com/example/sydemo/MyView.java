package com.example.sydemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int id=canvas.saveLayerAlpha(0,0,200,200,Canvas.ALL_SAVE_FLAG);
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(100,100,100,paint);
        Paint paint1=new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(120,120,120,paint);
        canvas.restoreToCount(id);
    }
}
