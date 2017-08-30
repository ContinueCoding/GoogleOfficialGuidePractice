package com.huoxy.googleofficialpractice.training.chapter2;

import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huoxy.googleofficialpractice.R;

public class ContentSharingActivity extends AppCompatActivity {

    private ParcelFileDescriptor fileDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_sharing);


    }
}
