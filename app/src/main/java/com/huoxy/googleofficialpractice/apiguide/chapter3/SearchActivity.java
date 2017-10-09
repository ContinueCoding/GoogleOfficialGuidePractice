package com.huoxy.googleofficialpractice.apiguide.chapter3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import com.huoxy.googleofficialpractice.R;

/**
 * 搜索页面 —— Search Dialog & Search Widget
 *
 */
public class SearchActivity extends AppCompatActivity {

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


    }

    @Override
    public boolean onSearchRequested() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SearchableActivity.EXTRA_BOOLEAN_DATA, true);
        startSearch("initialQueryString", false, bundle, false);
        return true;
    }

}
