package com.example.myapplication;

public class VeryHighWarnings extends Warning {
    public VeryHighWarnings() {
        minutes=120;
    }
    @Override
    public String toString() {
        return "Please Follow the Following steps\n" +
                "1-Drink small amounts of water frequently for the next hour\n" +
                "2-Reduce physical activity for the next 2 hours\n " +
                "3-Measure your Sugar blood level again after 2 hours ";
    }
}
