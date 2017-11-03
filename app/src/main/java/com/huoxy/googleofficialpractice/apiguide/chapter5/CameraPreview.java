package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by huoxy on 2017/11/1.
 *  自定义相机预览View 6214 8301 2063 1915
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";

    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        this.camera = camera;

        holder = getHolder();
        holder.addCallback(this);
        // 拍照时保持屏幕常亮
        holder.setKeepScreenOn(true);
        // deprecated setting, but required on Android versions prior to Android 3.0, Android 3.0之后会自动设置
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

            startFaceDetection();
        } catch (IOException e) {
            Log.e(TAG, "surfaceCreated() ----- Error setting camera preview: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if(this.holder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        //stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        //TODO 问题：没有聚焦 & 方向偏移90度 ？？？

        // TODO set preview size and make any resize, rotate or reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

            startFaceDetection();
        } catch (Exception e){
            Log.e(TAG, "surfaceChanged() ----- Error setting camera preview: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    private void startFaceDetection(){
        // Try starting Face Detection
        Camera.Parameters params = camera.getParameters();

        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            Log.i(TAG, "getMaxNumDetectedFaces() = " + params.getMaxNumDetectedFaces());//getMaxNumDetectedFaces() = 16
            camera.setFaceDetectionListener(new MyFaceDetectionListener());
            camera.startFaceDetection();
        }
    }

    private class MyFaceDetectionListener implements Camera.FaceDetectionListener{

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if(faces.length > 0){
                //CameraFaceDetection: face detected: 1, Face 1 Location X: -452, Y: -107
                Log.i("CameraFaceDetection", "face detected: "+ faces.length +
                        ", Face 1 Location X: " + faces[0].rect.centerX() +
                        ", Y: " + faces[0].rect.centerY() );
            }
        }
    }
}
