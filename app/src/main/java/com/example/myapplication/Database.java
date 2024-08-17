package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Database  extends SQLiteOpenHelper {
    public static final String table="readings";
    public static final String reading="reading";
    public static final String carbs="carbs_gram";
    public static final String units="units";
    public static final String year="year";
    public static final String month="month";
    public static final String day="day";
    public static final String hour="hour";
    public static final String minute="minute";
    public static final String id="id";
    public static final String dbname="user1.db";
    public static final int Data_version=5;

    LocalDateTime tim= LocalDateTime.now();

    public Database(@Nullable Context context) {
        super(context,dbname,null,Data_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable="create table " +table+ " ( " +id+ " INTEGER primary key autoincrement , " +reading+ " INTEGER, " +carbs+ " INTEGER, " +units+ " INTEGER, " +year+ " INTEGER , " +month+ " INTEGER, " +day+ " INTEGER , " +hour+ " INTEGER , " +minute+ " INTEGER  ) ";
        db.execSQL(createtable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if(newVersion > oldVersion){
        // String upgrade="alter table " +table+ " add column id INTEGER Primary key autoincrement  " ;
        // db.execSQL(upgrade);}
        //db.execSQL("alter table " +table+ " add column " +id+ " INTEGER PRIMARY KEY AUTOINCREMENT;");
        //onCreate(db);


    }
    public boolean deleterow(int position){
        SQLiteDatabase db= this.getWritableDatabase();
        String query=" DELETE FROM " + table + " WHERE " + id + " = " + position;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }
    public boolean updaterow(Reading r,int position){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(reading,r.getLevel());
        cv.put(carbs,r.getGrams());
        cv.put(units,r.getInsulin());

        Integer res;
        res=db.update(table,cv," id = '" +position+ "'", null);
        if (res>0)
            return true;
        else
            return false;

    }

    public void deleteall(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(table,null,null);
    }
    public boolean addone(Reading user1){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(reading,user1.getLevel());
        cv.put(carbs,user1.getGrams());
        cv.put(units,user1.getInsulin());
        cv.put(year,tim.getYear());
        cv.put(month,tim.getMonthValue());
        cv.put(day,tim.getDayOfMonth());
        cv.put(hour,tim.getHour());
        cv.put(minute,tim.getMinute());
        long insert= db.insert(table,null,cv);
        if (insert==-1){
            return false;
        }
        else{
            return true;
        }

    }
    public ArrayList<Reading> getEverything(){
        ArrayList<Reading> returnList =new ArrayList<>();
        String queryString="select * from " +table;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                int reading= cursor.getInt(1);
                int carbs= cursor.getInt(2);
                int units= cursor.getInt(3);
                int year= cursor.getInt(4);
                int month= cursor.getInt(5);
                int day= cursor.getInt(6);
                int hour= cursor.getInt(7);
                int minute= cursor.getInt(8);
                Reading r= new Reading(LocalDateTime.of(year,month,day,hour,minute),reading,carbs);
                r.setInsulin(units);
                r.setId(id);
                returnList.add(r);


            }while(cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;

    }

}
