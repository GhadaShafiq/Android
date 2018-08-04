package com.example.dell.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewMember extends AppCompatActivity {


    EditText txtName,txtID;
    Spinner spn;
    Button btnSave,btnClose;
    SeekBar seekBar;
    RadioGroup rg;
    SQLiteDatabase sql;
    Database db;
    ArrayList<String> arlist ;
    int id=0,value,age;
    String name,gender,type;
    TextView AgeNum;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);

        seekBar = (SeekBar) findViewById(R.id.sekbar);
        txtID = (EditText)findViewById(R.id.txtid);
        txtName = (EditText)findViewById(R.id.txtname);
        spn = (Spinner)findViewById(R.id.spn);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnClose = (Button)findViewById(R.id.btnClose);
        rg = (RadioGroup)findViewById(R.id.group1);
        AgeNum=(TextView) findViewById(R.id.agenum);

        sql = openOrCreateDatabase("iti",0,null);
        db = new Database(sql);
        db.createTable();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                value = progress;
                AgeNum.setText(value+"");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btnSave_onClick(view);
                }

        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void btnSave_onClick(View view) {

        String tmp=txtID.getText().toString();
        if(tmp.equals(""))
            Toast.makeText(getApplicationContext(), "You did not enter ID", Toast.LENGTH_SHORT).show();
         else {
            try{
                id = Integer.parseInt(tmp);
                if(db.isExist(id))
                    Toast.makeText(getApplicationContext(), "ID exists", Toast.LENGTH_SHORT).show();
                else {
                    name = txtName.getText().toString();

                    String regx = "^[\\p{L} .'-]+$";
                    Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(name);

                    if (name.equals(""))
                        Toast.makeText(getApplicationContext(), "You did not enter name", Toast.LENGTH_SHORT).show();
                    else if (matcher.find() == false)
                        Toast.makeText(getApplicationContext(), "You have to enter a valid name", Toast.LENGTH_SHORT).show();
                    else {
                        gender = spn.getSelectedItem().toString();
                        age = value;
                        int radioButtonID = rg.getCheckedRadioButtonId();
                        View radioButton = rg.findViewById(radioButtonID);
                        int idx = rg.indexOfChild(radioButton);
                        switch (idx) {
                            case 0: {
                                type = "staff";
                                if (value == 0)
                                    age = 30;
                                break;
                            }
                            case 1: {
                                type = "student";
                                if (value == 0)
                                    age = 18;
                                break;
                            }
                        }
                        db.insert(id, name, gender, type, age);
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putStringArrayListExtra("data", db.getAll());
                        this.setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"ID is not valid",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
