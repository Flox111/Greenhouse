package com.maltsev.greenhouse.ui.plant.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.plant.adapter.RecyclerViewAdapterPlants;
import com.maltsev.greenhouse.ui.plant.database.PlantDatabaseQueryClass;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class MyPlantsFragment extends Fragment {

    private PlantDatabaseQueryClass databaseQueryClass;

    private FloatingActionButton floatingActionButton;
    private RecyclerViewAdapterPlants recyclerViewAdapterPlants;
    private ArrayList<Plant> plants = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        databaseQueryClass = new PlantDatabaseQueryClass(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_plants, container, false);

        plants.addAll(databaseQueryClass.getAllPlant());

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_plants);
        recyclerViewAdapterPlants = new RecyclerViewAdapterPlants(getContext(),plants);
        recyclerView.setAdapter(recyclerViewAdapterPlants);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        Intent intent = new Intent(getContext(), CreatePlantActivity.class);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Plant plant = new Plant();
                        plant.setId(-1);
                        plant.setName(data.getExtras().getString("name"));
                        plant.setDescription(data.getExtras().getString("description"));
                        plant.setImageUrl(data.getExtras().getString("imageUrl"));

                        PlantDatabaseQueryClass databaseQueryClass = new PlantDatabaseQueryClass(getContext());

                        long id = databaseQueryClass.insertPlant(plant);

                        if(id>0){
                            plant.setId(id);
                            recyclerViewAdapterPlants.update(plant);
                            recyclerViewAdapterPlants.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getContext(), "Operation failed", Toast.LENGTH_LONG);
                        }
                    }
                });

        floatingActionButton = root.findViewById(R.id.button_add_plant);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    someActivityResultLauncher.launch(intent);
            }
        });

        return root;
    }

}