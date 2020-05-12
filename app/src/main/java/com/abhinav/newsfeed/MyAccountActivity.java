package com.abhinav.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyAccountActivity extends AppCompatActivity {

    public void signOut(View v ){
        Toast.makeText(this, "Feature will added soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setTitle("My Account");
    }
}
