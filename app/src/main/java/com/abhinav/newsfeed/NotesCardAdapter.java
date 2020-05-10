package com.abhinav.newsfeed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesCardAdapter extends RecyclerView.Adapter<NotesCardAdapter.NotesCardViewHolder> {

    Context context;
    ArrayList<String> notesTitle;
    ArrayList<String> notesContent;

    public NotesCardAdapter( Context context, ArrayList<String> notesTitle, ArrayList<String> notesContent ){
        this.context = context;
        this.notesContent = notesContent;
        this.notesTitle = notesTitle;
    }

    @NonNull
    @Override
    public NotesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notes_card, parent, false);
        return new NotesCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesCardViewHolder holder, final int position) {
        holder.notesCardTitle.setText(notesTitle.get(position));
        holder.notesCardContent.setText(notesContent.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesEditorActivity.class);
                intent.putExtra("noteId",position);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder( context )
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete this Note !!!")
                        .setMessage("Are you sure you want to delete this note ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesTitle.remove(position) ;
                                notesContent.remove(position);

                                notifyDataSetChanged();

                                try {
                                    String str = ObjectSerializer.serialize(notesTitle) ;
                                    MainActivity.sharedPreferences.edit().putString( "notesTitle", str ).apply();
                                    str = ObjectSerializer.serialize( notesContent) ;
                                    MainActivity.sharedPreferences.edit().putString("notesContent",str).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
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
        return notesTitle.size();
    }

    public class NotesCardViewHolder extends RecyclerView.ViewHolder {

        TextView notesCardTitle;
        TextView notesCardContent;

        public NotesCardViewHolder(@NonNull View itemView) {
            super(itemView);
            notesCardTitle = itemView.findViewById(R.id.notesListTitle);
            notesCardContent = itemView.findViewById(R.id.notesListContent);
        }
    }
}
