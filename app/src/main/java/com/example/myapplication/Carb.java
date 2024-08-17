package com.example.myapplication;

public class Carb {
    private int cpg;
    private String name;

    public Carb(int cpg,String name) {
        this.cpg = cpg;
        this.name=name;
    }

    public int getCpg() {
        return cpg;
    }

    public void setCpg(int cpg) {
        this.cpg = cpg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
