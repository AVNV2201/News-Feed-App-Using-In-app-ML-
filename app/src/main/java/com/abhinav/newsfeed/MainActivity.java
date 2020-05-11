package com.abhinav.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.time.chrono.MinguoChronology;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences( "com.abhinav.newsfeed", MODE_PRIVATE );

        ViewPager viewPagerMain = findViewById(R.id.mainViewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        NewsFragmentPageAdapter newsFragmentPageAdapter = new NewsFragmentPageAdapter(getSupportFragmentManager());
        viewPagerMain.setAdapter(newsFragmentPageAdapter);
        tabLayout.setupWithViewPager(viewPagerMain);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
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
                    return true;
                }

                if( id == R.id.weather_menu ){
                    startActivity( new Intent( MainActivity.this, WeatherActivity.class));
                    return true;
                }

                if( id == R.id.share_app){
                    Toast.makeText(MainActivity.this, "Feature will be implemented when this app will be on PlayStore :)", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if( id == R.id.about){
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    return true;
                }

                if( id == R.id.account_menu ){
                    startActivity( new Intent(MainActivity.this, MyAccountActivity.class));
                    return true;
                }

                if( id == R.id.category_menu ){
                    startActivity(new Intent(MainActivity.this, CountrySelectActivity.class));
                    return true;
                }

                return false;
            }
        });
    }
}
