package com.abhinav.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences sharedPreferences;
    static SQLiteDatabase database;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if( !ResourceHelper.isConnected() ){
                findViewById(R.id.noInternetImage).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went Wrong!",Toast.LENGTH_SHORT).show();
        }

        database = this.openOrCreateDatabase( StorageHelper.DATABASE_NAME, Context.MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS " + StorageHelper.TABLE_NAME + " ( id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, url_to_news VARCHAR, url_to_image VARCHAR )";
        database.execSQL(sql);



        sharedPreferences = getApplicationContext().getSharedPreferences( "com.abhinav.newsfeed", MODE_PRIVATE );

        ViewPager viewPagerMain = findViewById(R.id.mainViewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NewsFragmentPageAdapter newsFragmentPageAdapter = new NewsFragmentPageAdapter(getSupportFragmentManager());
        viewPagerMain.setAdapter(newsFragmentPageAdapter);
        tabLayout.setupWithViewPager(viewPagerMain);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this ,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if( id == R.id.news_notes){
                    startActivity( new Intent( MainActivity.this, NotesActivity.class));
                }

                if( id == R.id.weather_menu ){
                    startActivity( new Intent( MainActivity.this, WeatherActivity.class));
                }

                if( id == R.id.share_app){
                    Toast.makeText(MainActivity.this, "Feature will be implemented when this app will be on PlayStore :)", Toast.LENGTH_SHORT).show();
                }

                if( id == R.id.about){
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                }

                if( id == R.id.account_menu ){
                    startActivity( new Intent(MainActivity.this, MyAccountActivity.class));
                }

                if( id == R.id.category_menu ){
                    startActivity(new Intent(MainActivity.this, CountrySelectActivity.class));
                }

                if( id == R.id.read_news_later){
                    startActivity(new Intent(MainActivity.this, ReadLaterActivity.class));
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if( item.getItemId() == android.R.id.home){
            if( drawerLayout.isDrawerOpen(GravityCompat.START) ){
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
