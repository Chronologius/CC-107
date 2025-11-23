package com.bigo143.budgettracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.R;
import com.bigo143.budgettracker.adapters.CategoryAdapter;
import com.bigo143.budgettracker.models.CategoryModel;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    ArrayList<CategoryModel> list;

    public AccountFragment(ArrayList<CategoryModel> list){
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        // recycle view inside container
        RecyclerView rv = new RecyclerView(requireContext());
        rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rv.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        CategoryAdapter adapter = new CategoryAdapter(list, CategoryAdapter.TYPE_ACCOUNT);
        rv.setAdapter(adapter);
        ((ViewGroup)root.findViewById(R.id.categoryContentContainer)).addView(rv);
        return root;
    }
}
