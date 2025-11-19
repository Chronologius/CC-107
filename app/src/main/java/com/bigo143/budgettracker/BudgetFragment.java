package com.bigo143.budgettracker;

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

    ArrayList<CategoryModel> budgetedList = new ArrayList<>();
    ArrayList<CategoryModel> notBudgetedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSampleData();
        setupRecyclerViews();
    }

    private void setupSampleData() {

        // ---- Budgeted Categories ----
        budgetedList.add(new CategoryModel("Food", 150, 100, R.drawable.ic_food));
        budgetedList.add(new CategoryModel("Transport", 300, 180, R.drawable.ic_transport));
        budgetedList.add(new CategoryModel("Bills", 1000, 650, R.drawable.ic_bills));

        // ---- Not Budgeted Categories ----
        notBudgetedList.add(new CategoryModel("Shopping", 0, 0, R.drawable.ic_shopping));
        notBudgetedList.add(new CategoryModel("Snacks", 0, 0, R.drawable.ic_snacks));
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
}
