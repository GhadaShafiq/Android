package com.example.dell.project;
import java.util.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dell on 9/16/2017.
 */

public class Database {
    SQLiteDatabase sql;
    public Database(SQLiteDatabase sql)
    {
        this.sql = sql;
    }
    private static final String Database_Name="iti.db";

    public  void createTable(){
        try{

            sql.execSQL( "create table if not exists Members "+
                    "( id integer primary key , name char, gender char, type varchar, age integer );");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void insert(int id,String name,String gender,String type, int age){
       try{
           sql.execSQL("INSERT INTO Members(id, name, gender, type, age)\n" +
                   "VALUES("+id+",'"+name+"','"+gender+"','"+type+"',"+age+");");
       }
         catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> getAll(){
        ArrayList<String> arr = new ArrayList<String>();
        Cursor c = sql.rawQuery("select name from Members",null);
        if(c.getCount() >0){
            while(c.moveToNext()){
                arr.add(c.getString(0));
            }

        }
        c.close();
        return arr;
    }
    public void delete(String name){
        try{
            sql.execSQL("DELETE\n" +
                    "FROM\n" +
                    " Members\n" +
                    "WHERE\n" +
                    " name ='"+name+"';");
           Log.i("DB","data deleted successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> SortByName(){
        ArrayList<String> arr = getAll();
        Collections.sort(arr);
        return arr;
    }


    public ArrayList<String> getByName(String name){
        ArrayList<String> arr=new ArrayList<String>();
        Cursor cursor=sql.rawQuery("SELECT NAME FROM Members WHERE NAME='"+name+"'",null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                arr.add(cursor.getString(0));
            }
        }
        cursor.close();
        return  arr;
    }
    public ArrayList<String> getByID(int id){
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cursor = sql.rawQuery("SELECT NAME FROM Members WHERE ID ="+id+";", null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                arr.add(cursor.getString(0));
            }
        }
        cursor.close();
        return arr;
    }
    public void ClearDatabase(){
        sql.execSQL("delete * from Members");
    }
    public boolean isExist(int id){
        Cursor c = sql.rawQuery("select id from Members",null);
        if(c.getCount() >0){
            while(c.moveToNext()){
                if(id==c.getInt(0)){
                    return true;
                }
            }
        }
        c.close();
        return false;
    }

}
