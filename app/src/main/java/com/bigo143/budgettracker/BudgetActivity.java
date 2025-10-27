package com.bigo143.budgettracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BudgetActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private NotBudgetedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        recycler = findViewById(R.id.recyclerCategories);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // sample data
        adapter = new NotBudgetedAdapter(this);
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Transport", 0.0, R.drawable.ic_transport));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Groceries", 0.0, R.drawable.ic_food));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Clothing", 0.0, R.drawable.ic_categories));
        recycler.setAdapter(adapter);

        ImageButton add = findViewById(R.id.btnAdd); // bottom nav fab id reused
        if (add != null) {
            add.setOnClickListener(v -> {
                // Launch AddRecordActivity
                startActivity(new android.content.Intent(this, AddRecordActivity.class));
            });
        }
    }
}
