package com.example.sydemo.model.dlfilemodel;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.core.widget.ContentLoadingProgressBar;

import com.example.sydemo.db.IDBgetHelper;
import com.example.sydemo.model.dlfilemodel.FileDownloadCreate;
import com.example.sydemo.model.dlfilemodel.IFileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IDownloadManager {
    public void startNewDownLoad(String url);
    public void pauseAllDownLoad();
    public void pauseCall(int code);
    public void setMaxDownload(int count);
    public void addFileDownloadCreate(String filePath, String url, String fileName,ContentLoadingProgressBar contentLoadingProgressBar);
    public void addFileDownloadCreate(String filePath, Integer code, String url,String fileName,ContentLoadingProgressBar contentLoadingProgressBar);
    public void addFileDownloadCreate(IFileInfo iFileInfo,ContentLoadingProgressBar contentLoadingProgressBar);
    public void changeFileDownloadCreateToNotify(Context context);
    public ArrayList<IFileInfo> changeFileDownloadCreateToProgress(Map<Integer,ContentLoadingProgressBar>map);
    public List<IFileInfo> getDownloadingFileInfoList();
    public void destory();
    public Map<Integer, FileDownloadCreate>getFileDownloadCreateMap();
    public void resetFileInfo(IDBgetHelper<IFileInfo> fileInfoDBHelper);
    public void storeFileInfo(IDBgetHelper<IFileInfo> fileInfoDBHelper);
    public List<IFileInfo> getListIFileInfoInDb();


}
