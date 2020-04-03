package com.example.sydemo.model.dlfilemodel;

import java.io.RandomAccessFile;

public interface IFileManager extends IFileInfo{
    public void writeByteToFile(byte[] bytes);
    public void writeBytesFinish() throws Exception;
    public IFileInfo getFileInfo();
    public boolean isFileChange();
}
