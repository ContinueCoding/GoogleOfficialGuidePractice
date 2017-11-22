package com.huoxy.googleofficialpractice.apiguide.chapter8;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huoxy.googleofficialpractice.R;

public class TextAndInputActivity extends AppCompatActivity {

    ClipboardManager manager;
    ClipData clipData;
    ClipData.Item item;
    ClipDescription description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_and_input);
    }
}
