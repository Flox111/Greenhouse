package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.maltsev.greenhouse.R;

public class GreenhouseActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewDescription;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
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
        setContentView(R.layout.greenhouse_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar_greenhouse);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        preview = findViewById(R.id.preview_greenhouse);
        Glide.with(this).load(getIntent().getExtras().getString("ImageUrl")).into(preview);

        appBarLayout = findViewById(R.id.appbar_greenhouse);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar_greenhouse);
        collapsingToolbar.setTitle(getIntent().getExtras().getString("Name"));

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > 200) {
                invalidateOptionsMenu();
            } else {
                invalidateOptionsMenu();
            }
        });
    }
}
