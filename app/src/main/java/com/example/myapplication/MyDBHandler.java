package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{
    public static final int DB_VERSION=2;
    private static final String DB_NAME= "food.db";
    private static final String DB_PATH="/data/data/com.example.myapplication/databases/";
    SQLiteDatabase myDataBase;
    private final Context mContext;
    public MyDBHandler(Context context,String name,SQLiteDatabase.CursorFactory factory, int version ) {
        super(context,DB_NAME, factory, DB_VERSION);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    //check if database exists
    private boolean checkDatabase(){
        try{
            final String mPath=DB_PATH+DB_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true ;
            else
                return false;
            }
        catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }
    //copy database if exists in application
    private void copyDatabase()throws IOException {
      try {
          InputStream mInputStream=mContext.getAssets().open(DB_NAME);
          String outFileName=DB_PATH+DB_NAME;
          OutputStream mOutputStream=new FileOutputStream(outFileName);
          byte[]buffer= new byte[1024];
          int length;
          while ((length=mInputStream.read(buffer))>0) {
              mOutputStream.write(buffer,0,length);
          }
          mOutputStream.flush();
          mOutputStream.close();
          mInputStream.close();
          }
      catch (Exception e){
          e.printStackTrace();
      }

    }
    //get database
    private void createDatabase()throws IOException{
        boolean mDatabaseExist=checkDatabase();
        if(!mDatabaseExist)
            this.getReadableDatabase();
        try {
            copyDatabase();
        }
        catch (IOException mIOExeption){
            mIOExeption.printStackTrace();
            throw new Error("ERROR COPYING");
        }
        finally {
            this.close();
        }
    }
    //after finishing using it you should close it
    @Override
    public synchronized void close(){
        if (myDataBase!=null)
            myDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }
    //get and show all information
    public ArrayList<Carb> loadHandler(){
        try {
            createDatabase();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        ArrayList<Carb> result =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from food",null);
        while (c.moveToNext()){
            String result_name=c.getString(0);
            int result_cpg=c.getInt(1);
            result.add(new Carb(result_cpg,result_name));
            ;
        }
        c.close();
        db.close();
        return result;
    }
}
