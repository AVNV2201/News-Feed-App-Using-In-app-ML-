package com.abhinav.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DisplayNewsActivity extends AppCompatActivity {

    String title, description, urlToNews, urlToImage;
    ProgressBar progressBar;
    int isSavedInRwadLater ;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        setTitle("News Article");

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESC");
        urlToNews = intent.getStringExtra("URL");
        urlToImage = intent.getStringExtra("IMGURL");

        if( StorageHelper.isStoredInDatabase(database, urlToNews ) ){
            isSavedInRwadLater = 1;
        } else {
            isSavedInRwadLater = 0;
        }

        progressBar = findViewById(R.id.displaynewsProgressBar);
        final WebView displayNewsWebView = findViewById(R.id.displayNewsWebView);
        WebSettings webSettings = displayNewsWebView.getSettings();
        displayNewsWebView.setVisibility(View.INVISIBLE);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSaveFormData(true);
        displayNewsWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        displayNewsWebView.loadUrl(urlToNews);

        displayNewsWebView.setWebChromeClient( new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
        displayNewsWebView.setWebViewClient( new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                Toast.makeText(DisplayNewsActivity.this, "Loading Page...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
                displayNewsWebView.setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.addNoteFab) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( DisplayNewsActivity.this, NotesActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_news_menu,menu);

        MenuItem readLateMenu = menu.findItem(R.id.read_later_display_news);
        if( isSavedInRwadLater == 0 ){
            readLateMenu.setIcon(R.drawable.read_later);
        } else {
            readLateMenu.setIcon(R.drawable.read_later_saved);
        }

        MenuItem shareMenu = menu.findItem(R.id.share_display_menu);
        

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if( id == R.id.read_later_display_news){
            
            if( isSavedInRwadLater == 0 ){

                item.setIcon(R.drawable.read_later_saved);
                News news = new News(title, description, "", urlToNews, urlToImage);
                StorageHelper.insertNewsIntoDatabase( database, news);

                Toast.makeText(DisplayNewsActivity.this, "Saved to Read Later", Toast.LENGTH_SHORT).show();
                isSavedInRwadLater = 1;

            } else {
                Toast.makeText(DisplayNewsActivity.this, "Already Saved", Toast.LENGTH_SHORT).show();
            }
            
        }

        return true ;
    }
}
