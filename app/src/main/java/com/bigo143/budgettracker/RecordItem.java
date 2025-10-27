package com.bigo143.budgettracker;

public class RecordItem {
    private final String category, date;
    private final double amount;
    public RecordItem(String category, String date, double amount) { this.category=category; this.date=date; this.amount=amount;}
    public String getCategory(){return category;}
    public String getDate(){return date;}
    public double getAmount(){return amount;}
}
