package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huoxy.googleofficialpractice.R;

public class AppComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_component);

        //1 - Fragment切换动画
        findViewById(R.id.fragment_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppComponentActivity.this, FragmentTransitionActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //2 - 加载器 - 在Activity or Fragment中异步加载数据
        findViewById(R.id.loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppComponentActivity.this, LoaderActivity.class));
            }
        });

        //3 - Service
        findViewById(R.id.service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppComponentActivity.this, ServiceActivity.class));
            }
        });

        //4 - ContentProvider
        findViewById(R.id.content_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppComponentActivity.this, ContentProviderActivity.class));
            }
        });
    }
}
