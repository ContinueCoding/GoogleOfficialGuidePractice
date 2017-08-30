package com.huoxy.googleofficialpractice.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huoxy.googleofficialpractice.R;
import com.huoxy.googleofficialpractice.training.chapter2.ContentSharingActivity;
import com.huoxy.googleofficialpractice.training.chapter3.PhotoAndVideoWithCameraActivity;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        //CH2 - Building Apps with Content Sharing
        findViewById(R.id.chapter2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainingActivity.this, ContentSharingActivity.class));
            }
        });

        //CH3 - Building Apps with MultiMedia
        findViewById(R.id.chapter3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainingActivity.this, PhotoAndVideoWithCameraActivity.class));
            }
        });
    }
}
