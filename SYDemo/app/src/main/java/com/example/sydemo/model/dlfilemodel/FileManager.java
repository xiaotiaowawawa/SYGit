package com.example.sydemo.model.dlfilemodel;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager implements IFileManager{
    private IFileInfo fileInfo;
    private FileOutputStream fileOutputStream;
    private File file;
    RandomAccessFile rmfile=null;
    public static FileManager createFileManager(String filePath,String fileName,String fileUrl){
        synchronized (FileManager.class) {
            FileManager fileManager = new FileManager();
            IFileInfo fileInfo = new DownFileInfo(filePath, fileName, fileUrl);
            fileManager.fileInfo=fileInfo;
            try {
                fileManager.rmfile = fileManager.setRandomAccessFile();
            } catch (Exception e) {
                Log.d("msg1","file获取失败");
                e.printStackTrace();
            }
            return fileManager;
        }
    }
    public static FileManager createFileManager(IFileInfo iFileInfo){
        synchronized (FileManager.class) {
            FileManager fileManager = new FileManager();
            fileManager.fileInfo=iFileInfo;
            try {
                fileManager.rmfile = fileManager.setRandomAccessFile();
            } catch (Exception e) {
                Log.d("msg1","file获取失败");
                e.printStackTrace();
            }
            return fileManager;
        }
    }
    @Override
    public String getFilePath() {
        return fileInfo.getFilePath();
    }

    @Override
    public long getCode() {
        return fileInfo.getCode();
    }

    public void setRmFile() {
        if (rmfile==null)
            try {
                rmfile=new RandomAccessFile(fileInfo.getFileAllPath(),"rw");
                try {
                    rmfile.seek(fileInfo.getCurrentProcess());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                Log.d("msg1","file获取失败");
                e.printStackTrace();
            }

    }

    private FileManager(){
    }

    public IFileInfo getFileInfo() {
        return fileInfo;
    }
    public void writeByteToFile(byte[] bytes){
        try {
            if (bytes!=null) {
                if (rmfile == null)
                    setRmFile();
                rmfile.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeBytesFinish() throws Exception{
        synchronized (this) {
            rmfile.close();
            rmfile = null;
        }
    }
    private FileOutputStream getOutPutStream(){
        if (fileOutputStream == null) {
            synchronized (this) {
                if (fileOutputStream == null) {
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        return fileOutputStream;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }

                }
            }
         }
            return fileOutputStream;
        }
    private RandomAccessFile setRandomAccessFile()throws Exception{
        Log.i("msg1","文件名是"+fileInfo.getFileAllPath());
        if (file==null)
            file=new File(fileInfo.getFileAllPath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        rmfile=new RandomAccessFile(fileInfo.getFileAllPath(),"rw");
        rmfile.seek(fileInfo.getCurrentProcess());
        return rmfile;
    }

    @Override
    public boolean isFileChange() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileInfo.setProccess(0L);
                rmfile=new RandomAccessFile(fileInfo.getFileAllPath(),"rw");
                rmfile.seek(fileInfo.getCurrentProcess());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public void changeStatueToIsloading() {
        fileInfo.changeStatueToIsloading();
    }

    @Override
    public void changeStatueToPause() {
        fileInfo.changeStatueToPause();
    }

    @Override
    public void changeStatueToSuccess() {
        fileInfo.changeStatueToSuccess();
    }

    @Override
    public void changeStatueStatueToError() {
        fileInfo.changeStatueStatueToError();
    }

    @Override
    public Long getProcess() {
        return fileInfo.getProcess();
    }

    @Override
    public void setProccess(Long process) {
        fileInfo.setProccess(process);
    }

    @Override
    public String getFileName() {
        return fileInfo.getFileName();
    }

    @Override
    public String getFileAllPath() {
        return fileInfo.getFileName();
    }

    @Override
    public void setTotalLength(long totalLength) {
        fileInfo.setTotalLength(totalLength);
    }

    @Override
    public long getTotalLength() {
        return fileInfo.getTotalLength();
    }

    @Override
    public int getStatue() {
        return fileInfo.getStatue();
    }

    @Override
    public Long getCurrentProcess() {
        return fileInfo.getCurrentProcess();
    }

    @Override
    public String getFileUrl() {
        return fileInfo.getFileUrl();
    }

    @Override
    public boolean isSuccess() {
        return fileInfo.isSuccess();
    }
}
