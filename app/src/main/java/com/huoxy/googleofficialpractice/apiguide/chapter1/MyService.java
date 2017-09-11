package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int startId = msg.what;
            stopSelf(startId);
        }
    };

    private final LocalBinder binder = new LocalBinder();
    private final Random random = new Random();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        new Thread(){
            @Override
            public void run() {
                Random random = new Random();
                try {
                    int millis = random.nextInt(5000);
                    Log.i(TAG, "Task time = " + millis + ", taskId = " + startId);
                    Thread.sleep(millis);
                    Message obtain = Message.obtain();
                    obtain.what = startId;
                    handler.sendMessage(obtain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy() ------ ");
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    //IBinder
    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    /**
     * 获取100以内的随机数，供客户端调用
     * @return 随机数
     */
    public int getRandomNumber(){
        return random.nextInt(100);
    }

}
