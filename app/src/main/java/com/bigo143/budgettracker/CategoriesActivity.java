package com.bigo143.budgettracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoryAdapter.OnItemActionListener {

    public static final String TYPE_INCOME = "income";
    public static final String TYPE_ACCOUNT = "account";
    public static final String TYPE_EXPENSE = "expense";

    private Button btnIncome, btnAccounts, btnExpense;
    private ImageButton btnAdd;
    private RecyclerView rvCategories;
    private TextView tvEmpty;
    private CategoryAdapter adapter;
    private DatabaseHelper dbHelper;
    private String currentType = TYPE_INCOME;
    private String username; // logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        dbHelper = new DatabaseHelper(this);

        // get logged-in username
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        username = prefs.getString("username", null);

        btnIncome = findViewById(R.id.btnTypeIncome);
        btnAccounts = findViewById(R.id.btnTypeAccounts);
        btnExpense = findViewById(R.id.btnTypeExpense);
        btnAdd = findViewById(R.id.btnAddCategory);
        rvCategories = findViewById(R.id.rvCategories);
        tvEmpty = findViewById(R.id.tvEmpty);

        adapter = new CategoryAdapter(new ArrayList<>(), this);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        rvCategories.setAdapter(adapter);

        // click handlers to switch type
        View.OnClickListener typeClick = v -> {
            if (v.getId() == R.id.btnTypeIncome) selectType(TYPE_INCOME);
            else if (v.getId() == R.id.btnTypeAccounts) selectType(TYPE_ACCOUNT);
            else selectType(TYPE_EXPENSE);
        };

        btnIncome.setOnClickListener(typeClick);
        btnAccounts.setOnClickListener(typeClick);
        btnExpense.setOnClickListener(typeClick);

        // Add new category
        btnAdd.setOnClickListener(v -> showAddDialog());

        // default
        selectType(currentType);
    }

    private void selectType(String type) {
        currentType = type;
        // update chip UI (simple color toggle)
        int blue = ContextCompat.getColor(this, R.color.primaryBlue);
        int lightGray = ContextCompat.getColor(this, R.color.grayLight);
        int white = ContextCompat.getColor(this, android.R.color.white);
        int black = ContextCompat.getColor(this, R.color.black);

        btnIncome.setBackgroundTintList(
                ContextCompat.getColorStateList(this, type.equals(TYPE_INCOME) ? R.color.primaryBlue : R.color.grayLight)
        );
        btnIncome.setTextColor(type.equals(TYPE_INCOME) ? white : black);

        btnAccounts.setBackgroundTintList(
                ContextCompat.getColorStateList(this, type.equals(TYPE_ACCOUNT) ? R.color.primaryBlue : R.color.grayLight)
        );
        btnAccounts.setTextColor(type.equals(TYPE_ACCOUNT) ? white : black);

        btnExpense.setBackgroundTintList(
                ContextCompat.getColorStateList(this, type.equals(TYPE_EXPENSE) ? R.color.primaryBlue : R.color.grayLight)
        );
        btnExpense.setTextColor(type.equals(TYPE_EXPENSE) ? white : black);

        loadCategories();
    }

    private void loadCategories() {
        adapter.clear();
        if (username == null) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("No user found. Please log in.");
            return;
        }

        Cursor c = dbHelper.getCategoriesForUserAndType(username, currentType);
        if (c != null && c.getCount() > 0) {
            tvEmpty.setVisibility(View.GONE);
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                String name = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_USERNAME));
                adapter.add(new CategoryAdapter.Category(id, name));
            }
            c.close();
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("No categories yet. Tap + to add.");
        }
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add " + capitalize(currentType) + " category");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = dbHelper.insertCategory(username, currentType, name);
            if (ok) {
                Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show();
                loadCategories();
            } else {
                Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // Adapter callbacks
    @Override
    public void onEdit(CategoryAdapter.Category category) {
        // show edit dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit category");

        final EditText input = new EditText(this);
        input.setText(category.name);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                boolean ok = dbHelper.updateCategory(category.id, newName);
                if (ok) {
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    loadCategories();
                } else Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onDelete(CategoryAdapter.Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Delete category")
                .setMessage("Delete '" + category.name + "'?")
                .setPositiveButton("Delete", (d, w) -> {
                    boolean ok = dbHelper.deleteCategory(category.id);
                    if (ok) {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        loadCategories();
                    } else Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
