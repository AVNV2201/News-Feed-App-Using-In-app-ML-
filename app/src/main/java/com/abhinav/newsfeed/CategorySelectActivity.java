package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CategorySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        setTitle("Select Category");

        int country = getIntent().getIntExtra("country", -1);

        if( country == -1 ){ finish(); }

        ArrayList<String> categoryNameList = new ArrayList<>(ResourceHelper.categoryNameList);
        ArrayList<Integer> categoryImageList = new ArrayList<>(ResourceHelper.categoryImageList);

        RecyclerView recyclerView = findViewById(R.id.categorySelectRcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        CategoryCardAdapter adapter = new CategoryCardAdapter(this, categoryNameList, categoryImageList, country);
        recyclerView.setAdapter(adapter);
    }
}







