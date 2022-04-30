package com.maltsev.greenhouse.ui.plant.layout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.greenhouse.adapter.GreenhouseTabAdapter;
import com.maltsev.greenhouse.ui.plant.adapter.PlantTabsAdapter;

public class PlantActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ShapeableImageView preview;
    private ViewPager2 viewPager;
    private PlantTabsAdapter adapter;
    private LinearLayout layoutAddNote;

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
        toolbar.setTitle(getIntent().getExtras().getString("name"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preview = findViewById(R.id.preview_plant);
        Glide.with(this).load(getIntent().getExtras().getString("imageUrl")).into(preview);

        appBarLayout = findViewById(R.id.appbar_plant);
        viewPager = findViewById(R.id.view_page_plant);
        tabLayout = findViewById(R.id.tab_layout_plant);

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > 200) {
                invalidateOptionsMenu();
            } else {
                invalidateOptionsMenu();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        adapter = new PlantTabsAdapter(fm, getLifecycle());
        viewPager.setAdapter(adapter);

        layoutAddNote = findViewById(R.id.layout_add_note);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1){
                    layoutAddNote.animate().translationY(0);
                }
                else{
                    layoutAddNote.animate().translationY(layoutAddNote.getHeight());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
