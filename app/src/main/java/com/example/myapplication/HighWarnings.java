package com.example.myapplication;

public class HighWarnings extends Warning {
    public HighWarnings() {
        minutes=120;
    }

    @Override
        public String toString() {
            return "Please Follow the Following steps\n" +
                    "1-Drink small amounts of water frequently for the next hour\n" +
                    "2-Measure your Sugar blood level again after 2 hours ";
        }

}
