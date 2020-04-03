package com.example.sydemo.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static int currentVersion=1;
    public DataBaseHelper(@Nullable Context context, @Nullable String name){
        super(context, name, null, currentVersion);
    }
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDownloadFileSql="create table downloadfile(_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,currentProcess integer,fileName TEXT,fileAllPath TEXT,statue int,totalLength integer,fileUrl TEXT)";
        db.execSQL(createDownloadFileSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
