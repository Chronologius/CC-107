package com.bigo143.budgettracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.AddRecordActivity;
import com.bigo143.budgettracker.NotBudgetedAdapter;
import com.bigo143.budgettracker.R;

public class BudgetFragment extends Fragment {

    private RecyclerView recycler;
    private NotBudgetedAdapter adapter;

    public BudgetFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        // ✅ Setup RecyclerView
        recycler = view.findViewById(R.id.recyclerCategories);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new NotBudgetedAdapter(requireContext());
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Transport", 0.0, R.drawable.ic_transport));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Groceries", 0.0, R.drawable.ic_food));
        adapter.addItem(new NotBudgetedAdapter.CategoryRow("Clothing", 0.0, R.drawable.ic_categories));
        recycler.setAdapter(adapter);

//        // ✅ Add button
//        ImageButton add = view.findViewById(R.id.btnAdd);
//        if (add != null) {
//            add.setOnClickListener(v ->
//                    startActivity(new Intent(requireContext(), AddRecordActivity.class))
//            );
//        }
//
//        // ✅ Bottom navigation setup
//        View bottomBar = view.findViewById(R.id.bottomBar);
//        if (bottomBar == null) {
//            Toast.makeText(requireContext(), "bottomBar is null!", Toast.LENGTH_SHORT).show();
//        } else {
//            Bottom_nav.setupBottomNav(requireActivity(), bottomBar);
//        }

        return view;
    }
}
