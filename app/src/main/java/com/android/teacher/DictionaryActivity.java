package com.android.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DictionaryActivity extends AppCompatActivity {

    int dictionaryId;
    MediaPlayer mp = new MediaPlayer();
    String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dictionaryId = bundle.getInt("DICTIONARY_ID");

        TextView word = (TextView)findViewById(R.id.word);
        TextView wordMeaning1 = (TextView) findViewById(R.id.dictionary1);
        TextView wordMeaning2 = (TextView) findViewById(R.id.dictionary2);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        DbBackend dbBackend = new DbBackend(DictionaryActivity.this);
        WordObject object = dbBackend.getWordById(dictionaryId);

        assert word != null;
        word.setText(object.getWord());
        assert wordMeaning1 != null;
        wordMeaning1.setText("RU: " + object.getRU());
        assert wordMeaning2 != null;
        wordMeaning2.setText("TR: " + object.getTR());

        if(object.getImg() != null){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(object.getImg());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            assert imageView != null;
            imageView.setImageBitmap(bitmap);
        }

        if(object.getSnd() != null){
            try {
                String filename = String.valueOf(dictionaryId);
                FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(object.getSnd());
                outputStream.close();
                filePath = "/data/data/com.android.teacher/files/" + dictionaryId;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(filePath != null){
            fab.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
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
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.word_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_del:
                AlertDialog diaBox = AskOption();
                diaBox.show();
                return true;
            case R.id.action_edit:
                startActivity(new Intent(this, EditWord.class).putExtra("DICTIONARY_ID", dictionaryId));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog AskOption() {
        return new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(android.R.drawable.ic_menu_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DbBackend dbBackend = new DbBackend(DictionaryActivity.this);
                        dbBackend.delete(dictionaryId); dialog.dismiss();
                        NavUtils.navigateUpFromSameTask(DictionaryActivity.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                })
                .create();
    }
}
