package com.bigo143.budgettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category c = categories.get(position);
        holder.editCategoryName.setText(c.getName());
        holder.editCategoryLimit.setText(String.valueOf(c.getLimit()));
        holder.editCategorySpent.setText(String.valueOf(c.getSpent()));
        holder.imgCategory.setImageResource(c.getImageRes());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText editCategoryName, editCategoryLimit, editCategorySpent;
        ImageView imgCategory;
        ViewHolder(View itemView) {
            super(itemView);
            editCategoryName = itemView.findViewById(R.id.editCategoryName);
            editCategoryLimit = itemView.findViewById(R.id.editCategoryLimit);
            editCategorySpent = itemView.findViewById(R.id.editCategorySpent);
            imgCategory = itemView.findViewById(R.id.imgCategory);
        }
    }
}
