package com.maltsev.greenhouse.ui.plant.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maltsev.greenhouse.ui.note.Menu;
import com.maltsev.greenhouse.ui.note.layout.NotesTabFragment;
import com.maltsev.greenhouse.ui.plant.layout.RemindersFragment;

public class PlantTabsAdapter extends FragmentStateAdapter {
    private final int totalTabs = 3;
    private String[] tabTitles = new String[]{"Reminders", "MyNotes", "Info"};

    public PlantTabsAdapter(@NonNull FragmentManager fragmentActivity, @NonNull Lifecycle lifecycle) {
        super(fragmentActivity, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new NotesTabFragment(Menu.PLANT);
            default:
                return new RemindersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
