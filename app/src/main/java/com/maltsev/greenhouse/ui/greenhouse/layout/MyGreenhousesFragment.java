package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.adapter.RecyclerViewAdapterGreenhouses;
import com.maltsev.greenhouse.ui.greenhouse.model.Greenhouse;
import com.maltsev.greenhouse.ui.plant.adapter.RecyclerViewAdapterPlants;
import com.maltsev.greenhouse.ui.plant.layout.CreatePlantActivity;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.util.ArrayList;

public class MyGreenhousesFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private RecyclerViewAdapterGreenhouses recyclerViewAdapterGreenhouses;
    private ArrayList<Greenhouse> greenhouses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_greenhouses, container, false);

        greenhouses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Greenhouse greenhouse = new Greenhouse();
            greenhouse.setName("Greenhouse" + i);
            greenhouse.setDescription("Description");
            greenhouses.add(greenhouse);
            greenhouses.get(i).setImageUrl("https://images.unsplash.com/photo-1589885616367-e131e99a6147?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1374&q=80");
        }

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_greenhouses);
        recyclerViewAdapterGreenhouses = new RecyclerViewAdapterGreenhouses(getContext(), greenhouses);
        recyclerView.setAdapter(recyclerViewAdapterGreenhouses);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        floatingActionButton = root.findViewById(R.id.button_add_greenhouse);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateGreenhouseActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        return root;
    }
}