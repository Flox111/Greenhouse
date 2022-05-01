package com.maltsev.greenhouse.ui.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.common.DateFormatter;
import com.maltsev.greenhouse.ui.greenhouse.model.Note;

import java.util.ArrayList;

public class RecyclerViewAdapterNotes extends RecyclerView.Adapter<RecyclerViewAdapterNotes.MyViewHolder>{

    private Context mContext;
    private ArrayList<Note> mData;

    public RecyclerViewAdapterNotes(Context mContext, ArrayList<Note> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterNotes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_note, parent, false);
        return new RecyclerViewAdapterNotes.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterNotes.MyViewHolder holder, int position) {
        holder.text.setText(mData.get(position).getText());
        String date = DateFormatter.getDateFromDatabase(mData.get(position).getDate());
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void update(Note note){
        mData.add(note);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.note_date);
            text = itemView.findViewById(R.id.note_text);
        }

    }
}
