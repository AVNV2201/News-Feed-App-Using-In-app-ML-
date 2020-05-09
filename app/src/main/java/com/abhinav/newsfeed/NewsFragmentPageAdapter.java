package com.abhinav.newsfeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsFragmentPageAdapter extends FragmentPagerAdapter {

    public NewsFragmentPageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if( position == 0 )
            fragment = new NationalNewsFragment();

        else
            fragment = new InternationalNewsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if( position == 0 )
            return "NATIONAL";

        if( position == 1 )
            return "INTERNATIONAL";

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
