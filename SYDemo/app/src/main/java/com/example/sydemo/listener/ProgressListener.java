package com.example.sydemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;

import com.example.sydemo.MyApplication;

public class ProgressListener implements IProgressListener<ContentLoadingProgressBar>{
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private int currentPro=0;
    public ProgressListener(ContentLoadingProgressBar contentLoadingProgressBar,int currentPro) {
        this.contentLoadingProgressBar = contentLoadingProgressBar;
        this.currentPro=currentPro;
        contentLoadingProgressBar.setProgress(currentPro);
    }

    public void setCurrentPro(int currentPro) {
        this.currentPro = currentPro;
        if (contentLoadingProgressBar!=null){
            contentLoadingProgressBar.setProgress(currentPro);
        }
    }

    @Override
    public void start(final String content) {
        if (contentLoadingProgressBar!=null) {
            contentLoadingProgressBar.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void process(final int currentProgress) {
      //  Log.d("msg1","hellp");
        if (contentLoadingProgressBar!=null)
        contentLoadingProgressBar.post(new Runnable() {
            @Override
            public void run() {
                if (contentLoadingProgressBar!=null)
                contentLoadingProgressBar.setProgress(currentProgress);
            }
        });


    }
    @Override
    public void pause(final String content) {
        if (contentLoadingProgressBar!=null)
        contentLoadingProgressBar.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.context,content,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void success(final String content) {
        Log.i("msg1","下载完成");
        if (contentLoadingProgressBar!=null){
            contentLoadingProgressBar.setProgress(100);
        }
        contentLoadingProgressBar.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.context,content,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void error(final String content) {
        if (contentLoadingProgressBar!=null)
        contentLoadingProgressBar.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.context,content,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void destory() {
        contentLoadingProgressBar=null;
    }

    @Override
    public ContentLoadingProgressBar ContentLoadingProgressBar() {
        return contentLoadingProgressBar;
    }

    @Override
    public void setContentLoadingProgressBar(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.contentLoadingProgressBar=contentLoadingProgressBar;
    }
}
