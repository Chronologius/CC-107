package com.bigo143.budgettracker.MgaMatatanggalNaFiles;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bigo143.budgettracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_charts);
        setupPie();
        setupBar();
    }

    private void setupPie() {
        PieChart pie = findViewById(R.id.pieChart);
        ArrayList<PieEntry> e = new ArrayList<>();
        e.add(new PieEntry(40f,"Food"));
        e.add(new PieEntry(30f,"Transport"));
        e.add(new PieEntry(20f,"Utilities"));
        e.add(new PieEntry(10f,"Others"));
        PieDataSet ds = new PieDataSet(e,"");
        ds.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData d = new PieData(ds);
        pie.setData(d);
        pie.getDescription().setEnabled(false);
        pie.setCenterText("Expenses");
        pie.invalidate();
    }

    private void setupBar() {
        BarChart bar = findViewById(R.id.barChart);
        ArrayList<BarEntry> e = new ArrayList<>();
        e.add(new BarEntry(0, 5000f));
        e.add(new BarEntry(1, 2500f));
        BarDataSet ds = new BarDataSet(e, "Income vs Expense");
        ds.setColors(new int[]{Color.parseColor("#4CAF50"), Color.parseColor("#F44336")});
        BarData d = new BarData(ds);
        bar.setData(d);
        bar.getDescription().setEnabled(false);
        bar.invalidate();
    }
}
