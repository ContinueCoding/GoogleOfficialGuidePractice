package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.huoxy.googleofficialpractice.R;

/**
 * 搜索页面 —— Search Dialog & Search Widget
 *
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private Button searchDialog;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //1 - Search Dialog with Context Data
        searchDialog = (Button) findViewById(R.id.search_dialog);
        searchDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchRequested();
            }
        });

        //TODO 2 - SearchView
        searchView = (SearchView) findViewById(R.id.search_view);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // Do not iconify the widget; expand it by default
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("请输入搜索关键词");
        searchView.setSubmitButtonEnabled(true);//右侧箭头'>'表示提交

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });


    }

    @Override
    public boolean onSearchRequested() {
        Log.i(TAG, "onSearchRequested() ===== ");
        Bundle bundle = new Bundle();
        bundle.putBoolean(SearchableActivity.EXTRA_BOOLEAN_DATA, true);
        startSearch("initialQueryString", false, bundle, false);
        return true;
    }

}
