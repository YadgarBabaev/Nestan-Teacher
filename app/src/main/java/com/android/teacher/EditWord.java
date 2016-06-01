package com.android.teacher;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EditWord extends ActionBarActivity {

    int id;
    String kg, ru, tr;
    ImageButton img, snd;
    Spinner category;
    int categoryId;
    byte[] imageByteArray;
    byte[] soundByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activities_layout);

        id = getIntent().getIntExtra("DICTIONARY_ID", 0);

        LinearLayout ll = (LinearLayout)findViewById(R.id.editFrame);
        ll.setVisibility(View.VISIBLE);

        img = (ImageButton) findViewById(R.id.selectImage);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });
        snd = (ImageButton) findViewById(R.id.selectSound);
        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        DbBackend dbBackend = new DbBackend(this);
        WordObject word = dbBackend.getWordById(id);

        ((EditText)findViewById(R.id.word)).setText(word.getWord());
        ((EditText)findViewById(R.id.translationRU)).setText(word.getRU());
        ((EditText)findViewById(R.id.translationTR)).setText(word.getTR());
        ((TextView)findViewById(R.id.imgByteArray)).setText("Image: " + word.getImg().toString());
        if(word.getSnd() != null) {
            soundByteArray = word.getSnd();
            ((TextView) findViewById(R.id.sndByteArray)).setText("Sound: " + word.getSnd().toString());
        }
        if(word.getImg() != null){
            imageByteArray = word.getImg();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(word.getImg());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            img.setImageBitmap(bitmap);
        }

        String[] terms = dbBackend.categoryWords();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, terms);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category = (Spinner) findViewById(R.id.categorySpin);
        category.setAdapter(listAdapter);
        category.setSelection(word.getCat());
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {categoryId = position;}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {categoryId = 0;}
        });

        Button btn = (Button)findViewById(R.id.saveData);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(filePath);
            img.setImageBitmap(image);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            imageByteArray = outputStream.toByteArray();
        }
        else if(resultCode == RESULT_OK && requestCode == 100){
            Uri selectedSound = data.getData();
            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedSound, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            try {
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                DataInputStream dis = new DataInputStream(bis);
                soundByteArray = toByteArray(dis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAction(){
        kg = ((EditText)findViewById(R.id.word)).getText().toString();
        ru = ((EditText)findViewById(R.id.translationRU)).getText().toString();
        tr = ((EditText)findViewById(R.id.translationTR)).getText().toString();

        if(kg != null && ru != null && tr != null && imageByteArray != null) {
            DbBackend dbBackend = new DbBackend(this);
            int ans = dbBackend.update(id, kg, ru, tr, imageByteArray, soundByteArray, categoryId);
            if(ans > 0)
                finish();
            else
                Toast.makeText(this, "Error occurred while data inserted", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in,out);
        return out.toByteArray();
    }

    private static long copy(InputStream from, OutputStream to) throws IOException {
        byte[] buf = new byte[4000];
        long total = 0;
        while (true){
            int r = from.read(buf);
            if (r==-1) break;
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }
}