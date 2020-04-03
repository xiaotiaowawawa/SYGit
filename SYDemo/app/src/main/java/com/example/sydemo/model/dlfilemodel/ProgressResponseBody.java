package com.example.sydemo.model.dlfilemodel;

import android.util.Log;

import com.example.sydemo.listener.DownloadFileListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
   // private final DownloadFileListener downloadFileListener;
    public  BufferedSource bufferedSource;
    private  long totalBytesRead =0L;
   // public final IFileManager iFileManager;
   private FileDownloadCreate fileDownloadCreate;
   private  IProcessBody iProcessBody;
   private int code;
    public ProgressResponseBody(ResponseBody responseBody, FileDownloadCreate fileDownloadCreate, IProcessBody iProcessBody,int code) {
        this.responseBody = responseBody;
       this.fileDownloadCreate=fileDownloadCreate;
       this. iProcessBody=iProcessBody;
       this.code=code;
       if (fileDownloadCreate!=null){
           totalBytesRead=fileDownloadCreate.getIFileManager().getCurrentProcess();

       }
    }

    public FileDownloadCreate getFileDownloadCreate() {
        return fileDownloadCreate;
    }



    public void setFileDownloadCreate(FileDownloadCreate fileDownloadCreate) {
        this.fileDownloadCreate = fileDownloadCreate;
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @NotNull
    @Override
    public BufferedSource source() {
        if (bufferedSource==null)
            bufferedSource= Okio.buffer(source(responseBody.source()));
        return bufferedSource;
    }
    private Source source(Source source) {

        return new ForwardingSource(source) {

            //当前读取字节数
            @Override public long read(Buffer sink, long byteCount) throws IOException {
           //     Log.i("msg1","经过read");
                if (fileDownloadCreate != null) {
                    IFileManager iFileManager = fileDownloadCreate.getIFileManager();
                    if (iFileManager != null) {
                        DownloadFileListener downloadFileListener = fileDownloadCreate.getDownloadFileListener();
                        try {
                            long bytesRead = super.read(sink, byteCount);
                            totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                            if (fileDownloadCreate != null) {
                                iFileManager.setProccess(totalBytesRead);
                              //      Log.i("msg1",""+bytesRead);
                                if (downloadFileListener != null)
                                    downloadFileListener.process((int) (totalBytesRead * 1.0 / iFileManager.getTotalLength() * 100));
                            }
                            // Log.d("msg1",""+bytesRead);
                            //回调，如果contentLength()不知道长度，会返回-1
                            //  Log.d("msg1",""+bytesRead);
                            return bytesRead;

                        } catch (Exception e) {
                            //出现异常，比如超时或者call被cancle掉
                            fileDownloadCreate = null;
                            if (iProcessBody!=null) {
                                Log.i("msg1","执行到ib的destory了");
                                iProcessBody.errorDestory(getCurrentResponseBody(), iFileManager, downloadFileListener, code);
                            }
                            Log.i("msg1", "catch到异常了");
                        }
                    }
                }
                return -1L;
            }
        };

    }

    public  ProgressResponseBody getCurrentResponseBody() {
        return this;
    }

    public void setiProcessBody(IProcessBody iProcessBody) {
        this.iProcessBody = iProcessBody;
    }
}
