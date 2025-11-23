package com.bigo143.budgettracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigo143.budgettracker.databinding.FragmentBudgetBinding;

import java.util.ArrayList;

public class BudgetFragment extends Fragment {

    private FragmentBudgetBinding binding;
    private BudgetedAdapter budgetedAdapter;
    private NotBudgetedAdapter notBudgetedAdapter;

    private ArrayList<CategoryModel> budgetedList = new ArrayList<>();
    private ArrayList<CategoryModel> notBudgetedList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private String currentUser = "john_doe"; // TODO: replace with actual logged-in username

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        dbHelper = new DatabaseHelper(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadBudgetedData();
        loadNotBudgetedData();
        setupRecyclerViews();
    }

    private void loadBudgetedData() {
        budgetedList.clear();
        Cursor cursor = dbHelper.getBudgetedCategories(currentUser);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double limit = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                double spent = dbHelper.getTotalExpenseForCategory(currentUser, name); // optional method
                int icon = getIconForCategory(name); // optional, map your drawables
                budgetedList.add(new CategoryModel(name, limit, spent, icon));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void loadNotBudgetedData() {
        notBudgetedList.clear();
        Cursor cursor = dbHelper.getUnbudgetedCategories(currentUser);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int icon = getIconForCategory(name); // optional
                notBudgetedList.add(new CategoryModel(name, 0, 0, icon));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void setupRecyclerViews() {
        // Budgeted List
        budgetedAdapter = new BudgetedAdapter(budgetedList, requireContext());
        binding.rvBudgeted.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBudgeted.setAdapter(budgetedAdapter);

        // Not Budgeted List
        notBudgetedAdapter = new NotBudgetedAdapter(notBudgetedList, requireContext());
        binding.rvNotBudgeted.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNotBudgeted.setAdapter(notBudgetedAdapter);
    }

    // Example mapping of drawable icons for categories
    private int getIconForCategory(String name) {
        switch (name.toLowerCase()) {
            case "food":
                return R.drawable.ic_food;
            case "transport":
                return R.drawable.ic_transport;
            case "bills":
                return R.drawable.ic_bills;
            case "shopping":
                return R.drawable.ic_shopping;
            case "snacks":
                return R.drawable.ic_snacks;

        }
        return 0;
    }
}
