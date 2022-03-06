package com.maltsev.greenhouse.ui.plant.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.plant.adapter.RecyclerViewAdapterPlants;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class MyPlantsFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private RecyclerViewAdapterPlants recyclerViewAdapterPlants;
    private ArrayList<Plant> plants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_plants, container, false);

        plants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Plant plant = new Plant();
            plant.setName("Name");
            plant.setDescription("Description");
            plants.add(plant);
            plants.get(i).setImageUrl("https://images.unsplash.com/photo-1623050095430-480240c0c463?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80");
        }
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_plants);
        recyclerViewAdapterPlants = new RecyclerViewAdapterPlants(getContext(),plants);
        recyclerView.setAdapter(recyclerViewAdapterPlants);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        floatingActionButton = root.findViewById(R.id.button_add_plant);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePlantActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        return root;
    }
}