package com.example.sydemo.model.dlfilemodel;

import com.example.sydemo.listener.DownloadFileListener;

public interface IProcessBody {
    public void errorDestory(ProgressResponseBody progressResponseBody, IFileManager iFileManager, DownloadFileListener downloadFileListener, int code);

}
