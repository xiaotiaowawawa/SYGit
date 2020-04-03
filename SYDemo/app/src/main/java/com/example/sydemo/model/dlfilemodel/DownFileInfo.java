package com.example.sydemo.model.dlfilemodel;

import android.util.Log;

public class DownFileInfo implements IFileInfo{
    private Long currentProcess;
    private String fileName;
    private String filePath;
    private String fileAllPath;
    private int statue;
    private long totalLength;
    private String fileUrl;
    private long code=-1;
    public final static int isloading=0x01;
    public final static int pause=0x02;
    public final static int success=0x04;
    public final static int error=0x08;

    public void setFileUrl(String fileUrl) {
        if (code==-1)
            code=fileUrl.hashCode();
        this.fileUrl = fileUrl;
    }

    public DownFileInfo() {

    }

    public void changeStatueToIsloading(){
        statue&=isloading;
        statue|=isloading;
    }
    public void changeStatueToPause(){
        statue&=pause;
        statue|=pause;
    }
    public void changeStatueToSuccess(){
        statue&=success;
        statue|=success;
    }
    public void changeStatueStatueToError(){
        statue&=error;
        statue|=error;
    }
    public DownFileInfo( String filePath,String fileName,String fileUrl) {
        this.currentProcess = 0L;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileUrl=fileUrl;
        this.statue=0;
        fileAllPath=filePath+fileName;
        Log.i("msg1",fileAllPath);
        code=fileUrl.hashCode();
    }

    public long getCode() {
        return code;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileAllPath(String fileAllPath) {
        this.fileAllPath = fileAllPath;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    @Override
    public Long getProcess() {
        return currentProcess;
    }

    @Override
    public void setProccess(Long process) {
        this.currentProcess = process;
    }

    public Long getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Long currentProcess) {
        this.currentProcess = currentProcess;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileAllPath() {
        return fileAllPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    @Override
    public boolean isSuccess() {
        if ((statue&(success))==success){
            return true;
        }else if (currentProcess==totalLength){
           changeStatueToSuccess();
            return true;
        }
        return false;
    }
}
