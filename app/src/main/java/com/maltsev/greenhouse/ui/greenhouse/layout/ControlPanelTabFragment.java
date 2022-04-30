package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.adapter.RecyclerViewAdapterSensor;
import com.maltsev.greenhouse.ui.greenhouse.model.Sensor;

import java.util.ArrayList;

public class ControlPanelTabFragment extends Fragment {

    private RecyclerViewAdapterSensor recyclerViewAdapterSensor;
    private ArrayList<Sensor> sensors;
    private String nameOfGreenhouse;

    public ControlPanelTabFragment(String nameOfGreenhouse){
        this.nameOfGreenhouse = nameOfGreenhouse;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.control_panel_tab_fragment,container,false);

        sensors = new ArrayList<>();
        Sensor sensor1 = new Sensor("Температура","+24.6°C");
        Sensor sensor2 = new Sensor("Освещённость","25.8 lux");
        Sensor sensor3 = new Sensor("Влажность воздуха","25.2%");
        Sensor sensor4 = new Sensor("Влажность почвы","25.2%");
        sensors.add(sensor1);
        sensors.add(sensor2);
        sensors.add(sensor3);
        sensors.add(sensor4);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_sensors);
        recyclerViewAdapterSensor = new RecyclerViewAdapterSensor(getContext(), sensors);
        recyclerViewAdapterSensor.setNameOfGreenhouse(nameOfGreenhouse);
        recyclerView.setAdapter(recyclerViewAdapterSensor);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }
}
