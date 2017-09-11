package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MessengerService extends Service {

    public static final int MSG_TYPE_SAY_HELLO = 1;
    public static final int MSG_TYPE_SAY_GOODBYE = 2;

    final Messenger messenger = new Messenger(new IncomingHandler());

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_TYPE_SAY_HELLO:
                    Toast.makeText(MessengerService.this, "Hello", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_TYPE_SAY_GOODBYE:
                    Toast.makeText(MessengerService.this, "Goodbye", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
