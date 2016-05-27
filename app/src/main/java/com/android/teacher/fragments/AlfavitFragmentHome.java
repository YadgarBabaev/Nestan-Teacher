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
import com.android.teacher.adapters.AlfavitPagerAdapter;

public class AlfavitFragmentHome extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static AlfavitFragmentHome newInstance(){
        AlfavitFragmentHome fragment = new AlfavitFragmentHome();
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
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //setupViewPager(viewPager);

        AlfavitPagerAdapter adapter = new AlfavitPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //viewPager.setPageTransformer(true, new RotateUpTransformer());

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(Test1Fragment.newInstance(), "Общие");
        adapter.addFragment(Test2Fragment.newInstance(), "Бытовые");
        adapter.addFragment(Test1Fragment.newInstance(), "Бизнес");
        adapter.addFragment(Test1Fragment.newInstance(), "Для мероприятий");
        adapter.addFragment(Test1Fragment.newInstance(), "Государственные");
        adapter.addFragment(Test1Fragment.newInstance(), "Чрезвычайные");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment_alfavit, String title) {
            mFragmentList.add(fragment_alfavit);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/
}
