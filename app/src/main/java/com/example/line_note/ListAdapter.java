package com.example.line_note;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Note> mData = null;
    private Context mContext;

    ListAdapter(Context context, ArrayList<Note> list) {
        mData = list;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView thumbnail;

        ViewHolder(View itemView) {
            super(itemView) ;

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,DetailActivity.class);
                    intent.putExtra("position", getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mData.get(position) ;
        holder.title.setText(note.getTitle()) ;
        holder.content.setText(note.getShortContent()) ;
        holder.thumbnail.setBackgroundResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
