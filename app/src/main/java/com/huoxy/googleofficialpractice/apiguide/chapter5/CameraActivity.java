package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 自定义相机页面
 *
 */
public class CameraActivity extends AppCompatActivity {
    private final static String TAG = "CameraActivity";

    Collections c;
    Collection c1;
    private Camera camera;
    private CameraPreview cameraPreview;

    private Button take_picture;

    //4 - 拍照 - 保存照片
    private Camera.PictureCallback myPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picturePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if(picturePath == null){
                Log.e(TAG, "Error creating media file, check storage permissions!");
                return;
            }

            try {
                String pictureFile = picturePath.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Toast.makeText(CameraActivity.this, "拍照成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "保存照片失败，e.message() = " + e.getMessage());
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        take_picture = (Button) findViewById(R.id.take_picture);

        //3 - 预览相机实时画面
        if(checkCameraHardware()){
            camera = getCameraInstance();
            camera.setDisplayOrientation(90);//解决相机旋转90度的问题

            //5 - Use Camera Features
            Camera.Parameters parameters = camera.getParameters();
            /*Log.i(TAG, "Default focus mode = " + parameters.getFocusMode());//Default focus mode = auto
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);*/


            //6 - metering and focus areas
            if(parameters.getMaxNumMeteringAreas() > 0){
                //parameters.getMaxNumMeteringAreas() = 1
                //Log.i(TAG, "parameters.getMaxNumMeteringAreas() = " + parameters.getMaxNumMeteringAreas());
                List<Camera.Area> areaList = new ArrayList<>();

                Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
                areaList.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
                /*Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
                areaList.add(new Camera.Area(areaRect2, 400)); // set weight to 40%*/
                parameters.setMeteringAreas(areaList);
            }
            camera.setParameters(parameters);


            cameraPreview = new CameraPreview(this, camera);
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview);
            frameLayout.addView(cameraPreview);
        }else {
            Toast.makeText(this, "No Camera!", Toast.LENGTH_SHORT).show();
            finish();
        }

        //4 - 拍照
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, myPictureCallback);
            }
        });
    }

    //1 - detect camera hardware
    private boolean checkCameraHardware(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            //checkCameraHardware() === true, camera numbers = 2
            Log.i(TAG, "checkCameraHardware() === true, camera numbers = " + Camera.getNumberOfCameras());
            return true;
        }

        Log.i(TAG, "checkCameraHardware() === false");
        return false;
    }

    //2 - Accessing camera
    private Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open(); // 默认返回后置摄像头！
            //camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT); //如果有多个摄像头（Camera.getNumberOfCameras() > 1）时，也可以指定摄像头ID
        } catch (Exception e){
            Log.e(TAG, "Get Camera Instance Error!");
            e.printStackTrace();
        }

        return camera;
    }



    @Override
    protected void onPause() {
        super.onPause();

        //TODO 如果有MediaRecorder，必须先release MediaRecorder！


        if(camera != null){
            camera.release();
            camera = null;
        }
    }
}
