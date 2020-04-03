package com.example.sydemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sydemo.model.dlfilemodel.DownFileInfo;
import com.example.sydemo.model.dlfilemodel.IFileInfo;

public class FileInfoDBHelper extends BaseIDBgetHelper<IFileInfo>{
    public FileInfoDBHelper(SQLiteOpenHelper sqLiteOpenHelper) {
        super(sqLiteOpenHelper);
        super.tableName="downloadfile";
    }
    @Override
    public DownFileInfo solveCurorToModel(Cursor cursor,boolean b) {
        if (b) {
          //  Log.i("msg1","解析");
            DownFileInfo downFileInfo = new DownFileInfo();
            long currentProcess = cursor.getLong(1);
           // Log.i("msg1","currentget"+currentProcess);
            downFileInfo.setCurrentProcess(currentProcess);

            String fileName = cursor.getString(2);
            if (fileName != null)
                downFileInfo.setFileName(fileName);
            String fileAllPath = cursor.getString(3);
            if (fileAllPath != null) {
                downFileInfo.setFileAllPath(fileAllPath);
               String[]r= fileAllPath.split("/");
               String t="";
                for (int i=0;i<r.length-1;i++)
                t+=(r[i]+"/");
                downFileInfo.setFilePath(t);
            }
            Integer statue = cursor.getInt(4);
            downFileInfo.setStatue(statue);
            long totalLength = cursor.getLong(5);
            downFileInfo.setTotalLength(totalLength);
       //     Log.i("msg1","totallength"+totalLength);
            String fileUrl = cursor.getString(6);
            if (fileUrl != null)
                downFileInfo.setFileUrl(fileUrl);
            return downFileInfo;
        }
        return  null;
      //  }
       // return null;
    }



    @Override
    public IFileInfo isContainModel(IFileInfo model) {
        return isContainId(((Long)model.getCode()).intValue());
    }

    @Override
    public ContentValues solveModelToContentValues(ContentValues contentValues,IFileInfo downFileInfo) {
        if (downFileInfo != null) {
           // int code =((Long)downFileInfo.getCode()).intValue();
            contentValues.put("_id", downFileInfo.getCode());
            contentValues.put("currentProcess", downFileInfo.getCurrentProcess());
            Log.i("msg1",downFileInfo.getCurrentProcess()+"current");
            contentValues.put("fileName", downFileInfo.getFileName());
            contentValues.put("fileAllPath", downFileInfo.getFileAllPath());
            contentValues.put("statue", downFileInfo.getStatue());
            contentValues.put("totalLength", downFileInfo.getTotalLength());
            contentValues.put("fileUrl", downFileInfo.getFileUrl());
        }
        Log.i("msg1","执行solveModelToContentValues");
        return contentValues;
    }

    @Override
    public void updata(IFileInfo model) {
        if (isContainModel(model)!=null) {
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues1 = solveModelToContentValues(contentValues, model);
            contentValues1.remove("_id");
            update(contentValues1, model);
        }
    }

    @Override
    public void update(ContentValues contentValues, IFileInfo model) {
        if (contentValues!=null) {
            Log.i("msg1","执行到更新方法");
            try {
                getWritedatabase().update(tableName, contentValues, "_id=?", new String[]{String.valueOf(model.getCode())});
            }catch (Exception e){
                Log.i("msg1","更新异常");
            }
            Log.i("msg1","更新"+model.getFileName()+"成功");
        }
    }
}
