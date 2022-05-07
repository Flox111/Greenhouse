package com.maltsev.greenhouse.ui.greenhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.layout.SensorActivity;
import com.maltsev.greenhouse.ui.greenhouse.model.ManagementSystem;
import com.maltsev.greenhouse.ui.greenhouse.model.Sensor;

import java.util.ArrayList;

public class RecyclerViewAdapterManagementSystem extends RecyclerView.Adapter<RecyclerViewAdapterManagementSystem.MyViewHolder> {

    private Context mContext;
    private ArrayList<ManagementSystem> mData;
    private String nameOfGreenhouse;

    public RecyclerViewAdapterManagementSystem(Context mContext, ArrayList<ManagementSystem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_management_system, parent, false);
        return new RecyclerViewAdapterManagementSystem.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.managementSystemTitle.setText(mData.get(position).getName());
        int resourceImage = mContext.getResources().getIdentifier(mData.get(position).getIcon(), "drawable", mContext.getPackageName());
        holder.imageViewIcon.setBackground(ContextCompat.getDrawable(mContext, resourceImage));
//        holder.cardView.setOnClickListener(view -> {
//            Intent intent = new Intent(mContext, SensorActivity.class);
//            intent.putExtra("Title", mData.get(holder.getAdapterPosition()).getSensorTitle());
//            intent.putExtra("name", nameOfGreenhouse);
//            mContext.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView managementSystemTitle;
        private ImageView imageViewIcon;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            managementSystemTitle = itemView.findViewById(R.id.management_system_card_title);
            cardView = itemView.findViewById(R.id.card_view_management_system);
            imageViewIcon = itemView.findViewById(R.id.management_system_icon);
        }

    }

    public String getNameOfGreenhouse() {
        return nameOfGreenhouse;
    }

    public void setNameOfGreenhouse(String nameOfGreenhouse) {
        this.nameOfGreenhouse = nameOfGreenhouse;
    }
}
