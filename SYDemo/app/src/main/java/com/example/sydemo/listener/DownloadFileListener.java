package com.example.sydemo.listener;

public interface DownloadFileListener {
    public void start(String content);
    public void process(int currentProgress);
    public void pause(String content);
    public void success(String content);
    public void error(String content);
    public void destory();
}
