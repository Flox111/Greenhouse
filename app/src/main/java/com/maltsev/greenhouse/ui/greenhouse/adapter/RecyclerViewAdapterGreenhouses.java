package com.maltsev.greenhouse.ui.greenhouse.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.layout.GreenhouseActivity;
import com.maltsev.greenhouse.ui.greenhouse.model.Greenhouse;
import com.maltsev.greenhouse.ui.plant.layout.PlantActivity;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class RecyclerViewAdapterGreenhouses extends RecyclerView.Adapter<RecyclerViewAdapterGreenhouses.MyViewHolder> {
    private Context mContext;
    private ArrayList<Greenhouse> mData;

    public RecyclerViewAdapterGreenhouses(Context context, ArrayList<Greenhouse> greenhouses) {
        this.mContext = context;
        this.mData = greenhouses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_greenhouse, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (mData.get(position).getImageUrl() != null) {
            Glide.with(mContext).load(mData.get(position).getImageUrl()).into(holder.preview);
        }

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, GreenhouseActivity.class);
            intent.putExtra("Name", mData.get(holder.getAdapterPosition()).getName());
            intent.putExtra("ImageUrl", mData.get(holder.getAdapterPosition()).getImageUrl());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imageButton;
        private ShapeableImageView preview;
        private Dialog mDialog;
        private TextView textViewName;
        private TextView textViewDescription;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.preview_greenhouse);
            imageButton = itemView.findViewById(R.id.imageButton_greenhouse);
            cardView = itemView.findViewById(R.id.cardview_greenhouse);
            textViewName = itemView.findViewById(R.id.card_view_name_greenhouse);
            textViewDescription = itemView.findViewById(R.id.card_view_description_greenhouse);

            mDialog = new Dialog(itemView.getContext());
            imageButton.setOnClickListener(view -> {
                showPopupMenu(view);
            });
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu_plant);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_popup_edit:
                        popupMenu.dismiss();
                        showPopupEdit();
                        return true;
                    case R.id.action_popup_delete:
                        popupMenu.dismiss();
                        showPopupDelete();
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        }

        private void showPopupDelete(){
            mDialog.setContentView(R.layout.popup_delete);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btnActionDelete = mDialog.findViewById(R.id.delete_action);
            Button btnActionCancel = mDialog.findViewById(R.id.popup_delete_cancel_action);
            btnActionCancel.setOnClickListener(view -> mDialog.dismiss());
            btnActionDelete.setOnClickListener(view ->{
                //удаление цветка
            });
            mDialog.show();
        }

        private void showPopupEdit(){
            mDialog.setContentView(R.layout.popup_edit);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btnActionRename = mDialog.findViewById(R.id.edit_action);
            Button btnActionCancel = mDialog.findViewById(R.id.popup_edit_cancel_action);
            btnActionCancel.setOnClickListener(view -> mDialog.dismiss());
            btnActionRename.setOnClickListener(view ->{
                //изменение цветка
            });
            mDialog.show();
        }

    }
}
