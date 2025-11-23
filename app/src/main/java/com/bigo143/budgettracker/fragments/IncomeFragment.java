package com.bigo143.budgettracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.R;
import com.bigo143.budgettracker.adapters.CategoryAdapter;
import com.bigo143.budgettracker.models.CategoryModel;

import java.util.ArrayList;

public class IncomeFragment extends Fragment {

    ArrayList<CategoryModel> list;
    RecyclerView rv;
    CategoryAdapter adapter;

    public IncomeFragment(ArrayList<CategoryModel> list){
        this.list = list;
    }

    public IncomeFragment(){ this.list = new ArrayList<>(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.item_income_category, container, false);
        // Instead of inflating the item directly, create a simple container layout programmatically:
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        // But we'll create a simple recycler programmatically inside
        rv = new RecyclerView(requireContext());
        rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CategoryAdapter(list, CategoryAdapter.TYPE_INCOME);
        rv.setAdapter(adapter);
        ((ViewGroup)root.findViewById(R.id.categoryContentContainer)).addView(rv);
        return root;
    }
}
