package com.bigo143.budgettracker;

/**
 * TransactionRecord model
 * - Replaces the old "Record" class to avoid conflict with java.lang.Record
 * - Supports:
 *     • Normal transactions
 *     • Date headers (isHeader = true)
 * - Contains iconName (for Material Symbols), category, account, amount, and type
 */
public class TransactionRecord {

    // ---------- Constants for type ----------
    public static final int TYPE_EXPENSE = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_TRANSFER = 2;
    public static final int TYPE_HEADER = 3;

    // ---------- Properties ----------
    private boolean isHeader;
    private String headerTitle;

    private String category;
    private String account;
    private double amount;
    private int type;
    private String iconName;   // NEW: icon string name (ic_food, ic_salary, etc.)

    // ---------- CONSTRUCTOR FOR TRANSACTIONS ----------
    public TransactionRecord(String category, String account, double amount, int type, String iconName) {
        this.category = category;
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.iconName = iconName;
        this.isHeader = false;
        this.headerTitle = "";
    }

    // ---------- STATIC FACTORY FOR HEADER ----------
    public static TransactionRecord header(String title) {
        TransactionRecord h = new TransactionRecord("", "", 0, TYPE_HEADER, "");
        h.isHeader = true;
        h.headerTitle = title;
        return h;
    }

    // ---------- GETTERS ----------
    public boolean isHeader() { return isHeader; }
    public String getHeaderTitle() { return headerTitle; }
    public String getCategory() { return category; }
    public String getAccount() { return account; }
    public double getAmount() { return amount; }
    public int getType() { return type; }
    public String getIconName() { return iconName; }
}
