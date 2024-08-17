package com.example.myapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Reminder {
    private String label;
    private int hour;
    private int min;
    private int id;
    String time;

    public String getTime() {
        return time;
    }
    public PendingIntent getIntent(Context context){
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Reminder(String label,String time, int id, boolean repeat) {
        this.label = label;
        this.time=time;
        this.id = id;
        this.repeat = repeat;
    }


    public String toString(int hour,int min) {
        this.hour=hour;
        this.min=min;
        return "" +  "" + hour + ":" + min + "";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean repeat;

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Reminder() {
    }

    public Reminder(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Reminder(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public Reminder(String label, int hour, int min) {
        this.label = label;
        this.hour = hour;
        this.min = min;
    }
}
