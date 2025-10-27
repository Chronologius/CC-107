package com.bigo143.budgettracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_records);

        RecyclerView r = findViewById(R.id.recyclerRecords);
        r.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<RecordItem> data = new ArrayList<>();
        data.add(new RecordItem("Food","Oct 23, 2025",-150.0));
        data.add(new RecordItem("Salary","Oct 20, 2025",5000.0));
        RecordAdapter adapter = new RecordAdapter(data);
        r.setAdapter(adapter);
    }
}
