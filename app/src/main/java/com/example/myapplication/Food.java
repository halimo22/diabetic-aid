package com.example.myapplication;

public class Food extends Carb {
    private double carbstaken;
    private int amount;
    public Food(int cpg, String name,int amount) {
        super(cpg, name);
        this.amount=amount;
        carbstaken=(cpg/100.00)*amount;
    }
    public Food(Carb carb,int amount)
    {
        super(carb.getCpg(), carb.getName());
        this.amount=amount;
        carbstaken=(carb.getCpg()/100.0)*amount;
    }

    public double getCarbstaken() {
        return carbstaken;
    }

    public void setCarbstaken(double carbstaken) {
        this.carbstaken = carbstaken;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
