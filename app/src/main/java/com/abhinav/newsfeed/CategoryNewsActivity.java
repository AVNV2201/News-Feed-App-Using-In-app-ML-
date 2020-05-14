package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

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

        ArrayList<News> categoryNewsList = new ArrayList<>();

        NewsCardAdapter adapter = new NewsCardAdapter(this, categoryNewsList );
        recyclerView.setAdapter(adapter);

        NewsDownloadHelper helper = new NewsDownloadHelper(this);
        helper.setNewsList(recyclerView, categoryNewsList, ResourceHelper.countryCodes.get(country), ResourceHelper.categoryCodes.get(category));

    }
}





