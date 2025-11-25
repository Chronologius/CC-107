package com.bigo143.budgettracker.models;

public class CategoryModel {
    private String name;
    private int iconRes;
    private String subtitle; // optional (balance / tag)
    private double amount; // optional


    private double limit;
    private double spent;
    private int icon;

    public CategoryModel(String name, int iconRes){
        this.name = name;
        this.iconRes = iconRes;
        this.subtitle = "";
        this.amount = 0;
    }

    public CategoryModel(String name, int iconRes, String subtitle, double amount){
        this.name = name;
        this.iconRes = iconRes;
        this.subtitle = subtitle;
        this.amount = amount;
    }

    public CategoryModel(String name, double limit, double spent, int icon) {
        this.name = name;
        this.limit = limit;
        this.spent = spent;
        this.icon = icon;
    }

    public String getName(){ return name; }
    public int getIconRes(){ return iconRes; }
    public String getSubtitle(){ return subtitle; }
    public double getAmount(){ return amount; }


    public double getLimit() { return limit; }
    public double getSpent() { return spent; }
    public int getIcon() { return icon; }
}
