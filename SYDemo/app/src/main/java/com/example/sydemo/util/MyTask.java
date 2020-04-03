package com.example.sydemo.util;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.internal.schedulers.IoScheduler;

public class MyTask {
    static final NewTask newTask;
    final AtomicReference<NewTask> pool;

    public MyTask() {
        this.pool = new AtomicReference<NewTask>(newTask);
    }

    static {
        newTask=new NewTask();
    }
    static final class NewTask{

    }

    public NewTask getPool() {
        return pool.get();
    }
}
