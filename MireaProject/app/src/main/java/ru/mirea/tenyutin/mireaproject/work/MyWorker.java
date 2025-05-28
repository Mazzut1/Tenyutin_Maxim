package ru.mirea.tenyutin.mireaproject.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyWorker", "doWork: START");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return Result.failure();
        }

        Log.d("MyWorker", "doWork: END");
        return Result.success();
    }
}