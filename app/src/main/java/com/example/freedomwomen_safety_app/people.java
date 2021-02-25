package com.example.freedomwomen_safety_app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class people extends AppCompatActivity {

    Button b1, b2, b3;
    EditText el;
    ListView listView;
    SQLiteOpenHelper s1;
    SQLiteDatabase sqlitedb;
    DatabaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        el = findViewById(R.id.phone);
        b1 = findViewById(R.id.add);
        b2 = findViewById(R.id.delete);
        b3 = findViewById(R.id.view);
        myDB = new DatabaseHandler(this);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sr = el.getText().toString();
                addData(sr);

                Toast.makeText(people.this, "Data added", Toast.LENGTH_SHORT).show();
                el.setText("");
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlitedb = myDB.getWritableDatabase();
                String x = el.getText().toString();
                DeleteData(x);
                Toast.makeText(people.this, "DATA DELETED",Toast.LENGTH_SHORT).show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {

        ArrayList<String> thelist = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(people.this, "There is no content", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                thelist.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thelist);
                listView.setAdapter(listAdapter);
            }

        }
    }

    private void addData (String newEntry){

        boolean insertData = myDB.addData( newEntry);
          if (insertData==true) {
         Toast.makeText( people.this, "Data added",Toast.LENGTH_SHORT ).show();
                               }
           else {
           Toast.makeText(people.this, "Unsuccessful",Toast.LENGTH_SHORT).show();
             }
    }

    private boolean DeleteData(String x) {
        return sqlitedb.delete(DatabaseHandler.TABLE_NAME,DatabaseHandler.COL2 + "=?", new String[]{x})>0;
    }
}

