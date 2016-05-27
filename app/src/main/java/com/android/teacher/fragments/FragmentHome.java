package com.android.teacher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.teacher.R;
import com.android.teacher.adapters.PageAdapter;

public class FragmentHome extends Fragment {

    private long id;

    public static FragmentHome newInstance(long cid){
        FragmentHome fragment = new FragmentHome();
        fragment.setCId(cid);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alfavit_home, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        PageAdapter adapter = new PageAdapter(getActivity(), getChildFragmentManager(), getCId());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private long getCId(){return id;}

    private void setCId(long id){this.id = id;}
}
