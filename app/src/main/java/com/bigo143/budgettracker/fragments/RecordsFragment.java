package com.bigo143.budgettracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigo143.budgettracker.R;
import com.bigo143.budgettracker.RecordAdapter;
import com.bigo143.budgettracker.models.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private List<Record> fullList = new ArrayList<>();

    public RecordsFragment() {
        setHasOptionsMenu(true); // enables toolbar menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_records, container, false);

        recyclerView = view.findViewById(R.id.recyclerRecords);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadSampleData();

        adapter = new RecordAdapter(requireContext(), fullList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // --------------------------
    // MENU (Calendar / Filter / Search)
    // --------------------------
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_records, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();  // GOOD — Java allows this

        if (id == R.id.action_calendar) {
            // open calendar modal
            return true;

        } else if (id == R.id.action_filter) {
            // open filter modal
            return true;

        } else if (id == R.id.action_search) {
            // open search UI
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // --------------------------
    // Sample Data
    // --------------------------
    private void loadSampleData() {
        fullList.clear();

        fullList.add(Record.header("Jan 15, 2025"));
        fullList.add(new Record("Food", "Cash", 50.0, Record.TYPE_EXPENSE, "ic_food"));
        fullList.add(new Record("Salary", "Bank", 5000, Record.TYPE_INCOME, "ic_salary"));

        fullList.add(Record.header("Jan 14, 2025"));
        fullList.add(new Record("Transfer", "Bank → Wallet", 500, Record.TYPE_TRANSFER, "ic_transfer"));
    }
}
