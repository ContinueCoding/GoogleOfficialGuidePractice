package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

/**
 * ContentProvider Demo
 */
public class ContentProviderActivity extends AppCompatActivity {

    private static final String TAG = "ContentProvider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        //用户字典ContentProvider
        findViewById(R.id.cp_user_dictionary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, "", null, "");
                if(cursor != null && cursor.moveToLast()){
                    int count = cursor.getCount();
                    String word = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.WORD));
                    Log.i(TAG, "last word = " + word);//bd51783c979171240f399eb0a09cc5b7
                    Toast.makeText(ContentProviderActivity.this, "count = " + count + ", last word = " + word, Toast.LENGTH_SHORT).show();//4
                    cursor.close();
                }
            }
        });


    }
}
