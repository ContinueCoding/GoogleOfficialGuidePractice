package com.huoxy.googleofficialpractice.apiguide.chapter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.huoxy.googleofficialpractice.R;

public class AppResourcesActivity extends AppCompatActivity {

    AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_resources);
    }
}
