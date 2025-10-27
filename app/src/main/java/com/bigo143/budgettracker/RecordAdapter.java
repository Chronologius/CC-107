package com.bigo143.budgettracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.VH> {
    private final List<RecordItem> list;
    public RecordAdapter(List<RecordItem> list) { this.list = list; }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int i) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_record, p, false));
    }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        RecordItem r = list.get(pos);
        h.category.setText(r.getCategory());
        h.date.setText(r.getDate());
        h.amount.setText((r.getAmount()<0? "- ₱":"+ ₱") + Math.abs(r.getAmount()));
        h.amount.setTextColor(r.getAmount()<0 ? h.itemView.getContext().getColor(R.color.expenseValue) : h.itemView.getContext().getColor(R.color.incomeValue));
    }
    @Override public int getItemCount() { return list.size(); }
    static class VH extends RecyclerView.ViewHolder {
        TextView category, date, amount;
        VH(@NonNull View v){ super(v); category = v.findViewById(R.id.tvRecordCategory); date = v.findViewById(R.id.tvRecordDate); amount = v.findViewById(R.id.tvRecordAmount);}
    }
}
