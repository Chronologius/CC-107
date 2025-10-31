package com.bigo143.budgettracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddRecordActivity extends AppCompatActivity {

    private TextView tvResult, tvDate, tvTime;
    private StringBuilder expr = new StringBuilder();
    private Button btnIncome, btnExpense, btnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        tvResult = findViewById(R.id.tvResult);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        btnTransfer = findViewById(R.id.btnTransfer);

        setupModeButtons();
        setupDateTimePickers();

        int[] ids = {
                R.id.key_AC, R.id.key_DEL, R.id.key_div, R.id.key_mul,
                R.id.key_7, R.id.key_8, R.id.key_9, R.id.key_minus,
                R.id.key_4, R.id.key_5, R.id.key_6, R.id.key_plus,
                R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_equal,
                R.id.key_0, R.id.key_dot
        };

        for (int id : ids) {
            View v = findViewById(id);
            if (v instanceof Button) ((Button) v).setOnClickListener(this::onKey);
        }
    }

    private void onKey(View v) {
        String txt = ((Button) v).getText().toString();
        switch (txt) {
            case "Ac":
                expr.setLength(0);
                break;
            case "⌫":
                if (expr.length() > 0) expr.deleteCharAt(expr.length() - 1);
                break;
            case "=":
                try {
                    double res = ExpressionEvaluator.eval(expr.toString());
                    expr.setLength(0);
                    expr.append(trim(res));
                } catch (Exception e) {
                    expr.setLength(0);
                    expr.append("Error");
                }
                break;
            default:
                expr.append(txt);
        }
        tvResult.setText("= " + expr);
    }

    private void setupModeButtons() {
        View.OnClickListener listener = v -> {
            btnIncome.setBackgroundTintList(getColorStateList(R.color.primaryBlue));
            btnExpense.setBackgroundTintList(getColorStateList(R.color.primaryBlue));
            btnTransfer.setBackgroundTintList(getColorStateList(R.color.primaryBlue));

            ((Button) v).setBackgroundTintList(getColorStateList(R.color.primaryBlue));
        };

        btnIncome.setOnClickListener(listener);
        btnExpense.setOnClickListener(listener);
        btnTransfer.setOnClickListener(listener);
    }

    private void setupDateTimePickers() {
        Calendar cal = Calendar.getInstance();

        tvDate.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                tvDate.setText(String.format("%tB %d, %d", cal, day, year));
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        tvTime.setOnClickListener(v -> {
            new TimePickerDialog(this, (view, hour, minute) -> {
                String ampm = (hour >= 12) ? "PM" : "AM";
                int hr = (hour % 12 == 0) ? 12 : hour % 12;
                tvTime.setText(String.format("%d:%02d %s", hr, minute, ampm));
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
        });
    }

    private String trim(double d) {
        if (d == (long) d) return String.format("%d", (long) d);
        return String.format("%.2f", d);
    }
}
