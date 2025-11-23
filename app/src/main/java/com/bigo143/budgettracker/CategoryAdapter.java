package com.bigo143.budgettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {

    public interface OnItemActionListener {
        void onEdit(Category category);
        void onDelete(Category category);
    }

    public static class Category {
        public final int id;
        public final String name;
        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private final ArrayList<Category> items;
    private final OnItemActionListener listener;

    public CategoryAdapter(ArrayList<Category> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void add(Category c) {
        items.add(c);
        notifyItemInserted(items.size() - 1);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Category c = items.get(position);
        holder.tvName.setText(c.name);
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(c);
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(c);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnEdit;
        ImageButton btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryName);
            //btnEdit = itemView.findViewById(R.id.btnEditCategory);
            //btnDelete = itemView.findViewById(R.id.btnDeleteCategory);
        }
    }
}
