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
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class NewsDownloadHelper {

    private Context context;

    NewsDownloadHelper(Context context){
        this.context = context;
    }

    void setNewsList(final RecyclerView recyclerView, final ArrayList<News> newsList, String country, final String category){

        NewsApiClient newsApiClient = new NewsApiClient("ed99e8bfdeb44fc381142c485ef29a88");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                    .country(country)
                    .category(category)
                    .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse articleResponse) {
//                        Log.i("Success:::::::::", articleResponse.getArticles().get(0).getTitle());
                        try {
                            for( int i = 0; i < articleResponse.getArticles().size() ; i++ ){
                                Article article = articleResponse.getArticles().get(i);
                                newsList.add(new News(
                                        article.getTitle(),
                                        article.getDescription(),
                                        category,
                                        article.getUrl(),
                                        article.getUrlToImage()
                                ));
                            }

                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.i("Error:::::::::::::::", "" + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Again:::::::::::::::::::::","error");
                    }
                }
        );
    }
}
