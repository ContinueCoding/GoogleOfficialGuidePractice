package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

import java.util.ArrayList;
import java.util.Arrays;

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
                    Log.i(TAG, "count = " + count + ", last word = " + word);//bd51783c979171240f399eb0a09cc5b7
                    Toast.makeText(ContentProviderActivity.this, "count = " + count + ", last word = " + word, Toast.LENGTH_SHORT).show();//4
                    cursor.close();
                }
            }
        });

        //批量访问 - 更高性能
        findViewById(R.id.cp_batch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ContentProviderOperation> operations = new ArrayList<>();
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI).withValue(UserDictionary.Words.WORD, "Kaka").withYieldAllowed(true).build());
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI).withValue(UserDictionary.Words.WORD, "Rooney").withYieldAllowed(true).build());
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI).withValue(UserDictionary.Words.WORD, "Ronaldo").withYieldAllowed(true).build());
                try {
                    ContentProviderResult[] results = getContentResolver().applyBatch(UserDictionary.AUTHORITY, operations);
                    //results = [ContentProviderResult(uri=content://user_dictionary/words/8),
                    //           ContentProviderResult(uri=content://user_dictionary/words/9),
                    //           ContentProviderResult(uri=content://user_dictionary/words/10)]
                    Log.i(TAG, "results = " + Arrays.deepToString(results));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(ContentProviderActivity.this, "批量插入成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
