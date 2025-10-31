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

        adapter = new NotBudgetedAdapter(this);
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Transport", 0.0, R.drawable.ic_transport));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Groceries", 0.0, R.drawable.ic_food));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Clothing", 0.0, R.drawable.ic_categories));
        recycler.setAdapter(adapter);
        //aa
        ImageButton add = findViewById(R.id.btnAdd);
        if (add != null) {
            add.setOnClickListener(v -> {
                startActivity(new android.content.Intent(this, AddRecordActivity.class));
            });
        }

        // ✅ Initialize bottom navigation bar
        View bottomBar = findViewById(R.id.bottomBar);
        if (bottomBar == null) {
            android.widget.Toast.makeText(this, "bottomBar is null!", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            Bottom_nav.setupBottomNav(this, bottomBar);
        }
    }
}
