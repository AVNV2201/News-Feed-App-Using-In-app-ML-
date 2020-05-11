package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Objects;

public class CountrySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_select);
        setTitle("Select Country");

        ArrayList<Integer> countryImageList = new ArrayList<Integer>(ResourceHelper.countryFlagsImageList);
        ArrayList<String> countryNameList = new ArrayList<>(ResourceHelper.countryNameList);

        RecyclerView recyclerView = findViewById(R.id.countrySelectRcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        CategoryCardAdapter adapter = new CategoryCardAdapter(CountrySelectActivity.this, countryNameList, countryImageList, -1);
        recyclerView.setAdapter(adapter);
    }
}
