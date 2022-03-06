package com.maltsev.greenhouse.ui.plant.layout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.maltsev.greenhouse.R;

public class PlantActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewDescription;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private boolean appBarExpanded = true;
    private TabLayout tabLayout;
    private ShapeableImageView preview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar_plant);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preview = findViewById(R.id.preview_plant);
        Glide.with(this).load(getIntent().getExtras().getString("ImageUrl")).into(preview);


        appBarLayout = findViewById(R.id.appbar_plant);
        tabLayout = findViewById(R.id.tab_layout_plant);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar_plant);
        collapsingToolbar.setTitle(getIntent().getExtras().getString("Name"));

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > 200) {
                appBarExpanded = false;
                invalidateOptionsMenu();
            } else {
                appBarExpanded = true;
                invalidateOptionsMenu();
            }
        });
        //textViewName = findViewById(R.id.plant_name);
        //textViewName.setText(getIntent().getExtras().getString("Name"));

        //textViewDescription = findViewById(R.id.plant_description);
        //textViewDescription.setText(getIntent().getExtras().getString("Description"));
    }
}
