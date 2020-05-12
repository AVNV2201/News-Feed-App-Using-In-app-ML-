package com.abhinav.newsfeed;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

public class StorageHelper {

    static String DATABASE_NAME = "READ_LATER_DB";
    static String TABLE_NAME = "news_table" ;

    static SQLiteDatabase openDatabase( Context context ){

        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR, url_to_news VARCHAR, url_to_image VARCHAR )";
        database.execSQL(sql);
        return database;
    }

    static void insertNewsIntoDatabase( SQLiteDatabase database, News news){

        String sql = "INSERT INTO " + TABLE_NAME + " ( title, description, url_to_news, url_to_image ) VALUES ( ?, ?, ?, ? )";
        SQLiteStatement statement = MainActivity.database.compileStatement(sql);
        statement.bindString(1, news.getTitle());
        statement.bindString(2, news.getDescription());
        statement.bindString(3, news.getUrlToNews());
        statement.bindString(4, news.getUrlToImage());

        statement.execute();
        Log.i("insert ::::::::::::::::::",statement.toString());
    }

    static void deleteNewsFromDatabase( SQLiteDatabase database, long id ){

//        String clause = "id + ";

        MainActivity.database.delete(
                TABLE_NAME,
                "id = " + id,
                null
        ) ;

    }

    static boolean isStoredInDatabase(SQLiteDatabase database, String urlToNews ){

        String clause = "url_to_news = ?";
        String[] condition = { urlToNews };
        Cursor cursor = MainActivity.database.query(
                TABLE_NAME,
                null,
                clause,
                condition,
                null,
                null,
                null
        );

        if( cursor.getCount() > 0 ){
            cursor.close();
            Log.i("check :::::::::::::::::::::::", "sved");
            return true;
        }
        Log.i("check ::::::::::::::::::::", "not saved");

        cursor.close();
        return false;
    }

    static long getID( String urlToNews ){

        String clause = "url_to_news = ?";
        String[] condition = { urlToNews };
        Cursor cursor = MainActivity.database.query(
                TABLE_NAME,
                null,
                clause,
                condition,
                null,
                null,
                null
        );

        if( cursor.moveToFirst() ){
            long id = -1 ;
            id = cursor.getInt( cursor.getColumnIndex("id") );
            cursor.close();
            Log.i("check :::::::::::::::::::::::", "sved");
            return id;
        }
        Log.i("check ::::::::::::::::::::", "not saved");

        cursor.close();
        return -1;
    }

    static ArrayList<News> getNewsListFromDatabase( SQLiteDatabase database){

        String querry = "SELECT * FROM " + TABLE_NAME ;
        Log.i("yo:::::::::::::::::::::::::::","0");
        Cursor cursor = MainActivity.database.rawQuery(querry, null);
        Log.i("yo::::::::::::::::::::::::::::::", "1");

        ArrayList<News> newsList = new ArrayList<>();

        if( cursor.moveToFirst() ) {
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int urlToNewsIndex = cursor.getColumnIndex("url_to_news");
            int urlToImageIndex = cursor.getColumnIndex("url_to_image");

            while (!cursor.isAfterLast()) {
                News news = new News(cursor.getString(titleIndex),
                        cursor.getString(descriptionIndex),
                        "",
                        cursor.getString(urlToNewsIndex),
                        cursor.getString(urlToImageIndex));
                newsList.add(news);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return newsList;
    }

}
