package com.example.sydemo.model.dlfilemodel;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;

import com.example.sydemo.MyApplication;
import com.example.sydemo.R;
import com.example.sydemo.db.IDBgetHelper;
import com.example.sydemo.listener.DownloadFileListener;
import com.example.sydemo.listener.IProgressListener;
import com.example.sydemo.listener.NoticationListener;
import com.example.sydemo.listener.ProgressListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager implements IDownloadManager, IProcessBody,IDownManagerAc {
private OkHttpClient okHttpClient;
private Map<Integer, Call> callWeakHashMap;
private Map<Integer, FileDownloadCreate>downloadFileListenerWeakReference;
private Map<Integer, ProgressResponseBody>integerProgressResponseBodyMap;
private Map<Integer,FileDownloadCreate>downloadCreateMap;
private List<IFileInfo>iFileInfoListDownloading;
private Handler handler=new Handler();
private int maxDownCount;
private volatile  boolean isRemoveAll=false;//是否是全部暂停
private List<IFileInfo>iFileInfoList;
private ArrayList<IFileInfo>iFileInfoArrayList;
 public DownloadManager() {
    this.downloadFileListenerWeakReference=new HashMap<>();
    integerProgressResponseBodyMap=new HashMap<>();
    callWeakHashMap=new HashMap<>();
    downloadCreateMap=new HashMap<>();
     iFileInfoArrayList=new ArrayList<>();
    iFileInfoListDownloading=new ArrayList<>();
    maxDownCount=3;
    this.okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Response response=chain.proceed(chain.request());
                // stringWeakHashMap.put(Integer.parseInt(response.header("key")),response.header("fileLength"));
                //Log.d("msg1",response.header("key")+"keydddd");
                Log.i("msg1","经过拦截器"+response.code());
                if (response.code()==200) {
                    String errorCode=response.header("error");
                    if (errorCode==null) {
                        Integer code = response.request().url().hashCode();
                        FileDownloadCreate fileDownloadCreate = null;
                        if (downloadFileListenerWeakReference.containsKey(code)) {
                            fileDownloadCreate = downloadFileListenerWeakReference.get(code);
                            if (fileDownloadCreate != null) {
                                IFileManager iFileManager = fileDownloadCreate.getIFileManager();
                                if (iFileManager != null)
                                    downloadFileListenerWeakReference.get(code).getIFileManager().setTotalLength(Long.decode(response.header("fileLength")));
                            }
                        }
                        ProgressResponseBody progressResponseBody=new ProgressResponseBody(response.body(), fileDownloadCreate,DownloadManager.this,code);
                        integerProgressResponseBodyMap.put(code,progressResponseBody);
                        return response.newBuilder().body(progressResponseBody).build();
                    }
                    else {
                       //连接服务器但是文件没找到，或者下载内容超过本身内容
                        int code=Integer.parseInt(errorCode);
                        switch (code){
                            case 1:
                                Log.d("msg1","文件超出异常");
                                break;
                            case 2:
                                notifyMsg("改文件已经下架");
                                Log.d("msg1","服务器文件未找到");
                                break;
                        }
                    }

                }else{
                    Log.i("msg1","拦截异常");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.context,"服务器异常",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return response;
            }
        }).build();
        okHttpClient.dispatcher().setMaxRequests(5);

    }

    @Override
    public void startNewDownLoad(String url) {
        Call call=null;
        final int code=url.hashCode();
        if (!callWeakHashMap.containsKey(code)) {
            if(downloadFileListenerWeakReference.containsKey(code)) {
                IFileManager iFileManager=downloadFileListenerWeakReference.get(code).getIFileManager();
                Request request = new Request.Builder().addHeader("currentFileLength", String.valueOf(iFileManager.getCurrentProcess())).url(url).build();
                call = okHttpClient.newCall(request);
                callWeakHashMap.put(code, call);
            }else{
                Log.i("msg1","下载异常，重启app");
                notifyMsg("下载异常，请重启app");
            }
        }else {
            Log.d("msg1","没有remove掉");
            if (downloadFileListenerWeakReference.containsKey(code)){
                IFileManager iFileManager=downloadFileListenerWeakReference.get(code).getIFileManager();
                if (iFileManager.getStatue()!= DownFileInfo.isloading&&!iFileManager.isSuccess()){
                 pauseCall(code);
                 startNewDownLoad(url);
                 return;
                }
            }
        }
        Log.i("msg1",code+"开始下载");
            call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                FileDownloadCreate fileDownloadCreate=null;
                Log.d("msg1","下载异常");
                if (downloadFileListenerWeakReference.containsKey(code)) {
                    fileDownloadCreate = downloadFileListenerWeakReference.get(code);
                    if (fileDownloadCreate != null) {
                        IFileManager iFileManager = fileDownloadCreate.getIFileManager();
                        iFileManager.changeStatueStatueToError();
                        DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
                        if (downloadFileListener!=null)
                         downloadFileListener.error(iFileManager.getFileName() + "下载异常");
                    }
                }
                if (!isRemoveAll) {
                    callWeakHashMap.remove(code);
                    downloadCreateMap.remove(code);
                    integerProgressResponseBodyMap.remove(code);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                   solveResponse(response,code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void solveResponse(Response response,int code)throws Exception{
        ProgressResponseBody progressResponseBody=(ProgressResponseBody) response.body();
        IFileManager iFileManager=progressResponseBody.getFileDownloadCreate().getIFileManager();
        InputStream inputStream=response.body().byteStream();
        byte[] b=new byte[1024];
        while(inputStream.read(b)!=-1){
         iFileManager.writeByteToFile(b);
        }
        inputStream.close();
        iFileManager.writeBytesFinish();
        if (progressResponseBody.getFileDownloadCreate()!=null) {
            Log.i("msg1","执行了getFileDownloadCreate!=null");
            IFileManager iFileManager1 = progressResponseBody.getFileDownloadCreate().getIFileManager();
            DownloadFileListener downloadFileListener = progressResponseBody.getFileDownloadCreate().getDownloadFileListener();
            if (iFileManager1.isSuccess()) {
                Log.i("msg1","执行了Success");
                successDownLoad(progressResponseBody,iFileManager1,downloadFileListener, code);
            } else {
                Log.i("msg1","执行了文件大小不一致"+iFileManager.getCurrentProcess()+"   "+iFileManager.getTotalLength());
                errorInDownload(progressResponseBody,iFileManager1,downloadFileListener, code);
            }
        }else {
            Log.i("msg1","下载异常ddddd");
           // if (!isRemoveAll)
           // errorInDownload(progressResponseBody,null,null,code);
        }

    }
private void successDownLoad(ProgressResponseBody progressResponseBody,IFileManager iFileManager,DownloadFileListener downloadFileListener,int code){
    progressResponseBody.setFileDownloadCreate(null);
    progressResponseBody.setiProcessBody(null);
   // downloadFileListenerWeakReference.remove(code);
    iFileManager.changeStatueToSuccess();
     if (downloadFileListener!=null) {
         downloadFileListener.success(iFileManager.getFileName() + "下载完成");
         downloadFileListener.destory();
     }
     if (!isRemoveAll) {
         integerProgressResponseBodyMap.remove(code);
         downloadCreateMap.remove(code);
         callWeakHashMap.remove(code);
     }
}
private void errorInDownload(ProgressResponseBody progressResponseBody,IFileManager iFileManager,DownloadFileListener downloadFileListener,int code){
    if (progressResponseBody!=null) {
        progressResponseBody.setFileDownloadCreate(null);
        progressResponseBody.setiProcessBody(null);
    }
    Log.i("msg1","执行了第一部分");
    integerProgressResponseBodyMap.remove(code);
    Call call=callWeakHashMap.get(code);
    if (call!=null) {
        if (!call.isCanceled()){
            call.cancel();
        }
        Log.i("msg1","执行了第2部分");
        if (!isRemoveAll)
        callWeakHashMap.remove(code);
    }
    Log.i("msg1","执行了第3部分");
    if (!isRemoveAll)
    downloadCreateMap.remove(code);
    if (iFileManager!=null) {
        iFileManager.changeStatueStatueToError();
        Log.i("msg1","设置下载错误");
        if (downloadFileListener != null) {
            downloadFileListener.error(iFileManager.getFileName() + "下载异常请重新下载");
        }
       // pauseCall(code);
    }
}


    @Override
    public void errorDestory(ProgressResponseBody progressResponseBody,IFileManager iFileManager,DownloadFileListener downloadFileListener,int code) {
        errorInDownload(progressResponseBody,iFileManager,downloadFileListener,code);
    }

    @Override
    public void pauseAllDownLoad() {
     Log.i("msg1","执行了pauseAllDownLoad");
     if (callWeakHashMap!=null) {
         isRemoveAll=true;
         for (Integer code : callWeakHashMap.keySet()) {
             Call call=callWeakHashMap.get(code);
             if (!call.isCanceled()) {
                 call.cancel();
                 FileDownloadCreate fileDownloadCreate=downloadFileListenerWeakReference.get(code);
                 DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
                 errorInDownload(integerProgressResponseBodyMap.get(code),fileDownloadCreate.getIFileManager(),downloadFileListener,code);
                 downloadFileListener.destory();
                 Log.i("msg1","执行了cancle");
             }
         }
         callWeakHashMap.clear();
         downloadCreateMap.clear();
         integerProgressResponseBodyMap.clear();
     }
    }

    @Override
    public void pauseCall(int code) {
   if (!isRemoveAll)
     if (callWeakHashMap.containsKey(code)) {
         if (integerProgressResponseBodyMap.containsKey(code)){
             integerProgressResponseBodyMap.remove(code);
         }
         Call call = callWeakHashMap.get(code);
         if (!isRemoveAll)
         callWeakHashMap.remove(code);
         if (!call.isCanceled())
                call.cancel();
     }
    }

    @Override
    public void setMaxDownload(int count) {
        maxDownCount=count;
        okHttpClient.dispatcher().setMaxRequests(count);
    }

    @Override
    public void destory() {

    }

    @Override
    public void addFileDownloadCreate(String filePath, final Integer code, String url,String fileName, ContentLoadingProgressBar contentLoadingProgressBar) {
        IFileInfo iFileInfo=new DownFileInfo(filePath,fileName,url);
        addFileDownloadCreate(iFileInfo,contentLoadingProgressBar,code);
    }

    @Override
    public void addFileDownloadCreate(String filePath, String url,String fileName, ContentLoadingProgressBar contentLoadingProgressBar) {
        int code=url.hashCode();
        addFileDownloadCreate(filePath,code,url,fileName,contentLoadingProgressBar);
    }

    @Override
    public void addFileDownloadCreate(IFileInfo iFileInfo, ContentLoadingProgressBar contentLoadingProgressBar) {
       addFileDownloadCreate(iFileInfo,contentLoadingProgressBar,(int)(iFileInfo.getCode()));
    }
    private void addFileDownloadCreate(IFileInfo iFileInfo, ContentLoadingProgressBar contentLoadingProgressBar, int code){
        IFileManager iFileManager=null;
        DownloadFileListener downloadFileListener=null;
        FileDownloadCreate fileDownloadCreate=null;
        if (downloadFileListenerWeakReference.containsKey(code)){
            Toast.makeText(MyApplication.context,"已在下载任务中"+iFileInfo.getFileName(),Toast.LENGTH_SHORT).show();
            fileDownloadCreate=downloadFileListenerWeakReference.get(code);
            iFileManager=fileDownloadCreate.getIFileManager();
            downloadFileListener=fileDownloadCreate.getDownloadFileListener();
        }else{
            Log.i("msg1","新任务"+code);
            iFileManager= FileManager.createFileManager(iFileInfo);
            Long total=iFileManager.getTotalLength();
            downloadFileListener=new ProgressListener(contentLoadingProgressBar,getProcess(iFileManager));
            fileDownloadCreate=new FileDownloadCreate(downloadFileListener, iFileManager);
            downloadFileListenerWeakReference.put(code,fileDownloadCreate );
          //  downloadCreateMap.put(code,fileDownloadCreate);
        }
        if (iFileManager.getStatue()==0) {
            iFileManager.changeStatueToIsloading();
            downloadFileListener.start("开始下载" + iFileInfo.getFileName());
            iFileManager.isFileChange();
            startNewDownLoad(iFileInfo.getFileUrl());
            downloadCreateMap.put(code,fileDownloadCreate);
        }
        else if ((iFileManager.getStatue()&DownFileInfo.isloading)==DownFileInfo.isloading){
          //  notifyMsg("正在下载别重复点击好嘛...");
            if (!downloadCreateMap.containsKey(code)){
                iFileManager.isFileChange();
                startNewDownLoad(iFileInfo.getFileUrl());
                downloadCreateMap.put(code,fileDownloadCreate);
            }else {
                notifyMsg("正在下载别重复点击好嘛...");
            }
            Log.i("msg1",""+(iFileManager.getStatue()&DownFileInfo.isloading));
        }else if ((iFileManager.getStatue()&DownFileInfo.error)==DownFileInfo.error){
            Log.i("msg1","异常重新下载");
            // notifyMsg("恢复正常下载");
            iFileManager.changeStatueToIsloading();
            iFileManager.isFileChange();
            startNewDownLoad(iFileInfo.getFileUrl());
            downloadCreateMap.put(code,fileDownloadCreate);
        }else if (iFileManager.isSuccess()){
            notifyMsg("该文件已经下载成功，是否重新下载");
        } else{
            notifyMsg("未知状况");
        }
}
    @Override
    public void changeFileDownloadCreateToNotify(Context context) {
        for (Integer key:downloadFileListenerWeakReference.keySet()){
             FileDownloadCreate fileDownloadCreate=downloadFileListenerWeakReference.get(key);
             IFileManager iFileManager=fileDownloadCreate.getIFileManager();
             DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
             fileDownloadCreate.setDownloadFileListener(null);
             downloadFileListener.destory();
             if ((iFileManager.getStatue()&DownFileInfo.isloading)==DownFileInfo.isloading) {
                 DownloadFileListener downloadFileListener1 = new NoticationListener(key, context, iFileManager.getFileName() + "下载中", R.drawable.ic_launcher_background, "notification");
                 fileDownloadCreate.setDownloadFileListener(downloadFileListener1);
             }
        }
    }

    @Override
    public ArrayList<IFileInfo> changeFileDownloadCreateToProgress(Map<Integer,ContentLoadingProgressBar>map) {
        iFileInfoArrayList.clear();
        for (Integer key:downloadFileListenerWeakReference.keySet()){
            if (map.containsKey(key)){
                FileDownloadCreate fileDownloadCreate=downloadFileListenerWeakReference.get(key);
                DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
                IFileManager iFileManager=fileDownloadCreate.getIFileManager();
                iFileInfoArrayList.add(fileDownloadCreate.getIFileManager().getFileInfo());
                if (downloadFileListener instanceof NoticationListener) {
                    fileDownloadCreate.setDownloadFileListener(null);
                    downloadFileListener.destory();
                    Long total=iFileManager.getTotalLength();
                    DownloadFileListener downloadFileListener1 = new ProgressListener(map.get(key),getProcess(iFileManager));
                    fileDownloadCreate.setDownloadFileListener(downloadFileListener1);
                }
            }

        }
        return iFileInfoArrayList;
    }

    @Override
    public Map<Integer, FileDownloadCreate> getFileDownloadCreateMap() {
        return downloadFileListenerWeakReference;
    }
    public void notifyMsg(final String msg){
     handler.post(new Runnable() {
         @Override
         public void run() {
            Toast.makeText(MyApplication.context,msg,Toast.LENGTH_SHORT).show();
         }
     });
    }

    @Override
    public void resetFileInfo(IDBgetHelper<IFileInfo> fileInfoDBHelper) {
     iFileInfoList=fileInfoDBHelper.getAllModel();
     if (iFileInfoList!=null)
     Log.i("msg1",".................."+iFileInfoList.size());
    }

    @Override
    public void storeFileInfo(IDBgetHelper<IFileInfo> fileInfoDBHelper) {
       pauseAllDownLoad();
       Log.i("msg1","保存下载文件个数为"+downloadFileListenerWeakReference.size());
       for (FileDownloadCreate fileDownloadCreate:downloadFileListenerWeakReference.values()) {
           DownloadFileListener downloadFileListener = fileDownloadCreate.getDownloadFileListener();
           if (downloadFileListener != null)
               downloadFileListener.destory();
           fileInfoDBHelper.insertModelByModel(fileDownloadCreate.getIFileManager().getFileInfo(), true);
       }
    }

    @Override
    public List<IFileInfo> getListIFileInfoInDb() {
        return iFileInfoList;
    }

    @Override
    public List<IFileInfo> getDownloadingFileInfoList() {
     iFileInfoListDownloading.clear();
     for (FileDownloadCreate fileDownloadCreate:downloadFileListenerWeakReference.values()){
         IFileManager iFileManager=fileDownloadCreate.getIFileManager();
         if (iFileManager!=null&&((iFileManager.getStatue()&DownFileInfo.isloading)==DownFileInfo.isloading)){
             iFileInfoListDownloading.add(iFileManager.getFileInfo());
         }
     }
        return iFileInfoListDownloading;
    }

    @Override
    public void updateProgessBar(ContentLoadingProgressBar contentLoadingProgressBar,final IFileManager iFileManager) {
     final int code=((Long)iFileManager.getCode()).intValue();
        if (downloadFileListenerWeakReference.containsKey(code)){
            FileDownloadCreate fileDownloadCreate=downloadFileListenerWeakReference.get(code);
            DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
            contentLoadingProgressBar.setProgress(getProcess(iFileManager));
            Log.i("msg1","执行了上面");
            if (downloadFileListener!=null){
                if (downloadFileListener instanceof IProgressListener){
                    IProgressListener iProgressListener=(IProgressListener) downloadFileListener;
                    iProgressListener.setContentLoadingProgressBar(contentLoadingProgressBar);
                    Log.i("msg1","执行了downloadFileListener!=null");
                }else{
                    int process=0;
                    long total=iFileManager.getTotalLength();
                    if (total!=0){
                        process=getProcess(iFileManager);
                    }
                    Log.i("msg1","执行了里面");
                    Log.i("msg1",process+"进度");
                    fileDownloadCreate.setDownloadFileListener(null);
                    downloadFileListener.destory();
                    IProgressListener<ContentLoadingProgressBar>listener=new ProgressListener(contentLoadingProgressBar,process);
                    fileDownloadCreate.setDownloadFileListener(listener);
                }
            }else{
                IProgressListener<ContentLoadingProgressBar>listener=new ProgressListener(contentLoadingProgressBar,getProcess(iFileManager));
                fileDownloadCreate.setDownloadFileListener(listener);
            }
        }//并没有创建
        else {
            contentLoadingProgressBar.setProgress(getProcess(iFileManager));
        }
    }

    @Override
    public void updateProgessBar(ContentLoadingProgressBar contentLoadingProgressBar, int code,long total,long current) {
        if (downloadFileListenerWeakReference.containsKey(code)){
            FileDownloadCreate fileDownloadCreate=downloadFileListenerWeakReference.get(code);
            DownloadFileListener downloadFileListener=fileDownloadCreate.getDownloadFileListener();
            IFileManager iFileManager=fileDownloadCreate.getIFileManager();
            contentLoadingProgressBar.setProgress(getProcess(iFileManager));
            Log.i("msg1","执行了上面");
            if (downloadFileListener!=null){
                if (downloadFileListener instanceof IProgressListener){
                    IProgressListener iProgressListener=(IProgressListener) downloadFileListener;
                    iProgressListener.setContentLoadingProgressBar(contentLoadingProgressBar);
                    Log.i("msg1","执行了downloadFileListener!=null");
                }else{
                    int  process=getProcess(iFileManager);
                    Log.i("msg1","执行了里面");
                    Log.i("msg1",process+"进度");
                    fileDownloadCreate.setDownloadFileListener(null);
                    downloadFileListener.destory();
                    IProgressListener<ContentLoadingProgressBar>listener=new ProgressListener(contentLoadingProgressBar,process);
                    fileDownloadCreate.setDownloadFileListener(listener);
                }
            }else{
                IProgressListener<ContentLoadingProgressBar>listener=new ProgressListener(contentLoadingProgressBar,getProcess(iFileManager));
                fileDownloadCreate.setDownloadFileListener(listener);
            }
        }//并没有创建
        else {
            int procees=getProcess(total,current);
            contentLoadingProgressBar.setProgress(procees);
            Log.i("msg1","设置进度为"+procees);
        }
    }

    private int getProcess(IFileManager iFileManager){
    long total=iFileManager.getTotalLength();
    int process=0;
    if (total!=0){
        process=((Double)(iFileManager.getCurrentProcess().doubleValue() /total*100)).intValue();
    }
    return process;
}
private int getProcess(long total,long current){
     if (total!=0){
         return ((Double)(((Long)current).doubleValue()/total*100)).intValue();
     }
     return 0;
}
    @Override
    public void removeProgressBar(int code) {
        if (downloadFileListenerWeakReference.containsKey(code)){
            DownloadFileListener downloadFileListener=downloadFileListenerWeakReference.get(code).getDownloadFileListener();
            if (downloadFileListener!=null){
                if (downloadFileListener instanceof IProgressListener){
                    IProgressListener iProgressListener=(IProgressListener) downloadFileListener;
                    iProgressListener.setContentLoadingProgressBar(null);
                }
            }
        }
    }
}
