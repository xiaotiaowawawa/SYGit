package com.example.sydemo.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.sydemo.db.DataBaseHelper;
import com.example.sydemo.db.FileInfoDBHelper;
import com.example.sydemo.db.IDBgetHelper;
import com.example.sydemo.model.dlfilemodel.DownFileInfo;
import com.example.sydemo.model.dlfilemodel.IFileInfo;
import com.example.sydemo.ui.homeactivity.Contract;
import com.example.sydemo.ui.homeactivity.HomeActivity;
import com.example.sydemo.ui.notification.NotificationHelper;
import com.example.sydemo.view.BaseView;

import java.util.List;

public class HomePresenter <T extends BaseView> extends Presenter<T> implements Contract.MainPresenter<T> {
    public HomePresenter(T baseView) {
        super(baseView);
    }
    private FileInfoDBHelper idBgetHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.buildChannel();
        SQLiteOpenHelper openHelper=new DataBaseHelper((HomeActivity)baseView,"DownFileService");
        /*idBgetHelper=new FileInfoDBHelper(openHelper);
         String path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile/";
        // new DownFileInfo(path,"Beyond.zip","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=Beyond.zip");
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"activate.bat","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=activate.bat"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"douyu.exe","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=douyu.exe"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"eclipse.exe","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=eclipse.exe"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"gcc-7.2.0.tar.gz","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=gcc-7.2.0.tar.gz"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"language.mix","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=language.mix"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"pc_driver.rar","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=pc_driver.rar"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"Theme.mix","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=Theme.mix"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"timg.jpg","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=timg.jpg"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"Wdt.mix","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=Wdt.mix"),false);
        idBgetHelper.insertModelByModel(new DownFileInfo(path,"youdao.exe","http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=youdao.exe"),false);
       idBgetHelper.close();*/
     //  idBgetHelper.insertModelByModel(new DownFileInfo("path","fileName","fileUrl"));
     /* DownFileInfo downFileInfo=idBgetHelper.getDBModelByIdWithAllFile(-855009709);
        Log.i("msg1",downFileInfo.getTotalLength()+"");
        idBgetHelper.deleteModelById(1);
        DownFileInfo downFileInfo2=idBgetHelper.getDBModelByIdWithAllFile(1);
        Log.i("msg1",downFileInfo2+"");*/
       /* ContentValues contentValues1=new ContentValues();
        contentValues1.put("_id", 1);
        contentValues1.put("currentProcess", 2);
        contentValues1.put("fileName", "hello");
        contentValues1.put("fileAllPath", "path");
        contentValues1.put("statue", 1);
        contentValues1.put("totalLength", 333);
        contentValues1.put("fileUrl", "url");
        ContentValues contentValues2=new ContentValues();
        contentValues2.put("_id", 2);
        contentValues2.put("currentProcess", 2);
        contentValues2.put("fileName", "hello");
        contentValues2.put("fileAllPath", "path");
        contentValues2.put("statue", 1);
        contentValues2.put("totalLength", 333);
        contentValues2.put("fileUrl", "url");
        ContentValues contentValues3=new ContentValues();
        contentValues3.put("_id", 3);
        contentValues3.put("currentProcess", 2);
        contentValues3.put("fileName", "hello");
        contentValues3.put("fileAllPath", "path");
        contentValues3.put("statue", 1);
        contentValues3.put("totalLength", 333);
        contentValues3.put("fileUrl", "url");
        openHelper.getWritableDatabase().insert("downloadfile",null,contentValues1);
        openHelper.getWritableDatabase().insert("downloadfile",null,contentValues2);
        openHelper.getWritableDatabase().insert("downloadfile",null,contentValues3);
        List<IFileInfo>iFileInfoList=idBgetHelper.getAllModel();*/
     /*  String sql="select * from downloadfile";
        Cursor cursor=openHelper.getReadableDatabase().rawQuery(sql,null);
        while (cursor.moveToNext()){
            idBgetHelper.deleteModelById((int)cursor.getLong(0));
        }*/
        /* idBgetHelper.deleteModelById(-855009709);
        idBgetHelper.deleteModelById(1);
        idBgetHelper.deleteModelById(2);
        idBgetHelper.deleteModelById(3);

     /*  Cursor cursor=openHelper.getReadableDatabase().rawQuery("select * from downloadfile where _id in (1)",null);//query("downloadfile",new String[]{"totalLength"},"_id=?",new String[]{"1"},null,null,null);
        while (cursor.moveToNext()){
            long r=cursor.getLong(1);
            Log.i("msg1",r+"");
        }*/
     //   Log.d("msg1",downFileInfo.getFileAllPath());
    }
}
