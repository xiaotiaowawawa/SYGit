package com.example.sydemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorSpace;
import android.util.Log;

import com.example.sydemo.model.dlfilemodel.IFileInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class BaseIDBgetHelper<T> implements IDBgetHelper<T> {
    protected String tableName;
    private SQLiteDatabase writedatabase;
    private SQLiteDatabase readdatabase;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private byte isChange=0x00;
    public static byte CHANGEWRITE=0x01;
    public static byte CHANGEREAD=0x02;
    public static byte CHANGEALL=0x03;
    protected String idName="_id";

    public BaseIDBgetHelper(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper=sqLiteOpenHelper;
    }

    public SQLiteDatabase getReaddatabase() {
        if (readdatabase==null)
            readdatabase= sqLiteOpenHelper.getReadableDatabase();
        if ((isChange&CHANGEREAD)==CHANGEREAD){
            readdatabase= sqLiteOpenHelper.getReadableDatabase();
            isChange&=(~CHANGEREAD);
        }
        return readdatabase;
    }

    public SQLiteDatabase getWritedatabase() {
        if (writedatabase==null)
            writedatabase=sqLiteOpenHelper.getWritableDatabase();
        if ((isChange&CHANGEWRITE)==CHANGEWRITE){
            readdatabase= sqLiteOpenHelper.getReadableDatabase();
            isChange&=(~CHANGEWRITE);
        }
        return writedatabase;
    }

    @Override
    public T getDBModelByKeyId(int id, String[] content) {
        Cursor cursor=null;
        if (content==null){
            String sql="select * from "+tableName+" where "+idName+"="+id;
             cursor=getReaddatabase().rawQuery(sql,null);
        }
        else
         cursor=getReaddatabase().query(tableName, content, idName+"=?", new String[]{id+""}, null, null, null);
        boolean b=cursor.moveToNext();
        T model=solveCurorToModel(cursor,b);
        cursor.close();
        return model;
    }
    public abstract T solveCurorToModel(Cursor cursor,boolean can);
    public List<T>solveCurorToListModel(Cursor cursor){
        List<T> modelArray=new ArrayList<T>();
            while (cursor.moveToNext()){
                modelArray.add(solveCurorToModel(cursor,true));
             }
            cursor.close();
      return modelArray;
    }

    @Override
    public List<T> getAllModel() {
        String sql="select * from "+tableName;
        return solveCurorToListModel(getReaddatabase().rawQuery(sql,null));
    }

    @Override
    public T getDBModelByIdWithAllFile(int id) {
        String sql= "select * from " + tableName + " where "+idName+" in ("+id+")";
        Cursor cursor=getReaddatabase().rawQuery(sql, null);
        boolean b=cursor.moveToNext();
        T model=solveCurorToModel(cursor,b);
        cursor.close();
        return model;
    }

    @Override
    public List<T> getListDBModelByListId(List<Integer> integerList) {
        String canshu = getCriteria(integerList);
        if (canshu!=null) {
            String sql = "select * from " + tableName + " where "+idName+" in " + canshu;
            return solveCurorToListModel(getReaddatabase().rawQuery(sql, null));
        }
        return null;
    }

    @Override
    public void destory() {
        sqLiteOpenHelper.close();
        sqLiteOpenHelper=null;
        isChange&=CHANGEALL;
    }
    abstract public  void update(ContentValues contentValues, T model);
    @Override
    public void insertModelByModel( T model,boolean isUpdate) {
        if (model!=null) {
            Log.i("msg1","执行数据库insert方法");
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues1 = solveModelToContentValues(contentValues, model);
            if (isContainModel(model)==null) {
                getWritedatabase().insert(tableName, null, contentValues1);
            }else{
                if (isUpdate){
                    update(contentValues1,model);
                }
            }
        }
    }
    public abstract void updata(T model);
    public T isContainId(int id){
        return getDBModelByKeyId(id,null);
    }
    public abstract T isContainModel(T model);
    public BaseIDBgetHelper<T> setIdName(String idName) {
        this.idName = idName;
        return this;
    }

    public abstract ContentValues solveModelToContentValues(ContentValues contentValues,T model);
    @Override
    public void deleteModelById(int id) {
            getWritedatabase().delete(tableName,idName+"=?",new String[]{""+id});

    }

    @Override
    public List<T> getListDBModelByListIdWithCriteria(List<Integer> integerList, String[] criteria) {
        if (criteria != null) {
            String canshu = getCriteria(integerList);
            String criteriaString=getCriteria(Arrays.asList(criteria));
            if (canshu!=null){
                String sql = "select "+criteriaString+" from " + tableName + " where "+idName+" in " + canshu;
                return solveCurorToListModel(getReaddatabase().rawQuery(sql, null));
            }
            else
                return null;

        }
        return null;
    }

    @Override
    public void deleteModelByListId(List<Integer> integerList) {
        String canshu=getCriteria(integerList);
        if (canshu!=null) {
            String sql = "delete from " + tableName + " where " + idName + " in " + canshu.toString();
            getWritedatabase().rawQuery(sql,null);
        }
    }

    private<T> String getCriteria(List<T> tList){
        if (tList != null && tList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('(');
            stringBuilder.append((tList.get(0) + ""));
            for (int i=1;i<tList.size();i++){
                stringBuilder.append(","+tList.get(i));
            }
            stringBuilder.append(')');
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public void close() {
        destory();
    }

    @Override
    public void setOpenHelper(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper=sqLiteOpenHelper;
    }

    @Override
    public IDBgetHelper setSQLiteWriteDataBase(SQLiteDatabase sqLiteDataBase) {
        this.writedatabase=sqLiteDataBase;
        return this;
    }

    @Override
    public IDBgetHelper setSQLiteReadDataBase(SQLiteDatabase sqLiteDataBase) {
        this.readdatabase=sqLiteDataBase;
        return this;
    }

    @Override
    public void setTableName(String name) {
        this.tableName=name;
    }
}
