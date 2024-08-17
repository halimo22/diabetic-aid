package com.example.myapplication;

public class LowWarnings extends Warning
{
    public LowWarnings() {
        minutes=15;
    }

    @Override
    public String toString() {
        return "Please Follow the Following steps\n" +
                "1- Drink 100-200 ml juice,sugar with water or soda drinks\n" +
                "2-Measure your Sugar blood level again after 15 mins";
    }
}
