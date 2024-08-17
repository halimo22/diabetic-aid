package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database2 extends SQLiteOpenHelper {
    public static final String Db_name="Remind.db";
    public static final String table ="reminders";
    public static final String isrepeat="repeat";
    public static final String time="time";

    public static final String id="id";
    public static final String label="label";

    public Database2(@Nullable Context context) {
        super(context, Db_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable="create table " + table + " ( " + id + " INTEGER primary key autoincrement , " + label + " varchar(20) ," + time + "  varchar(30) ," + isrepeat + " INTEGER ) " ;
        db.execSQL(createtable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean deleterow(int postion){
        SQLiteDatabase db =this.getWritableDatabase();
        String query= " DELETE FROM " + table + " WHERE " + id + " = " + postion;
        Cursor cursor =db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }
    public boolean updaterow(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(label,reminder.getLabel());
        cv.put(time,reminder.getTime());
        cv.put(isrepeat,reminder.isRepeat());
        Integer res;
        res=db.update(table,cv," id = '" + reminder.getId() + "'",null);
        if(res>0)
            return true;
        else return false;

    }
    public boolean addone(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(label,reminder.getLabel());
        cv.put(time,reminder.getTime());
       if(reminder.isRepeat()==true){
           cv.put(isrepeat,1);
        }
       else if (reminder.isRepeat()==false){
           cv.put(isrepeat,0);
       }
        long insert=db.insert(table,null,cv);
        if(insert ==-1){
            return false;
        }else{
            return true;
        }
    }
    Cursor readalldata(){
        String query =" SELECT * FROM " + table;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }
    public List<Reminder> getEverything(){
        List<Reminder> reminderList=new ArrayList<>();
        String query="select * from " + table;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        boolean repeat=false;
        cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int id= cursor.getInt(0);
                String label= cursor.getString(1);
                String time = cursor.getString(2);
                if(cursor.getInt(3)==1){
                     repeat=true;
                }else if(cursor.getInt(3)==0){
                     repeat=false;
                }
                Reminder reminder=new Reminder(label,time,id,repeat);
                reminderList.add(reminder);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return reminderList;
    }




}
