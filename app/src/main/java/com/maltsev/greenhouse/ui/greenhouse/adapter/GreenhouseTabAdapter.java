package com.maltsev.greenhouse.ui.greenhouse.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maltsev.greenhouse.ui.note.Menu;
import com.maltsev.greenhouse.ui.greenhouse.layout.ControlPanelTabFragment;
import com.maltsev.greenhouse.ui.note.layout.NotesTabFragment;

public class GreenhouseTabAdapter extends FragmentStateAdapter {
    private final int totalTabs = 2;
    private String[] tabTitles = new String[]{"Control panel", "MyNotes"};
    private String nameOfGreenhouse;

    public GreenhouseTabAdapter(@NonNull FragmentManager fragmentActivity, @NonNull Lifecycle lifecycle) {
        super(fragmentActivity, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new NotesTabFragment(Menu.GREENHOUSE);
            default:
                return new ControlPanelTabFragment(nameOfGreenhouse);
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }

    public String getNameOfGreenhouse() {
        return nameOfGreenhouse;
    }

    public void setNameOfGreenhouse(String nameOfGreenhouse) {
        this.nameOfGreenhouse = nameOfGreenhouse;
    }
}
