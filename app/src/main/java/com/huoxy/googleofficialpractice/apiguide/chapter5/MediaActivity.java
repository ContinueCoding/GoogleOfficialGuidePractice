package com.huoxy.googleofficialpractice.apiguide.chapter5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

public class MediaActivity extends AppCompatActivity {

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
    }
}
