package com.example.sydemo.ui.mainactivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sydemo.R;
import com.example.sydemo.model.adapter.FileInfoRVAdapter;
import com.example.sydemo.model.dlfilemodel.FileManager;
import com.example.sydemo.model.dlfilemodel.IDownManagerAc;
import com.example.sydemo.model.dlfilemodel.IDownloadManager;
import com.example.sydemo.model.dlfilemodel.IFileInfo;
import com.example.sydemo.model.dlfilemodel.IFileManager;
import com.example.sydemo.presenter.MainPresenter;
import com.example.sydemo.ui.BaseActivity;
import com.example.sydemo.ui.myservice.DownFileService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity<MainPresenter> {
private int start;
private IDownManagerAc downloadManager;

private DownFileService downFileService;
private Map<Integer,ContentLoadingProgressBar>map;
private String url1="http://192.168.1.25:8080/MyChat/getAllRoomByUid?name=pc_driver.rar";
private String url2="http://192.168.1.25:8080/MyChat/getAllRoomByUid?name=gcc-7.2.0.tar.gz";

private RecyclerView show_downfile_list;
private FileInfoRVAdapter fileInfoRVAdapter;
private List<IFileInfo>iFileInfoList;

ServiceConnection serviceConnection=new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i("msg1","执行了");
        downFileService=((DownFileService.DownloadBinder)service).getDownFileService();
        downloadManager=downFileService.getIdownManagerForActivity();
        for (IFileInfo iFileInfo:downloadManager.getListIFileInfoInDb()){
            iFileInfoList.add(iFileInfo);
        }
     /*   for (IFileInfo iFileInfo:downloadManager.getDownloadingFileInfoList()){
            iFileInfoList.add(iFileInfo);
        }*/

      //  map.put(url1.hashCode(),oneProgress);
       // map.put(url2.hashCode(),twoProgress);
        fileInfoRVAdapter=new FileInfoRVAdapter(iFileInfoList);
        fileInfoRVAdapter.setOnclick((final IFileInfo downFileInfo, ContentLoadingProgressBar contentLoadingProgressBar)-> {
            Log.i("msg1","开始下载"+downFileInfo.getFileName());
                    downloadManager.addFileDownloadCreate(downFileInfo,contentLoadingProgressBar);
                }
        );
        show_downfile_list.setAdapter(fileInfoRVAdapter);
        show_downfile_list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       // fileInfoRVAdapter.notifyDataSetChanged();
        show_downfile_list.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                ContentLoadingProgressBar contentLoadingProgressBar=view.findViewById(R.id.download_progress);
                int code=((Long)contentLoadingProgressBar.getTag(R.id.fileCode)).intValue();
             // Log.i("msg1","onChildViewAttachedToWindow"+code);
                long total=(long)contentLoadingProgressBar.getTag(R.id.fileTotal);
                long current=(long)contentLoadingProgressBar.getTag(R.id.fileCurrent);
                Log.i("msg1","totoal"+total+"  current"+current);
                downloadManager.updateProgessBar(contentLoadingProgressBar,code,total,current);
            }
            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                int code=((Long)view.findViewById(R.id.download_progress).getTag(R.id.fileCode)).intValue();
                downloadManager.removeProgressBar(code);
            }
        });
       // downloadManager.changeFileDownloadCreateToProgress(fileInfoRVAdapter.getIntegerContentLoadingProgressBarMap());*/
        showMainView();
      /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downloadManager.pauseCall("http://192.168.43.70:8080/MyChat/getAllRoomByUid?name=gcc-7.2.0.tar.gz".hashCode());
            }
        }).start();*/
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
};
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" ,"android.permission.REQUEST_INSTALL_PACKAGES"};
    //然后通过一个函数来申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View layoutView) {
         //getToolbarStyleHelper().setHeight(R.id.base_toolbar, DensityUtil.dip2px(this,50));
        hideMainView();
        map=new HashMap<>();
        iFileInfoList=new ArrayList<>();
        show_downfile_list=findViewById(R.id.show_downfile_list);
        verifyStoragePermissions(this);
        /*oneProgress=findViewById(R.id.one_progress);
        twoProgress=findViewById(R.id.two_progress);
        findViewById(R.id.pause_call_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.pauseCall(url2.hashCode());
            }
        });
*/
        Intent intent=new Intent( this,DownFileService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

       /* findViewById(R.id.one_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadManager != null) {
                    String fileName = "pc_driver.rar";
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile/pc_driver.rar");
                    String url = "http://192.168.1.25:8080/MyChat/getAllRoomByUid?name=pc_driver.rar";
                    downloadManager.addFileDownloadCreate(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile/", url, fileName, oneProgress);
                }else
                    Toast.makeText(MainActivity.this,"等待下载服务开启",Toast.LENGTH_SHORT).show();
            }

        });
        findViewById(R.id.two_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadManager!=null){
                String fileName="gcc-7.2.0.tar.gz";
                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myfile/gcc-7.2.0.tar.gz");
                String url="http://192.168.1.25:8080/MyChat/getAllRoomByUid?name=gcc-7.2.0.tar.gz";
                downloadManager.addFileDownloadCreate(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myfile/",url,fileName,twoProgress);}
                else
                    Toast.makeText(MainActivity.this,"等待下载服务开启",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        downloadManager=null;
        downFileService.getiDownloadManager().changeFileDownloadCreateToNotify(downFileService);
        unbindService(serviceConnection);

        super.onDestroy();
    }

    @Override
    public View getToolbarParentView() {
        return getMainView().findViewById(R.id.main_layout);
    }
    public void getAllPermission(){//获得权限
        try{

            PackageInfo pack = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissionStrings = pack.requestedPermissions;
            for(String permissionString:permissionStrings){
                int permission = ActivityCompat.checkSelfPermission(this, permissionString);
                int REQUEST_PERMISSION = 1;
                if(permission!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,permissionStrings,REQUEST_PERMISSION);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter(this);
    }
}
