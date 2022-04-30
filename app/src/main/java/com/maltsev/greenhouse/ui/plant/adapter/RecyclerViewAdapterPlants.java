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
import com.maltsev.greenhouse.ui.greenhouse.model.Note;
import com.maltsev.greenhouse.ui.plant.database.PlantDatabaseQueryClass;
import com.maltsev.greenhouse.ui.plant.layout.CreatePlantActivity;
import com.maltsev.greenhouse.ui.plant.layout.PlantActivity;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class RecyclerViewAdapterPlants extends RecyclerView.Adapter<RecyclerViewAdapterPlants.MyViewHolder> {
    private Context mContext;
    private ArrayList<Plant> mData;
    private Dialog mDialog;

    public RecyclerViewAdapterPlants(Context context, ArrayList<Plant> plants) {
        this.mContext = context;
        this.mData = plants;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_plant, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewName.setText(mData.get(position).getName());
        holder.textViewDescription.setText(mData.get(position).getDescription());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, PlantActivity.class);
            intent.putExtra("name", mData.get(holder.getAdapterPosition()).getName());
            intent.putExtra("plantId", mData.get(holder.getAdapterPosition()).getId());
            intent.putExtra("description", mData.get(holder.getAdapterPosition()).getDescription());
            intent.putExtra("imageUrl", mData.get(holder.getAdapterPosition()).getImageUrl());
            mContext.startActivity(intent);
        });

        if (mData.get(position).getImageUrl() != null) {
            Glide.with(mContext).load(mData.get(position).getImageUrl()).into(holder.preview);
        }

        mDialog = new Dialog(mContext);
        holder.imageButton.setOnClickListener(view -> {
            Log.d("id", Long.toString(mData.get(position).getId()));
            showPopupMenu(view, position);
        });
    }

    public void update(Plant plant) {
        mData.add(plant);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu_plant);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    popupMenu.dismiss();
                    showPopupEdit(position);
                    return true;
                case R.id.action_popup_delete:
                    popupMenu.dismiss();
                    showPopupDelete(position);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void showPopupDelete(int position) {
        mDialog.setContentView(R.layout.popup_delete);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnActionDelete = mDialog.findViewById(R.id.delete_action);
        Button btnActionCancel = mDialog.findViewById(R.id.popup_delete_cancel_action);
        btnActionCancel.setOnClickListener(view -> mDialog.dismiss());
        btnActionDelete.setOnClickListener(view -> {
            PlantDatabaseQueryClass databaseQueryClass = new PlantDatabaseQueryClass(mDialog.getContext());

            long count = databaseQueryClass.deletePlantById(mData.get(position).getId());

            if (count > 0) {
                mData.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Plant deleted successfully", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(mContext, "Plant not deleted. Something wrong!", Toast.LENGTH_LONG).show();

            mDialog.dismiss();
        });
        mDialog.show();
    }

    private void showPopupEdit(int position) {
        mDialog.setContentView(R.layout.popup_edit);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnActionRename = mDialog.findViewById(R.id.edit_action);
        Button btnActionCancel = mDialog.findViewById(R.id.popup_edit_cancel_action);
        btnActionCancel.setOnClickListener(view -> mDialog.dismiss());
        btnActionRename.setOnClickListener(view -> {
            //изменение цветка
        });
        mDialog.show();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imageButton;
        private ShapeableImageView preview;
        private TextView textViewName;
        private TextView textViewDescription;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            preview = itemView.findViewById(R.id.preview_plant);
            imageButton = itemView.findViewById(R.id.imageButton_plant);
            cardView = itemView.findViewById(R.id.cardview_plant);
            textViewName = itemView.findViewById(R.id.card_view_name_plant);
            textViewDescription = itemView.findViewById(R.id.card_view_description_plant);
        }

    }
}
