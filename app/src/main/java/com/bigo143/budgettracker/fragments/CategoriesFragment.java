package com.bigo143.budgettracker.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bigo143.budgettracker.R;
import com.bigo143.budgettracker.models.CategoryModel;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    Button btnIncome, btnAccount, btnExpense;
    View container;

    ArrayList<CategoryModel> incomeList = new ArrayList<>();
    ArrayList<CategoryModel> accountList = new ArrayList<>();
    ArrayList<CategoryModel> expenseList = new ArrayList<>();

    public CategoriesFragment(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup containerGroup,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_categories, containerGroup, false);

        btnIncome = v.findViewById(R.id.btnIncome);
        btnAccount = v.findViewById(R.id.btnAccount);
        btnExpense = v.findViewById(R.id.btnExpense);
        container = v.findViewById(R.id.categoryContentContainer);

        // sample data
        incomeList.add(new CategoryModel("Salary", R.drawable.ic_salary));
        incomeList.add(new CategoryModel("Grants", R.drawable.ic_income));

        accountList.add(new CategoryModel("Cash", R.drawable.ic_wallet, "Balance: ₱885.00", 885));
        accountList.add(new CategoryModel("Card", R.drawable.ic_bank, "Balance: ₱0.00", 0));

        expenseList.add(new CategoryModel("Transport", R.drawable.ic_transport));
        expenseList.add(new CategoryModel("Food", R.drawable.ic_food));

        // default show account page
        showAccountFragment();

        btnIncome.setOnClickListener(v1 -> showIncomeFragment());
        btnAccount.setOnClickListener(v1 -> showAccountFragment());
        btnExpense.setOnClickListener(v1 -> showExpenseFragment());

        // Add button from header - open add dialog
        v.findViewById(R.id.btnOpenCategoryAdd).setOnClickListener(v1 -> {
            showAddDialog();
        });

        return v;
    }

    private void showIncomeFragment() {
        Fragment f = new IncomeFragment(incomeList);
        replaceContentFragment(f);
    }

    private void showAccountFragment() {
        Fragment f = new AccountFragment(accountList);
        replaceContentFragment(f);
    }

    private void showExpenseFragment() {
        Fragment f = new ExpenseFragment(expenseList);
        replaceContentFragment(f);
    }

    private void replaceContentFragment(Fragment fragment) {
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.categoryContentContainer, fragment);
        t.commit();
    }

    private void showAddDialog() {
        final Dialog d = new Dialog(requireContext());
        d.setContentView(R.layout.dialog_add_category);
        d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button save = d.findViewById(R.id.btnSaveCategory);
        save.setOnClickListener(v -> {
            // UI-only for now
            d.dismiss();
        });

        d.show();
    }
}
