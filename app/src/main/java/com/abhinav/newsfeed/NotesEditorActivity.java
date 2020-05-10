package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NotesEditorActivity extends AppCompatActivity {

    int noteId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        EditText noteTitleEditText = findViewById(R.id.noteTitleEditText) ;
        EditText noteContentEditText = findViewById( R.id.noteContentEditText2 ) ;

        noteId = getIntent().getIntExtra("noteId",-1) ;

        if( noteId != -1 ){
            noteTitleEditText.setText( NotesActivity.notesTitle.get(noteId) ) ;
            noteContentEditText.setText( NotesActivity.notesContent.get(noteId) );
        } else{
            NotesActivity.notesContent.add("") ;
            NotesActivity.notesTitle.add("") ;
            noteId = NotesActivity.notesContent.size()-1 ;
        }

        noteTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NotesActivity.notesTitle.set( noteId, String.valueOf( s ) ) ;

                String str = null;
                try {
                    str = ObjectSerializer.serialize(NotesActivity.notesTitle );
                    MainActivity.sharedPreferences.edit().putString( "notesTitle", str ).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        noteContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NotesActivity.notesContent.set( noteId, String.valueOf(s) ) ;

                try {
                    String str = ObjectSerializer.serialize( NotesActivity.notesContent ) ;
                    MainActivity.sharedPreferences.edit().putString("notesContent", str).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NotesActivity.notesCardAdapter.notifyDataSetChanged();
    }
}
