package com.huoxy.googleofficialpractice.apiguide.chapter4;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huoxy.googleofficialpractice.R;

public class AnimationGraphicActivity extends AppCompatActivity {

    private static final String TAG = "Animation";

    private LinearLayout container;
    private Button addViewBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_graphic);

        container = (LinearLayout) findViewById(R.id.container);
        addViewBtn = (Button) findViewById(R.id.add_view_btn);

        anim1();

        //区别：LayoutAnimationController - android:layoutAnimation
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
        addViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim2();
            }
        });
    }

    //1 - Animating with ValueAnimator
    private void anim1() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(1000);
        //监听动画执行过程
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "fraction = " + animation.getAnimatedFraction() + ", value = " + animation.getAnimatedValue() + ", playTime = " + animation.getCurrentPlayTime());
            }
        });
        //监听动画执行阶段
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart() ===== ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd() ===== ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        /*
            10-17 16:37:09.801 7608-7608/package I/Animation: fraction = 0.0, value = 0, playTime = 3
            10-17 16:37:09.801 7608-7608/package I/Animation: onAnimationStart() =====
            10-17 16:37:09.811 7608-7608/package I/Animation: fraction = 0.0, value = 0, playTime = 12
            10-17 16:37:09.861 7608-7608/package I/Animation: fraction = 0.0064039826, value = 0, playTime = 57
            10-17 16:37:09.891 7608-7608/package I/Animation: fraction = 0.017309189, value = 1, playTime = 90
            10-17 16:37:09.911 7608-7608/package I/Animation: fraction = 0.024959475, value = 2, playTime = 114
            10-17 16:37:09.961 7608-7608/package I/Animation: fraction = 0.055931747, value = 5, playTime = 157
            10-17 16:37:09.971 7608-7608/package I/Animation: fraction = 0.068038285, value = 6, playTime = 169
            10-17 16:37:09.981 7608-7608/package I/Animation: fraction = 0.08209628, value = 8, playTime = 186
            10-17 16:37:10.001 7608-7608/package I/Animation: fraction = 0.09734607, value = 9, playTime = 202
            10-17 16:37:10.021 7608-7608/package I/Animation: fraction = 0.11374399, value = 11, playTime = 219
            10-17 16:37:10.031 7608-7608/package I/Animation: fraction = 0.1312435, value = 13, playTime = 236
            10-17 16:37:10.051 7608-7608/package I/Animation: fraction = 0.148675, value = 14, playTime = 253
            10-17 16:37:10.071 7608-7608/package I/Animation: fraction = 0.1681675, value = 16, playTime = 270
            10-17 16:37:10.091 7608-7608/package I/Animation: fraction = 0.18860611, value = 18, playTime = 287
            10-17 16:37:10.101 7608-7608/package I/Animation: fraction = 0.20993274, value = 20, playTime = 303
            10-17 16:37:10.121 7608-7608/package I/Animation: fraction = 0.23208651, value = 23, playTime = 320
            10-17 16:37:10.141 7608-7608/package I/Animation: fraction = 0.25500444, value = 25, playTime = 337
            10-17 16:37:10.151 7608-7608/package I/Animation: fraction = 0.27721345, value = 27, playTime = 355
            10-17 16:37:10.171 7608-7608/package I/Animation: fraction = 0.30142605, value = 30, playTime = 371
            10-17 16:37:10.191 7608-7608/package I/Animation: fraction = 0.32620478, value = 32, playTime = 387
            10-17 16:37:10.201 7608-7608/package I/Animation: fraction = 0.3514793, value = 35, playTime = 404
            10-17 16:37:10.221 7608-7608/package I/Animation: fraction = 0.37717712, value = 37, playTime = 421
            10-17 16:37:10.241 7608-7608/package I/Animation: fraction = 0.4016847, value = 40, playTime = 438
            10-17 16:37:10.251 7608-7608/package I/Animation: fraction = 0.4279946, value = 42, playTime = 455
            10-17 16:37:10.271 7608-7608/package I/Animation: fraction = 0.45450982, value = 45, playTime = 472
            10-17 16:37:10.291 7608-7608/package I/Animation: fraction = 0.48115498, value = 48, playTime = 489
            10-17 16:37:10.301 7608-7608/package I/Animation: fraction = 0.5078536, value = 50, playTime = 505
            10-17 16:37:10.321 7608-7608/package I/Animation: fraction = 0.5345301, value = 53, playTime = 522
            10-17 16:37:10.341 7608-7608/package I/Animation: fraction = 0.55954856, value = 55, playTime = 539
            10-17 16:37:10.351 7608-7608/package I/Animation: fraction = 0.5859647, value = 58, playTime = 556
            10-17 16:37:10.371 7608-7608/package I/Animation: fraction = 0.6121354, value = 61, playTime = 573
            10-17 16:37:10.391 7608-7608/package I/Animation: fraction = 0.6379864, value = 63, playTime = 589
            10-17 16:37:10.401 7608-7608/package I/Animation: fraction = 0.6634439, value = 66, playTime = 606
            10-17 16:37:10.421 7608-7608/package I/Animation: fraction = 0.6869796, value = 68, playTime = 623
            10-17 16:37:10.441 7608-7608/package I/Animation: fraction = 0.7114672, value = 71, playTime = 640
            10-17 16:37:10.461 7608-7608/package I/Animation: fraction = 0.735352, value = 73, playTime = 657
            10-17 16:37:10.471 7608-7608/package I/Animation: fraction = 0.7585654, value = 75, playTime = 674
            10-17 16:37:10.491 7608-7608/package I/Animation: fraction = 0.78104174, value = 78, playTime = 690
            10-17 16:37:10.511 7608-7608/package I/Animation: fraction = 0.8027165, value = 80, playTime = 707
            10-17 16:37:10.521 7608-7608/package I/Animation: fraction = 0.8223288, value = 82, playTime = 724
            10-17 16:37:10.541 7608-7608/package I/Animation: fraction = 0.8422736, value = 84, playTime = 741
            10-17 16:37:10.561 7608-7608/package I/Animation: fraction = 0.8612423, value = 86, playTime = 757
            10-17 16:37:10.571 7608-7608/package I/Animation: fraction = 0.8791809, value = 87, playTime = 774
            10-17 16:37:10.591 7608-7608/package I/Animation: fraction = 0.8960383, value = 89, playTime = 791
            10-17 16:37:10.611 7608-7608/package I/Animation: fraction = 0.9108732, value = 91, playTime = 808
            10-17 16:37:10.621 7608-7608/package I/Animation: fraction = 0.92549723, value = 92, playTime = 825
            10-17 16:37:10.641 7608-7608/package I/Animation: fraction = 0.938908, value = 93, playTime = 842
            10-17 16:37:10.661 7608-7608/package I/Animation: fraction = 0.951067, value = 95, playTime = 859
            10-17 16:37:10.671 7608-7608/package I/Animation: fraction = 0.96193975, value = 96, playTime = 875
            10-17 16:37:10.691 7608-7608/package I/Animation: fraction = 0.9714953, value = 97, playTime = 892
            10-17 16:37:10.711 7608-7608/package I/Animation: fraction = 0.9792609, value = 97, playTime = 909
            10-17 16:37:10.721 7608-7608/package I/Animation: fraction = 0.98618495, value = 98, playTime = 926
            10-17 16:37:10.741 7608-7608/package I/Animation: fraction = 0.9917226, value = 99, playTime = 943
            10-17 16:37:10.761 7608-7608/package I/Animation: fraction = 0.995858, value = 99, playTime = 959
            10-17 16:37:10.771 7608-7608/package I/Animation: fraction = 0.9985795, value = 99, playTime = 976
            10-17 16:37:10.791 7608-7608/package I/Animation: fraction = 0.9998791, value = 99, playTime = 993
            10-17 16:37:10.811 7608-7608/package I/Animation: fraction = 1.0, value = 100, playTime = 1010
            10-17 16:37:10.811 7608-7608/package I/Animation: onAnimationEnd() =====
         */
    }

    //2 - LayoutTransition
    private void anim2() {
        TextView textView = new TextView(this);
        textView.setText("New View");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.RED);
        container.addView(textView);
        textView.animate();
    }

    private void createNewCanvas() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
    }


}
