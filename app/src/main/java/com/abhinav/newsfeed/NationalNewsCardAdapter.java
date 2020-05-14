package com.abhinav.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class NationalNewsCardAdapter extends RecyclerView.Adapter<NationalNewsCardAdapter.NewsCardViewHolder> {

    Context context;
    ArrayList<News> newsCard;

    public NationalNewsCardAdapter(Context context, ArrayList<News> newsCard){
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
    public void onBindViewHolder(@NonNull final NewsCardViewHolder holder, final int position) {
        final News news = newsCard.get(position);
        holder.newsCardTExt.setText(news.getTitle());
        Glide.with(context).load(news.getUrlToImage()).into(holder.newsCardImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.clicked.set(position, true);

                Intent intent = new Intent(context, DisplayNewsActivity.class);
                intent.putExtra("TITLE", news.getTitle());
                intent.putExtra("DESC", news.getDescription());
                intent.putExtra("URL", news.getUrlToNews());
                intent.putExtra("IMGURL", news.getUrlToImage());
                context.startActivity(intent);
            }
        });
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
