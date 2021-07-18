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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences sharedPreferences;
    static SQLiteDatabase database;

    static int no_of_rounds;
    static int total_selections ;
    static ArrayList<Integer> no_of_selections;
    static ArrayList<Integer> no_of_rewards;
    static ArrayList<Integer> selections ;
    static ArrayList<Boolean> clicked;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checking for internet connectivity
//        try {
//            if( !ResourceHelper.isConnected() ){
//                Log.i("yoyoyo::::::::::::::::::::"," yo yo ");
//                findViewById(R.id.noInternetImage).setVisibility(View.VISIBLE);
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Something went Wrong!",Toast.LENGTH_SHORT).show();
//        }

        // opening database and table
        database = this.openOrCreateDatabase( StorageHelper.DATABASE_NAME, Context.MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS " + StorageHelper.TABLE_NAME + " ( id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, url_to_news VARCHAR, url_to_image VARCHAR )";
        database.execSQL(sql);


        // getting permanent storage access of shared preferences
        sharedPreferences = getApplicationContext().getSharedPreferences( "com.abhinav.newsfeed", MODE_PRIVATE );

        // ML Data initialization
        no_of_rounds = 1;
        total_selections = 0;
        no_of_rewards = new ArrayList<>();
        no_of_selections = new ArrayList<>();
        selections = new ArrayList<>();
        initializeMLData();

        selections = MLHelper.getSelectionList( no_of_selections,
                                                no_of_rewards,
                                                no_of_rounds,
                                                MLHelper.CUBIC_FILTER
                                                 );
        for( int i = 0; i < selections.size(); i++ ){
            total_selections += selections.get(i);
            no_of_selections.set(i, no_of_selections.get(i) + selections.get(i) );
        }
        clicked = new ArrayList<>(Collections.nCopies(total_selections,false));

        // initializing layout
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


    void initializeMLData(){

        int no_of_categories = ResourceHelper.categoryCodes.size();

        no_of_rounds = sharedPreferences.getInt(MLHelper.storageNameForNoOfRounds,1);

        ArrayList<String> tmp1 = new ArrayList<>();
        ArrayList<String> tmp2= new ArrayList<>();

        try {

            tmp1 = (ArrayList<String>) ObjectSerializer.deserialize( sharedPreferences.getString(
                    MLHelper.storageNameForNoOfSelections, ObjectSerializer.serialize( new ArrayList<String>() ) ) ) ;
            tmp2 = (ArrayList<String>) ObjectSerializer.deserialize( sharedPreferences.getString(
                    MLHelper.storageNameForNoOfRewards, ObjectSerializer.serialize( new ArrayList<String>() ) ) ) ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("check 1:::::::::::::::::::","done");

        if( tmp1 == null || tmp2 == null ||
                tmp1.size() != no_of_categories || tmp2.size() != no_of_categories ){
            for( int i = 0; i < no_of_categories; i++ ){
                no_of_selections.add(0);
                no_of_rewards.add(0);
            }
        } else {
            for( int i = 0; i < no_of_categories; i++ ) {
                no_of_selections.add(Integer.parseInt(tmp1.get(i)));
                no_of_rewards.add(Integer.parseInt(tmp2.get(i)));
            }
        }
        Log.i("check 2 :::::::::::::::::::::::::","done");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
