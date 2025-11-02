package com.bigo143.budgettracker;

public class Category {
    private String name;
    private double limit;
    private double spent;
    private int imageRes;

    public Category(String name, double limit, double spent, int imageRes) {
        this.name = name;
        this.limit = limit;
        this.spent = spent;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public double getLimit() { return limit; }
    public double getSpent() { return spent; }
    public int getImageRes() { return imageRes; }

    public void setLimit(double limit) { this.limit = limit; }
    public void setSpent(double spent) { this.spent = spent; }
    public void setName(String name) { this.name = name; }
}
