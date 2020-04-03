package com.example.sydemo.ui.notification;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.sydemo.R;


public class BaseNotification {
    private Notification notification;
    RemoteViews views;
    private  NotificationCompat.Builder builder;

    public NotificationCompat.Builder getBuilder() {
        return builder;
    }

    public Notification getNotification() {
        return notification;
    }

    public void notificationBuild(Context context, int size){
      //  views = new RemoteViews(context.getPackageName(), R.layout.notification);
       // notification= new NotificationCompat.Builder(context).setContent(views).setSmallIcon(R.drawable.back_black).setAutoCancel(true).build();
      //  notification.contentView.setTextViewText(R.id.downloadSize,"正在下载图片");
       // notification.contentView.setProgressBar(R.id.progressSize,100,0,false);
        builder = new NotificationCompat.Builder(context,"notification")
                .setContentTitle("通知1").setProgress(100,70,false).setContentText("正在下载");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//Android4.1以上
            builder.setSmallIcon(R.drawable.ic_launcher_background);
        } // 创建通知的标题.
        notification=builder.build();
              // 创建通知的内容
               /* .setSmallIcon(R.drawable.back_white) // 创建通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.back_white)).setContent(views)*/

    }
    public void update(int size){
        builder.setProgress(100,size,true);
        notification=builder.build();
    }
}
