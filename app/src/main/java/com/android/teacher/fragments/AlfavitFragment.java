package com.android.teacher.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;

public class AlfavitFragment extends Fragment {
    private static String POS="pos";
    private int pos;
    private String[] names;
    TypedArray images;

    public static AlfavitFragment newInstance(int position){
        Bundle args=new Bundle();
        args.putInt(POS, position);
        AlfavitFragment fragment = new AlfavitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos=getArguments().getInt(POS);
        names = getResources().getStringArray(R.array.names);
        images = getResources().obtainTypedArray(R.array.images);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alfavit, null);
        ImageView image=(ImageView)view.findViewById(R.id.imageAlfavit);
        TextView name=(TextView)view.findViewById(R.id.name);
        image.setImageResource(images.getResourceId(pos, -1));
        name.setText(names[pos]);
        return view;
    }
}