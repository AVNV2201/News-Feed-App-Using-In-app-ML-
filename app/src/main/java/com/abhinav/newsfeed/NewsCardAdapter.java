package com.abhinav.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.NewsCardViewHolder> {

    Context context;
    ArrayList<News> newsCard;

    public NewsCardAdapter(Context context, ArrayList<News> newsCard){
        this.context = context;
        this.newsCard = newsCard;
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card_layout,parent,false);
        return new NewsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, int position) {
        News news = newsCard.get(position);
        holder.newsCardTExt.setText(news.getTitle());
        Glide.with(holder.newsCardImage.getContext()).load(news.getUrlToImage()).into(holder.newsCardImage);
    }

    @Override
    public int getItemCount() {
        return newsCard.size();
    }

    public class NewsCardViewHolder extends RecyclerView.ViewHolder {

        TextView newsCardTExt;
        ImageView newsCardImage;

        public NewsCardViewHolder(@NonNull View itemView) {
            super(itemView);
            newsCardImage = itemView.findViewById(R.id.newsCardImageView);
            newsCardTExt = itemView.findViewById(R.id.newsCardTExtView);
        }
    }
}
