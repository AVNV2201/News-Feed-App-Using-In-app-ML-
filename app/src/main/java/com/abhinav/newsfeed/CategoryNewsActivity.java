package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CategoryNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);

        int country = getIntent().getIntExtra("country",-1);
        int category = getIntent().getIntExtra("category",-1);

        String title = ResourceHelper.countryNameList.get(country) + " - " + ResourceHelper.categoryNameList.get(category);
        setTitle(title);

        RecyclerView recyclerView = findViewById(R.id.categoryNewsRcView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsDownloadHelper helper = new NewsDownloadHelper(this);
        helper.getNewsList(recyclerView, ResourceHelper.countryCodes.get(country), ResourceHelper.categoryCodes.get(category));

    }
}





