package com.bigo143.budgettracker;

public class CategoryModel {

    private String name;
    private double limit;
    private double spent;
    private int icon;



    public CategoryModel(String name, double limit, double spent, int icon) {
        this.name = name;
        this.limit = limit;
        this.spent = spent;
        this.icon = icon;
    }

    public String getName() { return name; }
    public double getLimit() { return limit; }
    public double getSpent() { return spent; }
    public int getIcon() { return icon; }
}
