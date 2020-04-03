package com.example.sydemo.listener;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.sydemo.MyApplication;
import com.example.sydemo.R;
import com.example.sydemo.ui.notification.NotificationHelper;

public class NoticationListener implements DownloadFileListener{
    private int notifyId;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private Context context;
    private Handler handler=new Handler();
    public NoticationListener(int notifyId,Context context,String content,int icon,String channelId) {
        this.notifyId = notifyId;
        this.context=context;
       builder=new NotificationCompat.Builder(context,channelId).setContentText(content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//Android4.1以上
            builder.setSmallIcon(icon);
        }
        builder.setProgress(100,0,false);
        notification=builder.build();
    }
    public NoticationListener(int notifyId,NotificationCompat.Builder builder,Context context) {
        this.notifyId = notifyId;
        this.builder = builder;
        this.context=context;
        notification=builder.build();
    }
    @Override
    public void start(String content) {

    }

    @Override
    public void process(int currentProgress) {
        NotificationHelper.updateProgressDefault(currentProgress,builder,notifyId);
        //Log.i("msg1",""+currentProgress);
    }

    @Override
    public void pause(String content) {

    }

    @Override
    public void success(final String content) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                builder.setContentText(content);
            }
        });
    }

    @Override
    public void error(final String content) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.context,content,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void destory() {
        notification=null;
        if (handler!=null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //要在该线程取消
                    NotificationHelper.remoceNotification(notifyId);
                }
            });
            handler.removeCallbacks(null);
        }
        context=null;
        handler=null;
    }
}
