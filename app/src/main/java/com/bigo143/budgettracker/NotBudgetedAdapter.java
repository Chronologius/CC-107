package com.bigo143.budgettracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.models.CategoryModel;

import java.util.ArrayList;

public class NotBudgetedAdapter extends RecyclerView.Adapter<NotBudgetedAdapter.ViewHolder> {

    ArrayList<CategoryModel> list;
    Context context;

    public NotBudgetedAdapter(ArrayList<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_not_budgeted_category, parent, false); // <-- FIXED
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model = list.get(position);

        holder.title.setText(model.getName());
        holder.icon.setImageResource(model.getIcon());
        holder.limit.setText("Not set"); // optional
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title, limit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.imgCategory);
            title = itemView.findViewById(R.id.tvNotBudgetTitle);
            limit = itemView.findViewById(R.id.tvNotBudgetLimit);
        }
    }
}
