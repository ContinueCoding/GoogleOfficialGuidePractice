package com.huoxy.googleofficialpractice.apiguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huoxy.googleofficialpractice.R;
import com.huoxy.googleofficialpractice.apiguide.chapter1.AppComponentActivity;

public class ApiGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_guide);

        Log.i("保存状态", "onCreate() ------ savedInstanceState为null --> " + (savedInstanceState == null));

        //1 - 应用组件
        findViewById(R.id.app_components).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, AppComponentActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        Log.i("保存状态", "onPause() ------ ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("保存状态", "onStop() ------ ");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("保存状态", "onSaveInstanceState() ------ ");
        outState.putString("test", "ssss");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("保存状态", "onRestoreInstanceState() ------ test = " + savedInstanceState.get("test"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
