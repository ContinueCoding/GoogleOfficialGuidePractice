package com.huoxy.googleofficialpractice.apiguide.chapter1;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI)
                        .withValue(UserDictionary.Words.WORD, "Kaka").withYieldAllowed(true).build());
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI)
                        .withValue(UserDictionary.Words.WORD, "Rooney").withYieldAllowed(true).build());
                operations.add(ContentProviderOperation.newInsert(UserDictionary.Words.CONTENT_URI)
                        .withValue(UserDictionary.Words.WORD, "Ronaldo").withYieldAllowed(true).build());
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

        //日历 - 查看日历(查看特定日期 or 事件)
        findViewById(R.id.cp_calendar_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startMillis = System.currentTimeMillis() + 4 * 24 * 3600 * 1000;
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);
            }
        });

        //日历 - 插入事件
        findViewById(R.id.cp_calendar_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2017, 8, 16, 10, 0);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2017, 8, 16, 12, 0);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Fitness")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The HardSugar Gym")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "huo_xy2010@163.com");
                startActivity(intent);
            }
        });

        //日历 - 编辑事件
        findViewById(R.id.cp_calendar_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContentProviderActivity.this, "TODO", Toast.LENGTH_LONG).show();
            }
        });

        //联系人 - 用户个人资料
        findViewById(R.id.cp_contacts_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Sets the columns to retrieve for the user profile
                    String[] mProjection = new String[]
                            {
                                    ContactsContract.Profile._ID,
                                    ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
                                    ContactsContract.Profile.LOOKUP_KEY,
                                    ContactsContract.Profile.PHOTO_THUMBNAIL_URI
                            };

                    // Retrieves the profile from the Contacts Provider
                    Cursor mProfileCursor = getContentResolver().query(
                            ContactsContract.Profile.CONTENT_URI,
                            mProjection,
                            null,
                            null,
                            null);

                    if (mProfileCursor != null && mProfileCursor.moveToNext()) {
                        String name = mProfileCursor.getString(mProfileCursor.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME_PRIMARY));
                        String lookupKey = mProfileCursor.getString(mProfileCursor.getColumnIndex(ContactsContract.Profile.LOOKUP_KEY));
                        Log.i(TAG, "用户个人资料：name = " + name + ", key = " + lookupKey);
                    } else {
                        Toast.makeText(ContentProviderActivity.this, "获取用户个人资料失败", Toast.LENGTH_SHORT).show();
                    }

                    if (mProfileCursor != null) {
                        mProfileCursor.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
