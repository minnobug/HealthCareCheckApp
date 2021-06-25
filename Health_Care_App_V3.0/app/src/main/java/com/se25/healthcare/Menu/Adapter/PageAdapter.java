package com.se25.healthcare.Menu.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<Fragment> fragments;
    private ArrayList<Integer> titlesId;

    public void addFragment(Fragment fragment, int titleId) {
        if(fragments == null){
            fragments = new ArrayList<>();
            titlesId = new ArrayList<>();
        }
        fragments.add(fragment);
        titlesId.add(titleId);
    }

    public PageAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public Fragment getItemById(int id) {
        return fragments.get(titlesId.indexOf(id));
    }

    public int getPositiveById(int id) {
        return titlesId.indexOf(id);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titlesId.get(position));
    }
}
