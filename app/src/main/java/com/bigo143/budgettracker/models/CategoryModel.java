package com.bigo143.budgettracker.models;

public class CategoryModel {
    private String name;
    private int iconRes;
    private String subtitle; // optional (balance / tag)
    private double amount; // optional

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

    public String getName(){ return name; }
    public int getIconRes(){ return iconRes; }
    public String getSubtitle(){ return subtitle; }
    public double getAmount(){ return amount; }
}
