package com.example.sydemo.ui.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.sydemo.MyApplication;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    public static  NotificationManager notificationManager=(NotificationManager) MyApplication.context.getSystemService(NOTIFICATION_SERVICE);
    public  static void startNotification( Notification notification,int id){

        notificationManager.notify(id, notification);
    }
    public static void updateProgress(int size,BaseNotification baseNotification,int id){
        NotificationCompat.Builder builder=baseNotification.getBuilder();
        builder.setProgress(100,size,false);
       builder.setAutoCancel(true);
       Notification notification= builder.build();
       // notification.flags|=Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(id,notification);
    }
    public static void updateProgressDefault(int size,  NotificationCompat.Builder builde,int id){ ;
        builde.setProgress(100,size,false);
        notificationManager.notify(id,builde.build());
    }
    public static void  buildChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            String channelId="notification";
            String channelName="通知栏";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            createNotificationchannel(channelId,channelName,importance);
        }
    }
    public static void remoceNotification(int id){
        notificationManager.cancel(id);

    }
    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationchannel(String channelId, String channelName, int importance){
        NotificationManager notificationManager=(NotificationManager) MyApplication.context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel=new NotificationChannel(channelId,channelName,importance);
        notificationManager.createNotificationChannel(notificationChannel);
    }


}
