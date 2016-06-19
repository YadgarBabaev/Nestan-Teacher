package com.android.teacher.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.teacher.R;
import com.android.teacher.fragments.AlfavitFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlfavitPagerAdapter extends FragmentPagerAdapter {
    public Context context;
    private ArrayList<String> categoryTitle;

    public AlfavitPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        setTitle();
    }

    private void setTitle(){
        String[]alfavit = context.getResources().getStringArray(R.array.alfavit);
        categoryTitle = new ArrayList<>(Arrays.asList(alfavit));
    }

    @Override
    public Fragment getItem(int position) {
        return AlfavitFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return categoryTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryTitle.get(position);
    }
}

