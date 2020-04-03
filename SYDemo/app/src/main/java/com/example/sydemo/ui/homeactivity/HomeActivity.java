package com.example.sydemo.ui.homeactivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.Edits;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.afirez.spi.ExtensionLoader;
import com.example.commondapi.Display;
import com.example.sydemo.R;
import com.example.sydemo.presenter.HomePresenter;
import com.example.sydemo.ui.BaseActivity;
import com.example.sydemo.ui.bilibilhome.BiliBliHome;
import com.example.sydemo.ui.mainactivity.MainActivity;
import com.example.sydemo.ui.myservice.DownFileService;
import com.google.auto.service.AutoService;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class HomeActivity extends BaseActivity<HomePresenter> {

    ExecutorService executorService;
    DateFormat dateFormatGMT;
    volatile  int flag=0;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(View layoutView) {
        Intent intent=new Intent(this, DownFileService.class);
        startService(intent);
        getAllPermission();
        dateFormatGMT=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH); //时间格
        Date date = new Date();

        File file=new File(this.getExternalFilesDir("mycache")+"/shello/");
        File cacheDir = new File(file.getAbsolutePath(), "sunyucache");
        if (!file.exists()){
                Log.i("msg1","文件新建"+file.getAbsolutePath());
              boolean b=  file.mkdirs();
              if (!b){
                  Log.i("msg1","文件新建失败"+file.getAbsolutePath());
              }
        }else{
            Log.i("msg1","文件存在"+file.getAbsolutePath());
        }
        OkHttpClient okHttpClient=new OkHttpClient().newBuilder().cache(new Cache(cacheDir, 10 * 1024 * 1024)).build();
        layoutView.findViewById(R.id.hello_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.notify_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.hello_bn2).setOnClickListener((View v) ->{
            Intent intent1=new Intent(HomeActivity.this, BiliBliHome.class);
            startActivity(intent1);
          /*  Request request=new Request.Builder().addHeader("If-Modified-Since", dateFormatGMT.format(date)+"").cacheControl(new CacheControl.Builder()./*maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)*//*maxAge(60,TimeUnit.SECONDS).build()).url("http://192.168.43.70:8080/Context/getTestString").build();*/
         /*   Call call=okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("msg1","失败了");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.i("msg1","。。。。。。。。。。。。网络请求返回码"+response.code());
                    Log.i("msg1","。。。。。。。。。。。。请求头"+call.request().toString());
                    if (response.cacheResponse()!=null)
                    Log.i("msg1",".......缓存信息"+response.cacheResponse().toString());
                    if (response.networkResponse()!=null)
                    Log.i("msg1",".......网络响应信息"+response.networkResponse().headers().toString());
                    Log.i("msg1",".......响应头"+response.networkResponse().header("Last-Modified","不存在define响应头"));
                    response.body().close();
                }
            });*/
            }
        );
     /*  new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler=new Handler((@NonNull Message msg)-> {
                  Log.i("msg1","执行了message方法");
                        return false;

                });
                Log.i("msg1","。。。。。执行了looper前面的方法");
                handler.sendMessageDelayed(Message.obtain(),1000);
                Looper.loop();
                Log.i("msg1","。。。。。执行了looper后面的方法");
            }
        }).start();*/

      // testRxjava2();
     /*  Observable.create(new ObservableOnSubscribe<String>() {
           @Override
           public void subscribe(ObservableEmitter<String> emitter) throws Exception {
               emitter.onNext("");
               Thread.sleep(8000);
           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
           @Override
           public void accept(String aVoid) throws Exception {
               testRxjava2();
           }
       });*/

        //   LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //Log.i("msg1","hello"+( 1L << (63))+"max"+ Long.MAX_VALUE);

       /* ExecutorService executorService= Executors.newFixedThreadPool(3);
        Future<String>future=executorService.submit(new NewTask());
        Future<String>future1=executorService.submit(new NewTask());
        Future<String>future2=executorService.submit(new NewTask());
        Future<String>future3=executorService.submit(new NewTask());
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.i("msg1","线程池开始执行runnable方法了");
            }
        });*/
      /*  try {
            String s=future.get();
            String s1=future.get();
            String s2=future.get();
            String s3=future.get();

            Log.i("msg1","线程池获取的值为"+s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    /*NewRun newRun=new NewRun();
        CompositeDisposable tasks=new CompositeDisposable();
        ScheduledRunnable scheduledRunnable=new ScheduledRunnable(newRun,tasks);
        Future<?> f;*/
        executorService= Executors.newFixedThreadPool(3);
        //executorService.submit(new NewRun());
        ServiceLoader<Display> serviceLoader=ServiceLoader.load(Display.class);
        Iterator<Display> iterator=serviceLoader.iterator();

        String r=ExtensionLoader.getInstance().<Display>loadExtension("BDisplay").display();
        Log.i("msg1","rrrrrrr的内容"+r);
    }

    public void testRxjava2(){
        Observable.create(new ObservableOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<String>> emitter) throws Exception {
                ArrayList<String>stringArrayList=new ArrayList<>();
                stringArrayList.add("hello");
                emitter.onNext(stringArrayList);
            }
        }).observeOn(Schedulers.io()).doOnNext(new Consumer<ArrayList<String>>() {
            @Override
            public void accept(ArrayList<String> s) throws Exception {
                Log.i("msg2","第1个线程开始执行"+Thread.currentThread()+s.toString());
                s.add("第一个");
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<String>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ArrayList<String> s) {

                Log.i("msg2","下游事件线程"+Thread.currentThread()+s.toString());

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }
 static final class NewTask implements Callable<String>{
    public NewTask() {
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        Log.i("msg1","当前线程为"+Thread.currentThread());
        return "isMyTask";
    }
}
public class NewRun implements Runnable{
    @Override
    public void run() {
        if (flag==0){
            flag++;
            /*executorService.submit(new NewRun());
            executorService.submit(new NewRun());
            executorService.submit(new NewRun());
            executorService.submit(new NewRun());*/
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("msg1","runnable...."+Thread.currentThread());
    }
}
    @Override
    public HomePresenter setPresenter() {
        return new HomePresenter(this);
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
    public View getToolbarParentView() {
        return getMainView().findViewById(R.id.main_layout);
    }

    @Override
    protected void onDestroy() {
        Log.d("msg1","执行homeactivity的ondestory方法");
        Intent intent=new Intent(this, DownFileService.class);
        stopService(intent);
        super.onDestroy();
    }
}
