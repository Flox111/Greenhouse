package com.maltsev.greenhouse;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maltsev.greenhouse.ui.fragment.SigninTabFragment;
import com.maltsev.greenhouse.ui.fragment.SignupTabFragment;

public class AuthAdapter extends FragmentStateAdapter {
    private final int totalTabs = 2;
    private String[] tabTitles = new String[]{"Login", "SignUp"};

    public AuthAdapter(@NonNull FragmentManager fragmentActivity, @NonNull Lifecycle lifecycle){
        super(fragmentActivity, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment();
                return signupTabFragment;
            default:
                return new SigninTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}