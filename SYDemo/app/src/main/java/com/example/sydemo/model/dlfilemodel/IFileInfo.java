package com.example.sydemo.model.dlfilemodel;

public interface IFileInfo {
    public void changeStatueToIsloading();
    public void changeStatueToPause();
    public void changeStatueToSuccess();
    public void changeStatueStatueToError();
    public Long getProcess();
    public void setProccess(Long process);
    public String getFileAllPath();
    public String getFilePath();
    public String getFileName();
    public Long getCurrentProcess();
    public void setTotalLength(long totalLength);
    public  String getFileUrl();
    public long getTotalLength() ;
    public int getStatue();
    public boolean isSuccess();
    public long getCode();
}
