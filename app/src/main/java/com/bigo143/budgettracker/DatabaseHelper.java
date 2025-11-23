package com.bigo143.budgettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "budget_tracker.db";
    private static final int DATABASE_VERSION = 3; // incremented to force upgrade

    // --- Users table ---
    private static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    // --- Categories table ---
    private static final String TABLE_CATEGORIES = "categories";
    public static final String COL_CATEGORY_ID = "id";
    public static final String COL_CATEGORY_USER = "username";
    public static final String COL_CATEGORY_TYPE = "type";
    public static final String COL_CATEGORY_NAME = "name";

    // --- Budgets table ---
    private static final String TABLE_BUDGETS = "budgets";
    public static final String COL_BUDGET_ID = "id";
    public static final String COL_BUDGET_USER = "username";
    public static final String COL_BUDGET_CATEGORY = "category_id";
    public static final String COL_BUDGET_AMOUNT = "amount";

    // --- Records table ---
    private static final String TABLE_RECORDS = "records";
    public static final String COL_RECORD_ID = "id";
    public static final String COL_RECORD_USER = "username";
    public static final String COL_RECORD_CATEGORY = "category_id";
    public static final String COL_RECORD_TYPE = "type";
    public static final String COL_RECORD_AMOUNT = "amount";
    public static final String COL_RECORD_DATE = "date";
    public static final String COL_RECORD_NOTE = "note";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)");

        // Categories table
        db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY_USER + " TEXT, " +
                COL_CATEGORY_TYPE + " TEXT, " +
                COL_CATEGORY_NAME + " TEXT)");

        // Budgets table
        db.execSQL("CREATE TABLE " + TABLE_BUDGETS + " (" +
                COL_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BUDGET_USER + " TEXT, " +
                COL_BUDGET_CATEGORY + " TEXT, " +
                COL_BUDGET_AMOUNT + " REAL)");

        // Records table
        db.execSQL("CREATE TABLE " + TABLE_RECORDS + " (" +
                COL_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECORD_USER + " TEXT, " +
                COL_RECORD_CATEGORY + " TEXT, " +
                COL_RECORD_TYPE + " TEXT, " +
                COL_RECORD_AMOUNT + " REAL, " +
                COL_RECORD_DATE + " TEXT, " +
                COL_RECORD_NOTE + " TEXT)");
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Drop old tables only if necessary (optional)
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        // Create new tables if they don't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
                COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY_USER + " TEXT, " +
                COL_CATEGORY_TYPE + " TEXT, " +
                COL_CATEGORY_NAME + " TEXT)");

        // Budgets table
        db.execSQL("CREATE TABLE " + TABLE_BUDGETS + " (" +
                COL_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BUDGET_USER + " TEXT, " +
                COL_BUDGET_CATEGORY + " INTEGER, " +
                COL_BUDGET_AMOUNT + " REAL)");

        // Records table
        db.execSQL("CREATE TABLE " + TABLE_RECORDS + " (" +
                COL_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECORD_USER + " TEXT, " +
                COL_RECORD_CATEGORY + " TEXT, " +
                COL_RECORD_TYPE + " TEXT, " +
                COL_RECORD_AMOUNT + " REAL, " +
                COL_RECORD_DATE + " TEXT, " +
                COL_RECORD_NOTE + " TEXT)");
    }


    // ---------------- User management ----------------

    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        try {
            long result = db.insertOrThrow(TABLE_USERS, null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error registering user: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username = ? AND password = ?",
                    new String[]{username, password});
            exists = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error logging in: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        return exists;
    }

    public boolean userExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ?",
                    new String[]{email});
            exists = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error checking user exists: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        return exists;
    }

    // ---------------- Category management ----------------

    public boolean insertCategory(String username, String type, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_USER, username);
        values.put(COL_CATEGORY_TYPE, type);
        values.put(COL_CATEGORY_NAME, name);

        try {
            long result = db.insert(TABLE_CATEGORIES, null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting category: " + e.getMessage());
            return false;
        }
    }

    public Cursor getCategoriesForUserAndType(String username, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES + " WHERE username = ? AND type = ?",
                    new String[]{username, type});
        } catch (Exception e) {
            Log.e(TAG, "Error fetching categories: " + e.getMessage());
            return null;
        }
    }

    public boolean updateCategory(int id, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, newName);

        try {
            int rows = db.update(TABLE_CATEGORIES, values, COL_CATEGORY_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error updating category: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rows = db.delete(TABLE_CATEGORIES, COL_CATEGORY_ID + " = ?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting category: " + e.getMessage());
            return false;
        }
    }

    // ---------------- Budget management ----------------

    public boolean insertBudget(String username, int categoryId, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BUDGET_USER, username);
        values.put(COL_BUDGET_CATEGORY, categoryId);
        values.put(COL_BUDGET_AMOUNT, amount);

        try {
            long result = db.insert(TABLE_BUDGETS, null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting budget: " + e.getMessage());
            return false;
        }
    }

    public Cursor getBudgetedCategories(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT c.name, b.amount FROM " + TABLE_BUDGETS + " b " +
                        "JOIN " + TABLE_CATEGORIES + " c ON b.category_id = c.id " +
                        "WHERE b.username = ?",
                new String[]{username});
    }

    public Cursor getUnbudgetedCategories(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT c.name FROM " + TABLE_CATEGORIES + " c " +
                        "WHERE c.username = ? AND c.id NOT IN (SELECT category_id FROM " + TABLE_BUDGETS + " WHERE username = ?)",
                new String[]{username, username});
    }

    public double getTotalBudget(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_BUDGETS + " WHERE username = ?", new String[]{username});
        double total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(0);
            cursor.close();
        }
        return total;
    }

    // ---------------- Records management ----------------

    public boolean insertRecord(String username, int categoryId, String type, double amount, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RECORD_USER, username);
        values.put(COL_RECORD_CATEGORY, categoryId);
        values.put(COL_RECORD_TYPE, type);
        values.put(COL_RECORD_AMOUNT, amount);
        values.put(COL_RECORD_DATE, date);
        values.put(COL_RECORD_NOTE, note);

        try {
            long result = db.insert(TABLE_RECORDS, null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting record: " + e.getMessage());
            return false;
        }
    }

    public double getTotalExpense(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_RECORDS + " WHERE username = ? AND type = 'expense'",
                new String[]{username});
        double total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(0);
            cursor.close();
        }
        return total;
    }

    public double getTotalIncome(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_RECORDS + " WHERE username = ? AND type = 'income'",
                new String[]{username});
        double total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(0);
            cursor.close();
        }
        return total;
    }

    public Cursor getRecordsForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT r.id, c.name, r.type, r.amount, r.date, r.note FROM " + TABLE_RECORDS + " r " +
                        "LEFT JOIN " + TABLE_CATEGORIES + " c ON r.category_id = c.id " +
                        "WHERE r.username = ? ORDER BY r.date DESC",
                new String[]{username});
    }

    // Calculate total spent for a category for a user
    public double getTotalExpenseForCategory(String username, String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        double total = 0;

        try {
            // First, get category id from name
            cursor = db.rawQuery("SELECT id FROM " + TABLE_CATEGORIES + " WHERE username = ? AND name = ?",
                    new String[]{username, categoryName});

            int categoryId = -1;
            if (cursor != null && cursor.moveToFirst()) {
                categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                cursor.close();
            }

            if (categoryId != -1) {
                cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_RECORDS + " WHERE username = ? AND category_id = ? AND type = 'expense'",
                        new String[]{username, String.valueOf(categoryId)});
                if (cursor != null && cursor.moveToFirst()) {
                    total = cursor.getDouble(0);
                    cursor.close();
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error getting total expense for category: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }

        return total;
    }



    // Get total spent for a category
    public double getTotalSpentForCategory(String username, int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT SUM(amount) FROM records WHERE username = ? AND category_id = ?",
                    new String[]{username, String.valueOf(categoryId)});
            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching total spent: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return total;
    }

    // Get categories without budget
    public Cursor getNotBudgetedCategories(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT c.id, c.name FROM categories c " +
                        "LEFT JOIN budgets b ON c.id = b.category_id " +
                        "WHERE c.username = ? AND b.amount IS NULL",
                new String[]{username});
    }


}
