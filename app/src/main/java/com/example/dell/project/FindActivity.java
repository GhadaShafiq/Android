package com.example.dell.project;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class FindActivity extends AppCompatActivity {
    Button find;
    EditText txt;
    SQLiteDatabase sql;
    Database db;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        find = (Button)findViewById(R.id.btnSearch);
        txt = (EditText)findViewById(R.id.txt);
        sql = openOrCreateDatabase("iti",0,null);
        db = new Database(sql);
        list = (ListView)findViewById(R.id.list);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNumeric(txt.getText().toString()))
                {
                    ArrayList<String> id = new ArrayList<String>();
                    id = db.getByID(Integer.parseInt(txt.getText().toString()));
                    final ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, id);
                    list.setAdapter(adapter1);
                }
                else
                {
                    ArrayList<String> name=new ArrayList<String>();
                    name=db.getByName(txt.getText().toString());
                    final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                    list.setAdapter(adapter);
                }

            }
        });
    }
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(.\\d+)?");
    }

}
