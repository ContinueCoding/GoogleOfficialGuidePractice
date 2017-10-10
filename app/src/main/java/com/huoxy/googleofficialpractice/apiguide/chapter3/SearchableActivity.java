package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.app.SearchManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.huoxy.googleofficialpractice.R;

/**
 * 根据搜索关键字执行搜索 & 显示搜索结果页面
 *
 */
public class SearchableActivity extends AppCompatActivity {
    private static final String TAG = "SearchableActivity";

    public static final String EXTRA_BOOLEAN_DATA = "extra_boolean_data";

    private ListView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        searchResult = (ListView) findViewById(R.id.search_result_list);

        processQuery();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        processQuery();
    }

    private void processQuery() {
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String keyword = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            doSearch(keyword, bundle);
        }
    }

    private void doSearch(String keyword, @Nullable Bundle bundleExtra){
        Toast.makeText(this, "keyword = " + keyword, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "keyword = " + keyword);
        if(bundleExtra != null){
            boolean aBoolean = bundleExtra.getBoolean(EXTRA_BOOLEAN_DATA, false);
            Log.i(TAG, "extra_boolean_data = " + aBoolean);
        }
    }
}
