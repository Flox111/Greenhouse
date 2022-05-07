package com.maltsev.greenhouse.ui.greenhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.layout.GreenhouseActivity;
import com.maltsev.greenhouse.ui.greenhouse.layout.SensorActivity;
import com.maltsev.greenhouse.ui.greenhouse.model.Note;
import com.maltsev.greenhouse.ui.greenhouse.model.Sensor;

import java.util.ArrayList;

public class RecyclerViewAdapterSensor extends RecyclerView.Adapter<RecyclerViewAdapterSensor.MyViewHolder>{

    private Context mContext;
    private ArrayList<Sensor> mData;
    private String nameOfGreenhouse;

    public RecyclerViewAdapterSensor(Context mContext, ArrayList<Sensor> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_sensor, parent, false);
        return new RecyclerViewAdapterSensor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sensorTitle.setText(mData.get(position).getSensorTitle());
        holder.sensorValue.setText(mData.get(position).getSensorValue());
        holder.sensorNumberSystem.setText(mData.get(position).getSensorNumberSystem());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SensorActivity.class);
            intent.putExtra("title", mData.get(holder.getAdapterPosition()).getSensorTitle());
            intent.putExtra("name", nameOfGreenhouse);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView sensorTitle;
        private TextView sensorValue;
        private TextView sensorNumberSystem;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorTitle = itemView.findViewById(R.id.sensor_card_title);
            sensorValue = itemView.findViewById(R.id.sensor_card_value);
            cardView = itemView.findViewById(R.id.card_view_sensor);
            sensorNumberSystem = itemView.findViewById(R.id.number_system);
        }

    }

    public String getNameOfGreenhouse() {
        return nameOfGreenhouse;
    }

    public void setNameOfGreenhouse(String nameOfGreenhouse) {
        this.nameOfGreenhouse = nameOfGreenhouse;
    }
}
