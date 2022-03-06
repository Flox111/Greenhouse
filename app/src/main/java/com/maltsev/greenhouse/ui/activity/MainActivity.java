package com.maltsev.greenhouse.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.layout.MyGreenhousesFragment;
import com.maltsev.greenhouse.ui.plant.layout.MyPlantsFragment;
import com.maltsev.greenhouse.ui.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SettingsFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.settingsFragment);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch(item.getItemId()){
                    case R.id.settingsFragment:
                        selectedFragment = new SettingsFragment();
                        break;
                    case R.id.myPlantsFragment:
                        selectedFragment = new MyPlantsFragment();
                        break;
                    case R.id.MyGreenhousesFragment:
                        selectedFragment = new MyGreenhousesFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, selectedFragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}