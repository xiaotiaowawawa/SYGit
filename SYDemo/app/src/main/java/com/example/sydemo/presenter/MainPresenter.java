package com.example.sydemo.presenter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.sydemo.ui.mainactivity.Contract;
import com.example.sydemo.ui.notification.NotificationHelper;
import com.example.sydemo.view.BaseView;

public class MainPresenter<T extends BaseView> extends Presenter<T> implements Contract.MainPresenter {
    public MainPresenter(T baseView) {
        super(baseView);
    }
    int i=0;
    Long  totalLength;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    public void onCreate() {
         Log.i("msg1","presenter");

    }

    //然后通过一个函数来申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
