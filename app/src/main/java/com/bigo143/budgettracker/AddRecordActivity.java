package com.bigo143.budgettracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecordActivity extends AppCompatActivity {

    private TextView tvResult;
    private StringBuilder expr = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        tvResult = findViewById(R.id.tvResult);

        // wire keypad buttons (by tag set in xml)
        int[] ids = new int[] {
                R.id.key_AC, R.id.key_DEL, R.id.key_div, R.id.key_mul,
                R.id.key_7, R.id.key_8, R.id.key_9, R.id.key_minus,
                R.id.key_4, R.id.key_5, R.id.key_6, R.id.key_plus,
                R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_equal,
                R.id.key_00, R.id.key_0, R.id.key_dot
        };

        for (int id : ids) {
            View v = findViewById(id);
            if (v instanceof Button) {
                ((Button) v).setOnClickListener(this::onKey);
            }
        }
    }

    private void onKey(View v) {
        String txt = ((Button) v).getText().toString();
        switch (txt) {
            case "AC":
                expr.setLength(0);
                break;
            case "⌫":
                if (expr.length() > 0) expr.deleteCharAt(expr.length()-1);
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
        tvResult.setText(expr.toString());
    }

    private String trim(double d) {
        if (d== (long)d) return String.format("%d", (long)d);
        return String.format("%.2f", d);
    }
}
