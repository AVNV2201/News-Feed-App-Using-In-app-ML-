package com.abhinav.newsfeed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class ReadLaterNewsCardAdapter extends RecyclerView.Adapter<ReadLaterNewsCardAdapter.NewsCardViewHolder> {

    Context context;
    ArrayList<News> newsList;
    SQLiteDatabase database;

    public ReadLaterNewsCardAdapter(Context context ){
        this.context = context;
        this.newsList = StorageHelper.getNewsListFromDatabase(database);
        this.database  = MainActivity.database;
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card_layout,parent,false);
        return new NewsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, final int position) {

        final News news = newsList.get(position);
        holder.newsCardTExt.setText(news.getTitle());
        Glide.with(context).load(news.getUrlToImage()).into(holder.newsCardImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayNewsActivity.class);
                intent.putExtra("TITLE", news.getTitle());
                intent.putExtra("DESC", news.getDescription());
                intent.putExtra("URL", news.getUrlToNews());
                intent.putExtra("IMGURL", news.getUrlToImage());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder( context )
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete this news !!!")
                        .setMessage("Are you sure you want to delete this news ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                long id = StorageHelper.getID(news.getUrlToNews());
//                                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();

                                if( id != -1 ) {
                                    StorageHelper.deleteNewsFromDatabase(database,id);
                                    newsList.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "DELETED SUCCESFULLY :)", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "UNABLE TO DELETE :(", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton( "NO", null )
                        .show() ;

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
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
