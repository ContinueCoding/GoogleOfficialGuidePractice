package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.app.Service;
import android.content.Intent;
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

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
        super.onDestroy();
    }

}
