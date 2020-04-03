package com.example.sydemo.ui.myservice;


import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.sydemo.db.DataBaseHelper;
import com.example.sydemo.db.FileInfoDBHelper;
import com.example.sydemo.db.IDBgetHelper;
import com.example.sydemo.model.dlfilemodel.IDownManagerAc;
import com.example.sydemo.model.dlfilemodel.IDownloadManager;
import com.example.sydemo.model.dlfilemodel.DownloadManager;
import com.example.sydemo.model.dlfilemodel.IFileInfo;
import com.example.sydemo.ui.notification.BaseNotification;

public class DownFileService extends Service {
    private BaseNotification baseNotification;
    private IDownloadManager iDownloadManager;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private IDBgetHelper<IFileInfo> fileInfoDBHelper;
    public DownFileService() {

    }

    public SQLiteOpenHelper setSqLiteOpenHelper() {
        if (sqLiteOpenHelper==null) {
            sqLiteOpenHelper = new DataBaseHelper(DownFileService.this, "DownFileService");
            if (fileInfoDBHelper==null){
                fileInfoDBHelper=new FileInfoDBHelper(sqLiteOpenHelper);
            }else {
                fileInfoDBHelper.setOpenHelper(sqLiteOpenHelper);
            }
        }
        return sqLiteOpenHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iDownloadManager=new DownloadManager();
        setSqLiteOpenHelper();
        iDownloadManager.resetFileInfo(fileInfoDBHelper);

        sqLiteOpenHelper=null;
        Log.i("msg1","执行构造");
        Log.i("msg1","再次create................");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IDownloadManager getiDownloadManager() {
        return iDownloadManager;
    }
public IDownManagerAc getIdownManagerForActivity(){
     return (IDownManagerAc)iDownloadManager;
}
    @Override
    public IBinder onBind(Intent intent) {
      return new DownloadBinder();
    }
    public class DownloadBinder extends Binder {
       public DownFileService getDownFileService(){
           return DownFileService.this;
       }
    }

    @Override
    public void onDestroy() {
        Log.i("msg1","执行了service的destory方法");

        iDownloadManager.storeFileInfo(fileInfoDBHelper);
        Log.i("msg1","执行了service的close方法");
        fileInfoDBHelper.close();
        sqLiteOpenHelper=null;
        iDownloadManager.destory();
        super.onDestroy();
    }
}
