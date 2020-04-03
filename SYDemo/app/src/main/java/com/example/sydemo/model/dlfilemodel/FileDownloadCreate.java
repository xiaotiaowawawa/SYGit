package com.example.sydemo.model.dlfilemodel;

import com.example.sydemo.listener.DownloadFileListener;

public class FileDownloadCreate {
    private DownloadFileListener downloadFileListener;
    private IFileManager IFileManager;

    public FileDownloadCreate(DownloadFileListener downloadFileListener, IFileManager IFileManager) {
        this.downloadFileListener = downloadFileListener;
        this.IFileManager = IFileManager;
    }

    public com.example.sydemo.model.dlfilemodel.IFileManager getIFileManager() {
        return IFileManager;
    }

    public void setIFileManager(com.example.sydemo.model.dlfilemodel.IFileManager IFileManager) {
        this.IFileManager = IFileManager;
    }

    public DownloadFileListener getDownloadFileListener() {
        return downloadFileListener;
    }

    public void setDownloadFileListener(DownloadFileListener downloadFileListener) {
        this.downloadFileListener = downloadFileListener;
    }
}
