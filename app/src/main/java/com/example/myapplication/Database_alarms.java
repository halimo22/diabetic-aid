package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class Database_alarms extends SQLiteOpenHelper {
    public static final String db_name = "alarm.db";
    public static final String table="alarms";
    public static final String id="id";
    public static final String id_s="id_s";

    public Database_alarms(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " create table " +table+ " ( " +id+ " INTEGER primary key autoincrement , " + id_s + " INTEGER ) ";
        db.execSQL(create);

    }

    public boolean add(Alarms alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(id_s, alarm.get_id());
        long insert = db.insert(table, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleterow(Alarms alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + table + " WHERE " + id_s + " = " + alarm.get_id();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Alarms> getEverything() {
        List<Alarms> returnList = new ArrayList<>();
        String queryString = "select * from " +table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int _id = cursor.getInt(1);
                Alarms alarm = new Alarms(_id);
                returnList.add(alarm);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
