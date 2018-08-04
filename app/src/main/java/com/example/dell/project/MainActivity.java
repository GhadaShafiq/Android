package com.example.dell.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.StreamHandler;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity  {
    FloatingActionButton fab;
    ListView listview;
    SQLiteDatabase sql;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.btnGo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,NewMember.class),1);
            }
        });

        sql = openOrCreateDatabase("iti",0,null);
        db = new Database(sql);

        listview = (ListView)findViewById(R.id.lst);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int index, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("are you sure you want to delete this item ?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                TextView tv = (TextView) view;
                                db.delete(tv.getText().toString());
                                ArrayList<String> arr = db.getAll();
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr);
                                arrayAdapter.notifyDataSetChanged();
                                listview.setAdapter(arrayAdapter);
                            }
                        });
                alertDialog.show();

                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Object listItem = listview.getItemAtPosition(i);
               // Toast.makeText(getApplicationContext(),listItem.toString(),Toast.LENGTH_SHORT).show();
                ArrayList<String> res = db.getByName(listItem.toString());
                for(int j=0;j<res.size();j++){
                    Toast.makeText(getApplicationContext(),res.get(j)+" "+j,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            //Toast.makeText(getApplicationContext(),"return to main activity",Toast.LENGTH_SHORT).show();
            if(resultCode == Activity.RESULT_OK){
               // Toast.makeText(getApplicationContext(),"i have result ",Toast.LENGTH_SHORT).show();

                ArrayList<String> result= data.getStringArrayListExtra("data");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,result);

                listview.setAdapter(adapter);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void sbnMethod(MenuItem m) {

        ArrayList<String> arr = db.SortByName();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr);
        arrayAdapter.notifyDataSetChanged();
        listview.setAdapter(arrayAdapter);
    }

    public void findMethod(MenuItem m) {
        startActivity(new Intent(this, FindActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.ClearDatabase();
    }
}