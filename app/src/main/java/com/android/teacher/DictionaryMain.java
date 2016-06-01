package com.android.teacher;

import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DictionaryMain extends AppCompatActivity {

    ArrayAdapter<String> listAdapter;
    ListView itemList;
    int[] ids;
    boolean returned = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText filterText = (EditText) findViewById(R.id.editText);
        itemList = (ListView)findViewById(R.id.listView);

        DbBackend dbBackend = new DbBackend(DictionaryMain.this);
        final ArrayObject arrayObject= dbBackend.dictionaryWords();
        ids = arrayObject.getIds();

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        assert rg != null;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lang1:
                        filler(arrayObject.getKgList());
                        break;
                    case R.id.lang2:
                        filler(arrayObject.getRuList());
                        break;
                    case R.id.lang3:
                        filler(arrayObject.getTrList());
                        break;
                }
            }
        });

        rg.check(R.id.lang1);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DictionaryMain.this, DictionaryActivity.class);
                intent.putExtra("DICTIONARY_ID", ids[position]);
                startActivity(intent);
            }
        });

        assert filterText != null;
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DictionaryMain.this.listAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home)
            finish();

        if(id == R.id.action_add) {
            startActivity(new Intent(this, AddWord.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {super.onResume();
        if(returned){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void onPause() {super.onPause();
        returned = true;
    }

    @Override
    public ActionBar getSupportActionBar() {
        // Making getSupportActionBar() method to be @NonNull
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar == null) throw new NullPointerException("Action bar was not initialized");
        return actionBar;
    }

    private void filler(String[] terms){
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, terms);
        itemList.setAdapter(listAdapter);
    }
}
