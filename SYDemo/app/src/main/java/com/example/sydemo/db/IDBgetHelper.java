package com.example.sydemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public interface IDBgetHelper<T> {
    public T getDBModelByKeyId(int id, String content[]);
    public T getDBModelByIdWithAllFile(int id);
    public List<T> getAllModel();
    public List<T> getListDBModelByListId(List<Integer>integerList);
    public List<T> getListDBModelByListIdWithCriteria(List<Integer>integerList,String[]criteria);
    public IDBgetHelper setSQLiteWriteDataBase(SQLiteDatabase sqLiteDataBase);
    public IDBgetHelper setSQLiteReadDataBase(SQLiteDatabase sqLiteDataBase);
    public void insertModelByModel(T model,boolean isUpdate);
    public void deleteModelById(int id);
    public void setTableName(String name);
    public void deleteModelByListId(List<Integer>integerList);
    public void destory();
    public void setOpenHelper(SQLiteOpenHelper sqLiteOpenHelper);
    public void close();
}
