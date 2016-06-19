package com.android.teacher.fragments;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AlfavitFragment extends Fragment {

    private static String POS = "pos";
    private int pos;
    private String[] names;
    TypedArray images;
    TypedArray sounds;
    MediaPlayer mp = new MediaPlayer();

    public static AlfavitFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(POS, position);
        AlfavitFragment fragment = new AlfavitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt(POS);
        names = getResources().getStringArray(R.array.names);
        images = getResources().obtainTypedArray(R.array.images);
        sounds = getResources().obtainTypedArray(R.array.sounds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alfavit, null);
        ImageView image = (ImageView)view.findViewById(R.id.imageAlfavit);
        TextView name = (TextView)view.findViewById(R.id.name);
        ImageButton btn = (ImageButton)view.findViewById(R.id.sound);

        image.setImageResource(images.getResourceId(pos, -1));
        name.setText(names[pos]);

        setPlayLoad();
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {setPlayLoad();}
        });
        return view;
    }

    private  void setPlayLoad(){
        byte[] playload = new byte[0];
        try {
            int soundID = sounds.getResourceId(pos, -1);
            playload = IOUtils.toByteArray(getContext().getResources().openRawResource(soundID));
        } catch (IOException e) {e.printStackTrace();}
        playMp3(playload);
    }

    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("tempSnd", "mp3", getActivity().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mp.reset();
            mp.setDataSource(fis.getFD());
            mp.prepareAsync();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}