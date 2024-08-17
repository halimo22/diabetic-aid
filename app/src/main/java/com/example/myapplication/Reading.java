package com.example.myapplication;

import static com.example.myapplication.User.user;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
@RequiresApi(api = Build.VERSION_CODES.O)
public class Reading {

	LocalDateTime time= LocalDateTime.now();
	public Warning warning;
	int unitpergram=30;
	int unitpersugar=40;
	int optimal_value=100;
	Date date= new Date();
	int year;
	int grams;
	int insulin=0;
	String month;
	int day;
	int hour;
	int minutes;
	final String red="#E94C42";
	final String green="#C2D8B8";
	final String yellow="#F8A76C";
	int level;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	String color;
	public String getTime() {
		return String.format("%s %s %s %02d:%02d",day,month,year,hour,minutes);
	}
	int selected=0;
	static int itemcount=0;
	int id;
	public int getSelected()
	{
		return selected;
	}
	public void setSelected(int selected)
	{
		this.selected=selected;
	}

	public static int getItemcount() {
		return itemcount;
	}

	public static void setItemcount(int itemcount) {
		Reading.itemcount = itemcount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reading(LocalDateTime time, int level, int grams) {

		this.time = time;
		this.grams=grams;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			year=time.getYear();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			month=time.getMonth().toString();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			day=time.getDayOfMonth();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			hour=time.getHour();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			minutes=time.getMinute();
		}
		this.level = level;
		if (level<189&&level>=100) color=green;
		else if(level<100) color=red;
		else color=yellow;
		itemcount++;
		id=itemcount;
		unitpergram=user.carb_unit;
		unitpersugar=user.mgm_unit;
	}

	public Reading() {
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}
	public int getInsulin()
	{
		return insulin;
	}
	public int getRecommendedInsulin()
	{
		return grams/unitpergram+(level-optimal_value)/unitpersugar;
	}
	public void setInsulin(int insulin)
	{
		this.insulin=insulin;
	}
	public void setLevel(int level) {
		this.level = level;
		if (level<189&&level>=100) color=green;
		else if(level<100) color=red;
		else color=yellow;
	}
	public static int getAverage(ArrayList<Reading> readings)
	{
		int average=0;
		int count=0;
		for(Reading r:readings)
		{
			average+=r.level;
			count++;
		}
		return average;
	}




	public int getGrams() {
		return grams;
	}

	public void setGrams(int grams) {
		this.grams = grams;
	}

	public static int get1dayAverage(ArrayList<Reading> readings)
	{
		int average=0;
		int count=0;
		for (Reading r:readings
		) {
			if(LocalDateTime.now().getDayOfYear()==r.time.getDayOfYear()&&LocalDateTime.now().getYear()==r.time.getYear())
			{
				average+=r.level;
				count++;
			}
		}
		return average/count;

	}

}

