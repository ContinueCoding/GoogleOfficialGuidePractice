package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaRecorder";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1001;

    private Button recordBtn, playBtn;

    private String fileName;
    private boolean startRecording = true, startPlaying = true;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        //1 - audio
        findViewById(R.id.audio_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MediaActivity.this, "Audio App", Toast.LENGTH_SHORT).show();
            }
        });

        //2 - video
        findViewById(R.id.video_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MediaActivity.this, "Video App", Toast.LENGTH_SHORT).show();
            }
        });

        //-------------Media Recorder Test------------

        fileName = getExternalCacheDir().getAbsolutePath() + "/audio_record_test.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        //3.1 - media recorder录制
        recordBtn = (Button) findViewById(R.id.media_recorder_record);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(startRecording);
                if (startRecording) {
                    recordBtn.setText("Stop recording");
                } else {
                    recordBtn.setText("Start recording");
                }
                startRecording = !startRecording;
            }
        });

        //3.2 - media recorder播放
        playBtn = (Button) findViewById(R.id.media_recorder_play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(startPlaying);
                if (startPlaying) {
                    playBtn.setText("Stop playing");
                } else {
                    playBtn.setText("Start playing");
                }
                startPlaying = !startPlaying;
            }
        });
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    //开始录制
    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.reset();//解决-MediaRecorder: mediarecorder went away with unhandled events
        mediaRecorder.release();
        mediaRecorder = null;
    }


    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    //开始播放
    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.i(TAG, "MediaPlayer播放完毕");
                }
            });
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                Log.i(TAG, "Record Permission = " + permissionToRecordAccepted);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
