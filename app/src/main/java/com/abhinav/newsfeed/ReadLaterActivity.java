package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import java.sql.SQLData;
import java.util.ArrayList;

public class ReadLaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);
        setTitle("Read Later");

        RecyclerView recyclerView = findViewById(R.id.readLaterRcView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        ReadLaterNewsCardAdapter adapter = new ReadLaterNewsCardAdapter(this );
        recyclerView.setAdapter(adapter);
    }
}

