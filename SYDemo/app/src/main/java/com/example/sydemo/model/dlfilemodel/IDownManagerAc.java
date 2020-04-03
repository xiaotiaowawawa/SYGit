package com.example.sydemo.model.dlfilemodel;

import androidx.core.widget.ContentLoadingProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IDownManagerAc {
    public void updateProgessBar(ContentLoadingProgressBar contentLoadingProgressBar,IFileManager iFileManager);
    public void updateProgessBar(ContentLoadingProgressBar contentLoadingProgressBar,int code,long total,long current);
    public void removeProgressBar(int code);
    public List<IFileInfo> getListIFileInfoInDb();
    public ArrayList<IFileInfo> changeFileDownloadCreateToProgress(Map<Integer,ContentLoadingProgressBar> map);
    public void addFileDownloadCreate(String filePath, String url, String fileName,ContentLoadingProgressBar contentLoadingProgressBar);
    public void addFileDownloadCreate(String filePath, Integer code, String url,String fileName,ContentLoadingProgressBar contentLoadingProgressBar);
    public void addFileDownloadCreate(IFileInfo iFileInfo,ContentLoadingProgressBar contentLoadingProgressBar);
    public void pauseCall(int code);
}
