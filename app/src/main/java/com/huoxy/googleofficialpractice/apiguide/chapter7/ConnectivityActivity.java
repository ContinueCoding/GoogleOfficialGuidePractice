package com.huoxy.googleofficialpractice.apiguide.chapter7;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ConnectivityActivity extends AppCompatActivity {

    private static final String TAG = "Bluetooth";

    private static final int DISCOVERABLE_DURATION = 600;

    private static final int REQUEST_ENABLE_BLUETOOTH = 111;
    private static final int REQUEST_DISCOVERABLE_BLUETOOTH = 222;

    private BluetoothAdapter bluetoothAdapter;

    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(TAG, "name = " + device.getName() + ", address = " + device.getAddress() + ", bondState = " + device.getBondState());

                /**
                 I/Bluetooth: name = null, address = 78:4F:43:96:37:A0, bondState = 10
                 I/Bluetooth: name = huoxy_MBP, address = 6C:BD:B2:B9:DC:62, bondState = 10
                 I/Bluetooth: name = null, address = 7E:5F:1E:40:2A:AD, bondState = 10
                 I/Bluetooth: name = null, address = 7C:B2:47:7D:4B:FB, bondState = 10
                 I/Bluetooth: name = null, address = 64:B7:55:37:26:59, bondState = 10
                 I/Bluetooth: name = null, address = 4C:32:75:8A:78:02, bondState = 10
                 I/Bluetooth: name = vivo Y66, address = 70:D9:23:3D:A2:6D, bondState = 10
                 I/Bluetooth: name = null, address = 23:1A:F0:19:86:7D, bondState = 10
                 I/Bluetooth: name = vivo Y66, address = F4:70:AB:6A:BD:F3, bondState = 10
                 I/Bluetooth: name = 客厅的小米盒子, address = 00:9E:C8:C7:02:1D, bondState = 10
                 I/Bluetooth: name = null, address = 73:CA:E5:58:6C:A2, bondState = 10
                 I/Bluetooth: name = ian’s MacBook Pro, address = 58:EE:CD:07:11:B3, bondState = 10
                 I/Bluetooth: name = ian’s MacBook Pro, address = 98:01:A7:92:D3:98, bondState = 10
                 I/Bluetooth: name = null, address = 5B:29:B9:A2:A8:35, bondState = 10

                    P.S. bondState取值：BOND_NONE = 10；  BOND_BONDING = 11；  BOND_BONDED = 12
                 */
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //1 - 检查本机是否有蓝牙 & 打开蓝牙
        findViewById(R.id.check_and_enable_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSetEnable();
            }
        });

        //2 - 查找已配对的设备
        findViewById(R.id.find_bound_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findBoundBtDevice();
            }
        });

        //3 - 查找设备
        findViewById(R.id.find_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findBtDevice();
            }
        });

        //4 - 开启可检测性
        findViewById(R.id.start_discoverable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiscoverable();
            }
        });
    }

    //1 - 检查蓝牙 & 打开蓝牙
    private void checkAndSetEnable(){
        if(bluetoothAdapter != null){
            if(bluetoothAdapter.isEnabled()){
                Toast.makeText(this,"本机蓝牙正常", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"本机蓝牙还未打开", Toast.LENGTH_SHORT).show();
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT, REQUEST_ENABLE_BLUETOOTH);
            }
        } else {
            Toast.makeText(this,"本机没有蓝牙设备", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
                Toast.makeText(this, "蓝牙已打开", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_DISCOVERABLE_BLUETOOTH) {
                Toast.makeText(this, "已启动可检测性", Toast.LENGTH_SHORT).show();
            }
        } else if(resultCode == DISCOVERABLE_DURATION) {
            Toast.makeText(this, "已启动可检测性" + DISCOVERABLE_DURATION + "S", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "resultCode = " + resultCode);
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    //2 - 查找已配对的蓝牙设备
    private void findBoundBtDevice(){
        if(bluetoothAdapter != null){
            final Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
            if(bondedDevices.size() > 0){
                for(BluetoothDevice device : bondedDevices) {
                    Log.i(TAG, "name = " + device.getName() + ", mac = " + device.getAddress());
                }
            }else {
                Toast.makeText(this,"暂时没有配对的设备", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"本机没有蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    //3 - 查找蓝牙设备
    private void findBtDevice(){
        if(bluetoothAdapter != null){
            if(bluetoothAdapter.startDiscovery()){
                Toast.makeText(this,"查找中...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"查找失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"本机没有蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    //4 - 开启可检测性
    private void startDiscoverable(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
        //返回的resultCode为请求持续时间EXTRA_DISCOVERABLE_DURATION值
        startActivityForResult(intent, REQUEST_DISCOVERABLE_BLUETOOTH);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 及时停止扫描设备
        if(bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(bluetoothReceiver);
    }

    //1 - 服务器端示例代码
    private class BluetoothServer extends Thread{
        private final BluetoothServerSocket serverSocket;

        public BluetoothServer(){
            BluetoothServerSocket temp = null;
            try {
                //TODO name & uuid
                temp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("HuoBT", UUID.randomUUID());
            } catch (Exception e){
                e.printStackTrace();
            }

            serverSocket = temp;
        }

        @Override
        public void run() {
            BluetoothSocket clientSocket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true){
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // If a connection was accepted
                if(clientSocket != null){
                    try {
                        //TODO  Do work to manage the connection (in a separate thread)
                        //manageConnectedSocket(clientSocket);

                        // close server
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void cancel(){
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //2 - Bluetooth Client Demo
    private class BluetoothClient extends Thread{
        private final BluetoothSocket socket;
        private final BluetoothDevice device;

        public BluetoothClient(BluetoothDevice device){
            this.device = device;

            BluetoothSocket temp = null;
            try {
                //Get a BluetoothSocket to connect with the given BluetoothDevice
                //UUID is the app's UUID string, also used by the server code
                temp = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket = temp;
        }

        @Override
        public void run() {
            // Cancel discovery because it will slow down the connection
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                // Unable to connect; close the socket and get out
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            //TODO Do work to manage the connection (in a separate thread)
            //manageConnectedSocket(socket);
        }

        //Will cancel an in-progress connection, and close the socket
        public void cancel(){
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //3 - 管理连接示例
    private class ConnectedThread extends Thread{
        private BluetoothSocket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket){
            this.socket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tempIn = socket.getInputStream();
                tempOut = socket.getOutputStream();
            } catch (Exception e){
                e.printStackTrace();
            }

            inputStream = tempIn;
            outputStream = tempOut;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            long bytes;

            // Keep listening to the InputStream until an exception occurs
            while (true){
                try {
                    bytes = inputStream.read(buffer);

                    //TODO Send the obtained bytes to the UI activity
                    //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] data){
            try {
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel(){
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
