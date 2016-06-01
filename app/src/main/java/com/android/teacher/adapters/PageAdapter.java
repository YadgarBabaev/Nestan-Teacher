package com.android.teacher.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.teacher.ArrayObject;
import com.android.teacher.DbBackend;
import com.android.teacher.fragments.FragmentClass;

import java.util.ArrayList;
import java.util.Arrays;

public class PageAdapter extends FragmentPagerAdapter {
    public Context context;
    private ArrayList<String> title;
    private long id;
    String[] words;
    int[] ids;

    public PageAdapter(Context context, FragmentManager fm, long id) {
        super(fm);
        this.context = context;
        this.id = id;
        setTitle();
    }

    private void setTitle(){
        DbBackend dbBackend = new DbBackend(context);
        ArrayObject array = dbBackend.getWordByCategory(id);
        words = array.getKgList();
        ids = array.getIds();
        title = new ArrayList<>(Arrays.asList(words));
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentClass.newInstance(position, words, ids);
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}

