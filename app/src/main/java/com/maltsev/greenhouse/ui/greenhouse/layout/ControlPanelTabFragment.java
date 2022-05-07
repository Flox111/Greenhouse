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
import com.maltsev.greenhouse.ui.greenhouse.adapter.RecyclerViewAdapterManagementSystem;
import com.maltsev.greenhouse.ui.greenhouse.adapter.RecyclerViewAdapterSensor;
import com.maltsev.greenhouse.ui.greenhouse.model.ManagementSystem;
import com.maltsev.greenhouse.ui.greenhouse.model.Sensor;
import com.maltsev.greenhouse.ui.greenhouse.model.TypeManagementSystem;

import java.util.ArrayList;

public class ControlPanelTabFragment extends Fragment {

    private final String VENTILATION_SYSTEM_ICON = "ic_fan_svgrepo_com";
    private final String HEATING_SYSTEM_ICON = "ic_heat_svgrepo_com";
    private final String IRRIGATION_SYSTEM_ICON = "ic_watering_can";
    private final String LIGHTING_SYSTEM_ICON = "ic_light";
    private final String ELECTROMAGNET_SYSTEM_ICON = "ic_magnetic_field";

    private RecyclerViewAdapterSensor recyclerViewAdapterSensor;
    private RecyclerViewAdapterManagementSystem recyclerViewAdapterManagementSystem;
    private ArrayList<Sensor> sensors;
    private ArrayList<ManagementSystem> managementSystems;
    private String nameOfGreenhouse;

    public ControlPanelTabFragment(String nameOfGreenhouse){
        this.nameOfGreenhouse = nameOfGreenhouse;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_control_panel_tab,container,false);

        sensors = new ArrayList<>();
        Sensor sensor1 = new Sensor("Температура","+124.6","°C");
        Sensor sensor2 = new Sensor("Освещённость","25.8", "lux");
        Sensor sensor3 = new Sensor("Влажность воздуха","25.2","%");
        Sensor sensor4 = new Sensor("Влажность почвы","25.2","%");
        Sensor sensor5 = new Sensor("Магнитное поле","125.2","T");
        sensors.add(sensor1);
        sensors.add(sensor2);
        sensors.add(sensor3);
        sensors.add(sensor4);
        sensors.add(sensor5);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_sensors);
        recyclerViewAdapterSensor = new RecyclerViewAdapterSensor(getContext(), sensors);
        recyclerViewAdapterSensor.setNameOfGreenhouse(nameOfGreenhouse);
        recyclerView.setAdapter(recyclerViewAdapterSensor);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        managementSystems = new ArrayList<>();
        ManagementSystem system1 = new ManagementSystem("Система проветривания", TypeManagementSystem.VENTILATION_SYSTEM, VENTILATION_SYSTEM_ICON);
        ManagementSystem system2 = new ManagementSystem("Система обогрева",TypeManagementSystem.HEATING_SYSTEM, HEATING_SYSTEM_ICON);
        ManagementSystem system3 = new ManagementSystem("Система полива",TypeManagementSystem.IRRIGATION_SYSTEM, IRRIGATION_SYSTEM_ICON);
        ManagementSystem system4 = new ManagementSystem("Система освещения",TypeManagementSystem.LIGHTING_SYSTEM, LIGHTING_SYSTEM_ICON);
        ManagementSystem system5 = new ManagementSystem("Система электромагнита",TypeManagementSystem.ELECTROMAGNET_SYSTEM, ELECTROMAGNET_SYSTEM_ICON
        );
        managementSystems.add(system1);
        managementSystems.add(system2);
        managementSystems.add(system3);
        managementSystems.add(system4);
        managementSystems.add(system5);

        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerview_management_system);
        recyclerViewAdapterManagementSystem = new RecyclerViewAdapterManagementSystem(getContext(), managementSystems);
        recyclerViewAdapterManagementSystem.setNameOfGreenhouse(nameOfGreenhouse);
        recyclerView2.setAdapter(recyclerViewAdapterManagementSystem);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }
}
