package com.example.sydemo.listener;

import androidx.core.widget.ContentLoadingProgressBar;

public interface IProgressListener<T extends ContentLoadingProgressBar> extends DownloadFileListener{
    public T ContentLoadingProgressBar();
    public void  setContentLoadingProgressBar(T contentLoadingProgressBar);
    public void setCurrentPro(int currentPro);
}
