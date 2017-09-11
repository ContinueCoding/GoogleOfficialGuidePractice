package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.huoxy.googleofficialpractice.IRemoteService;

public class AidlService extends Service {

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return 123;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    public AidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
