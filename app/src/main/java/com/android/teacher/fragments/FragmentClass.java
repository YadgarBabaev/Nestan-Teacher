package com.android.teacher.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.DbBackend;
import com.android.teacher.R;
import com.android.teacher.WordObject;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragmentClass extends Fragment {
    private static String POS = "pos";
    private static String WRD = "list";
    private static String IDS = "ids";
    private int pos;
    private String[] names;
    private int[] ids;
    MediaPlayer mp = new MediaPlayer();
    String filePath = null;

    public static FragmentClass newInstance(int position, String[] words, int[] ids){
        Bundle args = new Bundle();
        args.putInt(POS, position);
        args.putStringArray(WRD, words);
        args.putIntArray(IDS, ids);
        FragmentClass fragment = new FragmentClass();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt(POS);
        names = getArguments().getStringArray(WRD);
        ids = getArguments().getIntArray(IDS);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alfavit, null);
        ImageView image = (ImageView)view.findViewById(R.id.imageAlfavit);
        TextView name = (TextView)view.findViewById(R.id.name);
        ImageButton btn = (ImageButton)view.findViewById(R.id.sound);

        DbBackend dbBackend = new DbBackend(getActivity());
        WordObject word = dbBackend.getWordById(ids[pos]);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(word.getImg());

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        image.setImageBitmap(bitmap);
        name.setText(names[pos]);

        if(word.getSnd() != null){
            try {
                String filename = String.valueOf(ids[pos]);
                FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(word.getSnd());
                outputStream.flush();
                outputStream.close();
                filePath = "/data/data/com.android.teacher/files/" + filename;
            } catch (Exception e) {e.printStackTrace();}
        }
        if(filePath != null){
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mp.reset();
                    mp.setDataSource(filePath);
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {mp.start();}
                    });
                    mp.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {mp.reset();}
        });
        return view;
    }
}