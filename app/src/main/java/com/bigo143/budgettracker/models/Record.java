package com.bigo143.budgettracker.models;

public class Record {

    public static final int TYPE_HEADER = -1;
    public static final int TYPE_EXPENSE = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_TRANSFER = 2;

    private boolean isHeader;
    private String headerTitle;

    private String category;
    private String account;
    private double amount;
    private int type;
    private int iconResId;

    // ------------------------------------
    // REQUIRED: EMPTY CONSTRUCTOR
    // ------------------------------------
    public Record() {
        // used for header creation
    }

    // ------------------------------------
    // HEADER FACTORY
    // ------------------------------------
    public static Record header(String title) {
        Record r = new Record();   // now VALID
        r.isHeader = true;
        r.headerTitle = title;
        r.type = TYPE_HEADER;
        return r;
    }

    // ------------------------------------
    // ITEM CONSTRUCTOR
    // ------------------------------------
    public Record(String category, String account, double amount, int type, String iconName) {
        this.isHeader = false;
        this.category = category;
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.iconResId = iconResId;
    }

    // ------------------------------------
    // GETTERS
    // ------------------------------------
    public boolean isHeader() { return isHeader; }

    public String getHeaderTitle() { return headerTitle; }

    public String getCategory() { return category; }

    public String getAccount() { return account; }

    public double getAmount() { return amount; }

    public int getType() { return type; }

    public int getIconName() { return iconResId; }
}
