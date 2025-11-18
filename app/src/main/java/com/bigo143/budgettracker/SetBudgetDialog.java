//package com.bigo143.budgettracker;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class SetBudgetDialog {
//
//    public interface OnBudgetSetListener {
//        void onBudgetSet(String category, double limit);
//    }
//
//    public static void show(Context ctx, String categoryName, OnBudgetSetListener listener) {
//        Dialog d = new Dialog(ctx);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.setContentView(R.layout.dialog_set_budget);
//        TextView tvCategory = d.findViewById(R.id.tvDialogCategoryName);
//        EditText input = d.findViewById(R.id.editLimitAmount);
//        Button btnSave = d.findViewById(R.id.btnSaveBudget);
//        Button btnCancel = d.findViewById(R.id.btnCancelBudget);
//        ImageButton btnClose = d.findViewById(R.id.btnClose);
//
//        tvCategory.setText(categoryName);
//        btnSave.setOnClickListener(v -> {
//            String s = input.getText().toString().trim();
//            if (s.isEmpty()) { Toast.makeText(ctx, "Enter a number", Toast.LENGTH_SHORT).show(); return; }
//            try {
//                double val = Double.parseDouble(s);
//                listener.onBudgetSet(categoryName, val);
//                d.dismiss();
//            } catch (NumberFormatException ex) {
//                Toast.makeText(ctx, "Invalid number", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnCancel.setOnClickListener(v -> d.dismiss());
//        btnClose.setOnClickListener(v -> d.dismiss());
//        d.show();
//    }
//}
