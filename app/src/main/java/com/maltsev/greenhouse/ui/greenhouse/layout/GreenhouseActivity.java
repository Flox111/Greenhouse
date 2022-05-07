package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.common.DateFormatter;
import com.maltsev.greenhouse.ui.greenhouse.adapter.GreenhouseTabAdapter;

public class GreenhouseActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private ShapeableImageView preview;
    private ViewPager2 viewPager;
    private GreenhouseTabAdapter adapter;
    private TabLayout tabLayout;
    private LinearLayout layoutAddNote;
    private TextView nameGreenhouse;
    private TextView actualDate;

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
        Glide.with(this).load(getIntent().getExtras().getString("imageUrl")).into(preview);

        layoutAddNote = findViewById(R.id.layout_add_note);

        appBarLayout = findViewById(R.id.appbar_greenhouse);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar_greenhouse);
        collapsingToolbar.setTitle(getIntent().getExtras().getString("name"));


        actualDate = findViewById(R.id.actual_date);
        actualDate.setText(getDateFromSharedPreferences());

        nameGreenhouse = findViewById(R.id.name_greenhouse);
        nameGreenhouse.setText(getIntent().getExtras().getString("name"));

        int maxOffset = 300;
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) > 0) {
                invalidateOptionsMenu();
            }
            float alpha = (float) Math.abs(verticalOffset) / maxOffset;
            int color = getColorWithAlpha(alpha, Color.parseColor("#e3f3f0"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(color);
            }
            toolbar.setBackgroundColor(color);
            toolbar.setTitleTextColor(getColorWithAlpha(alpha, Color.parseColor("#2e6657")));
        });

        viewPager = findViewById(R.id.view_page_greenhouse);
        tabLayout = findViewById(R.id.tab_layout_greenhouse);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new GreenhouseTabAdapter(fm, getLifecycle());
        adapter.setNameOfGreenhouse(getIntent().getExtras().getString("name"));
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    layoutAddNote.animate().translationY(0);
                } else {
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

    private int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    private String getDateFromSharedPreferences() {
        SharedPreferences settings = getSharedPreferences("actualDateUpdate", MODE_PRIVATE);
        return settings.getString("actualDateUpdate", DateFormatter.getDateNow());
    }

    private void updateActualDate() {
        SharedPreferences settings = getSharedPreferences("actualDateUpdate", MODE_PRIVATE);
        settings.edit().putString("actualDateUpdate", DateFormatter.getDateNow());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
