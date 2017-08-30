package com.huoxy.googleofficialpractice.training.chapter3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.huoxy.googleofficialpractice.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拍照 & 录像 & 操作Camera & 打印 Demo
 */
public class PhotoAndVideoWithCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "拍照&录像";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    private Button btnPhoto, btnVideo, btnControlCamera, btnPrint;
    private ImageView ivPhoto;
    private VideoView vv_video;

    private String mCurrentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_and_video_with_camera);

        initViews();
        setListeners();
    }

    private void initViews(){
        btnPhoto = (Button) findViewById(R.id.btn_photo);
        btnVideo = (Button) findViewById(R.id.btn_video);
        btnControlCamera = (Button) findViewById(R.id.btn_control_camera);
        btnPrint = (Button) findViewById(R.id.btn_print);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        vv_video = (VideoView) findViewById(R.id.vv_video);
    }

    private void setListeners(){
        btnPhoto.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnControlCamera.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_photo:
                takePhoto();
                break;
            case R.id.btn_video:
                recordVideo();
                break;
            case R.id.btn_control_camera:

                break;
            case R.id.btn_print:
                printImage();
                break;
        }
    }

    //======================= 1 - 拍照相关 ===========================
    private void takePhoto(){
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(photoIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(photoFile != null) {
                //FileProvider返回的content;// uri自动适用于Android 7.0 及更高版本
                Uri uriForFile = FileProvider.getUriForFile(this, "com.huoxy.googleofficialpractice.myprovider", photoFile);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(this, "您的手机上没有相机，无法拍照", Toast.LENGTH_SHORT).show();
        }
    }

    //创建图片资源
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//当前应用的私有目录
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "mCurrentPhotoPath = " + mCurrentPhotoPath);
        return image;
    }

    //将自己App保存的照片添加到系统Gallery中，以供其他App访问 -
    // 但是如果使用getExternalFilesDir()获取图片保存路径时，即使发送此广播，也不能加到Gallery中！
    private void addPictureToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivPhoto.getWidth();
        int targetH = ivPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        Log.i(TAG, "scaleFactor = " + scaleFactor);//4 - 也就是宽、高分别缩小了4倍，图片总共缩小了16倍！

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPhoto.setImageBitmap(bitmap);
    }


    //======================= 2 - 录像相关 ===========================

    private void recordVideo(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            Toast.makeText(this, "您的手机上没有摄像头，无法录像", Toast.LENGTH_SHORT).show();
        }
    }

    //======================= 3 - 操作Camera ===========================


    //======================= 4 - 打印测试 ===========================
    private void printImage(){
        Toast.makeText(this, "开始打印...", Toast.LENGTH_SHORT).show();
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        photoPrinter.printBitmap("ic_launcher.jpg - test print", bitmap);
    }


    //======================= 0 - 拍照、录像回调 ===========================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    //此时Intent data为空
                    /*Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    ivPhoto.setImageBitmap(bitmap);*/
                    ivPhoto.setVisibility(View.VISIBLE);
                    addPictureToGallery();
                    setPic();
                }
                break;
            case REQUEST_VIDEO_CAPTURE:
                if(resultCode == RESULT_OK){
                    Uri videoUri = data.getData();
                    vv_video.setVisibility(View.VISIBLE);
                    vv_video.setVideoURI(videoUri);
                    vv_video.start();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
