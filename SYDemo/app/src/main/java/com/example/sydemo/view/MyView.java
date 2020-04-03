package com.example.sydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.sydemo.R;

public class MyView extends View {
     DisplayMetrics displayMetrics;
     Canvas canvass;
    public MyView(Context context) {
        super(context);
        displayMetrics=context.getResources().getDisplayMetrics();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        displayMetrics=context.getResources().getDisplayMetrics();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        displayMetrics=context.getResources().getDisplayMetrics();
    }

    @Override
    public void draw(Canvas canvas) {
        canvass=canvas;
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        float width= displayMetrics.density*50;
        canvas.clipRect(new Rect(0,0,(int)width,(int)width));
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawRect(0,0,width*2,width*2,paint);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(width,width,width,paint);
        super.draw(canvas);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      /*  Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.save();
       float width= displayMetrics.density*50;
        //canvas.translate(80,80);
       /* Rect rect=new Rect(80,80,80,80);
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.clipRect(rect);
        canvas.drawRect(rect,paint);*/
        //canvas.restore();
        /*paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(width,width,width,paint);*/
    }
}
