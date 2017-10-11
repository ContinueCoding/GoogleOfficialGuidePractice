package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huoxy.googleofficialpractice.R;

/**
 * 拖放 —— 移动数据 or UI操作
 */
public class DragAndDropActivity extends AppCompatActivity {

    private static final String TAG = "DragAndDrop";

    private static final String IMAGEVIEW_TAG = "icon_bitmap";

    ImageView appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        appIcon = (ImageView) findViewById(R.id.app_icon);
        appIcon.setTag(IMAGEVIEW_TAG);
        appIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData clipData = new ClipData(v.getTag().toString(), mimeTypes, item);

                MyDragShadowBuilder myShadow = new MyDragShadowBuilder(appIcon);

                v.startDrag(clipData, myShadow, null, 0);

                return true;
            }
        });

        appIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();

                // Handles each of the expected events
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i(TAG, "DragEvent.ACTION_DRAG_STARTED ===== ");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(TAG, "DragEvent.ACTION_DRAG_ENTERED ===== ");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i(TAG, "DragEvent.ACTION_DRAG_LOCATION ===== ");
                        // Ignore the event
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i(TAG, "DragEvent.ACTION_DRAG_EXITED ===== ");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i(TAG, "DragEvent.ACTION_DROP ===== ");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i(TAG, "DragEvent.ACTION_DRAG_ENDED ===== ");
                        // Turns off any color tinting
                        break;
                    // An unknown action type was received.
                    default:
                        Log.e(TAG, "Unknown action type received by OnDragListener.");
                        break;
                }
                return true;
            }
        });
    }

    //定义了 MyDragShadowBuilder 它会创建灰色小方框形式的拖拽阴影用于拖拽 View
    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private static Drawable shadow;

        public MyDragShadowBuilder(View view) {
            super(view);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            // Defines local variables
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = getView().getWidth() / 2;

            // Sets the height of the shadow to half the height of the original View
            height = getView().getHeight() / 2;

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            outShadowSize.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            outShadowTouchPoint.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }
}
