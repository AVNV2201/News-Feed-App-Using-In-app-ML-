package com.abhinav.newsfeed;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MLBasedNewsDownloadHelper {

    private Context context;

    MLBasedNewsDownloadHelper(Context context){
        this.context = context;
    }

    void setNewsList(final RecyclerView recyclerView,
                     final ArrayList<News> newsList,
                     String country,
                     final String category,
                     final int maxNoOfNews){

        String url = ResourceHelper.NEWS_URL + "country=" + country ;
        url += "&category=" + category;
        url += "&apiKey=" + ResourceHelper.NEWS_API_KEY;

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articles = response.getJSONArray("articles");
                            String title, description, urlToNews, urlToImage;
                            for( int i = 0; i < articles.length() && i < maxNoOfNews ; i++ ){
                                JSONObject article = articles.getJSONObject(i);
                                title = article.getString("title");
                                description = article.getString("description");
                                urlToImage = article.getString("urlToImage");
                                urlToNews = article.getString("url");
                                newsList.add(new News(title,description,category, urlToNews,urlToImage));
                            }

                            Collections.shuffle(newsList);
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.i("Error:::::::::::::::", "" + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("Error:::::::::::::::", "" + error.getMessage());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue( context );
        queue.add(request);
    }
}
