package com.duykhanh.a9l01sqlite.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.duykhanh.a9l01sqlite.View.FragmentAdd_data;
import com.duykhanh.a9l01sqlite.View.FragmentShowData;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        Log.d("FM", "ViewPagerAdapter: " + fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentAdd_data.newInstance();
            case 1:
                return FragmentShowData.newInstance();
        }
        return null;
    }


    public void addFragment(Fragment framgent, String title) {
        fragmentList.add(framgent);
        mFragmentTitleList.add(title);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
