package com.android.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class DictionaryMain extends AppCompatActivity {

    private ArrayAdapter<String> listAdapter;
    String[] terms;
    int[] ids;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_main);

        EditText filterText = (EditText) findViewById(R.id.editText);
        ListView itemList = (ListView)findViewById(R.id.listView);

        DbBackend dbBackend = new DbBackend(DictionaryMain.this);
        ArrayObject arrayObject= dbBackend.dictionaryWords();
        terms = arrayObject.getWord();
        ids = arrayObject.getIds();

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, terms);

        assert itemList != null;
        itemList.setAdapter(listAdapter);
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
        if (id == R.id.action_add) {
            startActivity(new Intent(this, AddWord.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {super.onResume();}

    @Override
    public void onPause() {super.onPause();}
}
