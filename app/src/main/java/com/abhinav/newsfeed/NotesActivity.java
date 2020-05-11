package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {
    
    static ArrayList<String> notesTitle;
    static ArrayList<String> notesContent;
    static NotesCardAdapter notesCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesContent = new ArrayList<>() ;
        notesTitle = new ArrayList<>() ;

        notesTitle.clear();
        notesContent.clear();

        try {
            notesTitle = (ArrayList<String>) ObjectSerializer.deserialize( MainActivity.sharedPreferences.getString(
                    "notesTitle", ObjectSerializer.serialize( new ArrayList<String>() ) ) ) ;
            notesContent = (ArrayList<String>) ObjectSerializer.deserialize( MainActivity.sharedPreferences.getString(
                    "notesContent", ObjectSerializer.serialize( new ArrayList<String>() ) ) ) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if( notesTitle.size() == 0 || notesContent.size() == 0 ||
                notesContent.size() != notesTitle.size()) {
            notesContent.clear();
            notesTitle.clear();
            notesTitle.add("Example Note") ;
            notesContent.add("") ;
        }

//        if( getIntent().getIntExtra("flag", -1 ) != -1 ){
//            startActivity( new Intent( NotesActivity.this, NotesEditorActivity.class) );
//            finish();
//        }

        RecyclerView recyclerView = findViewById(R.id.notesRV);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        notesCardAdapter = new NotesCardAdapter(NotesActivity.this, notesTitle, notesContent);
        recyclerView.setAdapter(notesCardAdapter);

        FloatingActionButton fab = findViewById(R.id.notesFab) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotesActivity.this, NotesEditorActivity.class) );
            }
        });
    }
}
