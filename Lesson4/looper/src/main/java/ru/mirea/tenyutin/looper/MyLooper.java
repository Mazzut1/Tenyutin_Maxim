package ru.mirea.tenyutin.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyLooper extends Thread {

    public Handler mHandler;
    private final Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "Thread started");
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                int   age = b.getInt("AGE");
                String job = b.getString("JOB");

                Log.d("MyLooper", "Пришёл возраст = " + age +
                        ", профессия = " + job + ", ждём " + age + " c");

                try {
                    TimeUnit.SECONDS.sleep(age);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = String.format("Через %d сек.: вам %d лет, вы %s", age, age, job);

                Message answer = Message.obtain();
                Bundle bundle  = new Bundle();
                bundle.putString("RESULT", result);
                answer.setData(bundle);

                mainHandler.sendMessage(answer);
            }
        };

        Looper.loop();
    }
}