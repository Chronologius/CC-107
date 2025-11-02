package com.bigo143.budgettracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotBudgetedAdapter extends RecyclerView.Adapter<NotBudgetedAdapter.VH> {

    public static class CategoryRow {
        public String name;
        public double limit;
        public int iconRes;
        public CategoryRow(String name, double limit, int iconRes) {
            this.name = name; this.limit = limit; this.iconRes = iconRes;
        }
    }

    private final ArrayList<CategoryRow> items = new ArrayList<>();
    private final Context ctx;

    public NotBudgetedAdapter(Context ctx) { this.ctx = ctx; }

    public void addItem(CategoryRow r) { items.add(r); notifyItemInserted(items.size()-1); }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_not_budgeted_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CategoryRow r = items.get(position);
        holder.title.setText(r.name);
        holder.icon.setImageResource(r.iconRes);
        holder.btnSet.setOnClickListener(v -> {
            // show SetBudget dialog
            SetBudgetDialog.show(ctx, r.name, (category, limit) -> {
                r.limit = limit;
                notifyItemChanged(position);
            });
        });

        holder.more.setOnClickListener(v -> {
            PopupMenu pm = new PopupMenu(ctx, holder.more);
            pm.getMenuInflater().inflate(R.menu.popup_menu_budget, pm.getMenu());
            pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                @Override public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.action_change_limit) {
                        SetBudgetDialog.show(ctx, r.name, (category, limit) -> {
                            r.limit = limit; notifyItemChanged(position);
                        });
                        return true;
                    } else if (id == R.id.action_remove_budget) {
                        items.remove(position); notifyItemRemoved(position); notifyItemRangeChanged(position, items.size());
                        return true;
                    }
                    return false;
                }
            });
            pm.show();
        });

        holder.tvLimit.setText(r.limit > 0 ? ("Limit: ₱" + trimZero(r.limit)) : "Not set");
    }

    private String trimZero(double d) {
        if (d == (long)d) return String.format("%d", (long)d);
        return String.format("%.2f", d);
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, tvLimit;
        Button btnSet;
        ImageView icon;
        ImageButton more;
        VH(@NonNull View v) {
            super(v);
            icon = v.findViewById(R.id.imgCategory);
            title = v.findViewById(R.id.tvNotBudgetTitle);
            tvLimit = v.findViewById(R.id.tvNotBudgetLimit);
            btnSet = v.findViewById(R.id.btnSetBudget);
            more = v.findViewById(R.id.btnMore);
        }
    }
}
