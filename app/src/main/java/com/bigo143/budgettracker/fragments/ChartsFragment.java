package com.bigo143.budgettracker.fragments;

import android.graphics.Color;
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

import com.bigo143.budgettracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class ChartsFragment extends Fragment {

    public ChartsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true); // enables toolbar menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        setupPie(view);
        setupBar(view);

        return view;
    }

    // --------------------------
    // MENU (Calendar / Filter / Search)
    // --------------------------
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_normal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();  // GOOD — Java allows this

//        if (id == R.id.action_calendar) {
//            // open calendar modal
//            return true;
//
//        } else if (id == R.id.action_filter) {
//            // open filter modal
//            return true;         } else

        if (id == R.id.action_search) {
            // open search UI
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void setupPie(View view) {
        PieChart pie = view.findViewById(R.id.pieChart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Food"));
        entries.add(new PieEntry(30f, "Transport"));
        entries.add(new PieEntry(20f, "Utilities"));
        entries.add(new PieEntry(10f, "Others"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        pie.setData(data);
        pie.getDescription().setEnabled(false);
        pie.setCenterText("Expenses");
        pie.invalidate();
    }

    private void setupBar(View view) {
        BarChart bar = view.findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5000f));
        entries.add(new BarEntry(1, 2500f));

        BarDataSet dataSet = new BarDataSet(entries, "Income vs Expense");
        dataSet.setColors(new int[]{
                Color.parseColor("#4CAF50"), // Green for income
                Color.parseColor("#F44336")  // Red for expense
        });

        BarData data = new BarData(dataSet);
        bar.setData(data);
        bar.getDescription().setEnabled(false);
        bar.invalidate();
    }
}
