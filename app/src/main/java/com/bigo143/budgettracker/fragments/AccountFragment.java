package com.bigo143.budgettracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.R;
import com.bigo143.budgettracker.adapters.CategoryAdapter;
import com.bigo143.budgettracker.models.CategoryModel;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    private ArrayList<CategoryModel> list;
    private RecyclerView rv;
    private CategoryAdapter adapter;

    public AccountFragment(ArrayList<CategoryModel> list){
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_child_list, container, false);
        rv = v.findViewById(R.id.recyclerViewChild);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CategoryAdapter(list, CategoryAdapter.TYPE_INCOME);
        rv.setAdapter(adapter);

        return v;
    }

    public void addCategory(CategoryModel category){
        list.add(category);
        adapter.notifyItemInserted(list.size() - 1);
    }
}
