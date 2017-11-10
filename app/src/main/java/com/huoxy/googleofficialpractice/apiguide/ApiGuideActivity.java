package com.huoxy.googleofficialpractice.apiguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huoxy.googleofficialpractice.R;
import com.huoxy.googleofficialpractice.apiguide.chapter1.AppComponentActivity;
import com.huoxy.googleofficialpractice.apiguide.chapter2.AppResourcesActivity;
import com.huoxy.googleofficialpractice.apiguide.chapter3.UserInterfaceActivity;
import com.huoxy.googleofficialpractice.apiguide.chapter4.AnimationGraphicActivity;
import com.huoxy.googleofficialpractice.apiguide.chapter5.MediaActivity;
import com.huoxy.googleofficialpractice.apiguide.chapter6.LocationSensorsActivity;

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

        //2 - 应用资源
        findViewById(R.id.app_resources).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, AppResourcesActivity.class));
            }
        });


        //3 - 用户界面
        findViewById(R.id.user_interface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, UserInterfaceActivity.class));
            }
        });

        //4 - 动画 & 图形
        findViewById(R.id.animation_graphic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, AnimationGraphicActivity.class));
            }
        });

        //5 - 多媒体App
        findViewById(R.id.media_apps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, MediaActivity.class));
            }
        });

        //6 - Location and Sensors
        findViewById(R.id.location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApiGuideActivity.this, LocationSensorsActivity.class));
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
