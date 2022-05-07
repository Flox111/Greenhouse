package com.maltsev.greenhouse.ui.plant.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.plant.VerticalLineView;
import com.maltsev.greenhouse.ui.plant.database.PlantDatabaseQueryClass;
import com.maltsev.greenhouse.ui.plant.layout.PlantActivity;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class RecyclerViewAdapterReminders extends RecyclerView.Adapter<RecyclerViewAdapterReminders.MyViewHolder> {
    private Context mContext;
    private ArrayList<Integer> mData;
    private Dialog mDialog;

    public RecyclerViewAdapterReminders(Context context, ArrayList<Integer> plants) {
        this.mContext = context;
        this.mData = plants;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_reminder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == 1){
            holder.preview.setActive(true);
            holder.preview.setFirstActive(true);
        }
        if (position == 2){
            holder.preview.setActive(true);
        }
        if (position == 3){
            holder.preview.setActive(true);
            holder.preview.setLastActive(true);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imageButton;
        private VerticalLineView preview;
        private TextView textViewName;
        private TextView textViewDescription;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            preview = itemView.findViewById(R.id.preview_reminder);
            imageButton = itemView.findViewById(R.id.imageButton_reminder);
            cardView = itemView.findViewById(R.id.cardview_reminder);
            textViewName = itemView.findViewById(R.id.card_view_name_reminder);
            textViewDescription = itemView.findViewById(R.id.card_view_description_reminder);
        }

    }
}
