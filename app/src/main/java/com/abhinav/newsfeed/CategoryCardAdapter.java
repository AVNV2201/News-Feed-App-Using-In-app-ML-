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

import java.util.ArrayList;

public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardAdapter.CategoryCardViewHolder> {

    ArrayList<String> textList;
    ArrayList<Integer> imageList;
    Context context;
    int countryFlag ;

    public CategoryCardAdapter(Context context, ArrayList<String> textList, ArrayList<Integer> imageList, int countryFlag ){
        this.context = context;
        this.countryFlag = countryFlag;
        this.textList = textList;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_card_layout, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, final int position) {
        holder.cardText.setText(textList.get(position));
        holder.cardImage.setImageResource(imageList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( countryFlag == -1 ){

                    Intent intent = new Intent(context, CategorySelectActivity.class);
                    intent.putExtra("country",position);
                    context.startActivity(intent);

                } else{

                    Intent intent = new Intent(context, CategoryNewsActivity.class );
                    intent.putExtra("country",countryFlag);
                    intent.putExtra("category",position);
                    context.startActivity(intent);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class CategoryCardViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImage ;
        TextView cardText;

        public CategoryCardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.categoryCardImage);
            cardText = itemView.findViewById(R.id.categoryCardText);
        }
    }
}
