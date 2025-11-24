package com.bigo143.budgettracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class calcu_add extends AppCompatActivity {

    private TextView resultTextView;
    private EditText notesEditText;
    private AutoCompleteTextView accountDropdown;
    private AutoCompleteTextView categoryDropdown;
    private TextInputLayout accountDropdownLayout;
    private TextInputLayout categoryDropdownLayout;

    private TextView incomeTextView;
    private TextView transferTextView;
    private TextView expenseTextView;

    private TextView datePickerTextView;
    private TextView timePickerTextView;

    private StringBuilder expressionBuilder = new StringBuilder();
    private Calendar calendar = Calendar.getInstance();

    private enum TransactionType { INCOME, TRANSFER, EXPENSE }
    private TransactionType currentTransactionType = TransactionType.EXPENSE;

    private DatabaseHelper dbHelper;
    private String loggedInUser ; // Replace with real logged-in user

    private List<String> accountList = new ArrayList<>();
    private List<String> incomeCategoryList = new ArrayList<>();
    private List<String> expenseCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.calcu_add_expenseincome);

        SharedPreferences prefs = calcu_add.this.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        loggedInUser = prefs.getString("logged_in_user", null);

        // --- Initialize Database ---
        dbHelper = new DatabaseHelper(this);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Initialize views ---
        resultTextView = findViewById(R.id.result);
        notesEditText = findViewById(R.id.notes_edittext);
        accountDropdown = findViewById(R.id.account_dropdown);
        accountDropdownLayout = findViewById(R.id.account_dropdown_layout);
        categoryDropdown = findViewById(R.id.category_dropdown);
        categoryDropdownLayout = findViewById(R.id.category_dropdown_layout);
        incomeTextView = findViewById(R.id.text_income);
        transferTextView = findViewById(R.id.text_transfer);
        expenseTextView = findViewById(R.id.text_Expense);
        datePickerTextView = findViewById(R.id.date_picker_text);
        timePickerTextView = findViewById(R.id.time_picker_text);

        // --- Load data from database ---
        loadAccountsFromDB();
        loadCategoriesFromDB();

        // --- Setup dropdowns ---
        setupDropdown(accountDropdown, accountList);
        setupDropdown(categoryDropdown, expenseCategoryList); // default is expense

        // --- Setup transaction type ---
        setupTransactionTypeSelection();

        // --- Setup calculator buttons ---
        setupCalculatorButtons();

        // --- Save & Cancel buttons ---
        setupSaveCancelButtons();

        // --- Date & Time pickers ---
        setupDateTimePickers();

        // --- Clear error on input ---
        addTextWatchers();
    }

    private void loadAccountsFromDB() {
        accountList.clear();
        // Assuming DatabaseHelper has getAccounts(String username) returning Cursor with "name"
        Cursor cursor = dbHelper.getAccounts(loggedInUser);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                accountList.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void loadCategoriesFromDB() {
        incomeCategoryList.clear();
        expenseCategoryList.clear();

        Cursor incomeCursor = dbHelper.getCategoriesByType(loggedInUser, "income");
        if (incomeCursor != null && incomeCursor.moveToFirst()) {
            do {
                incomeCategoryList.add(incomeCursor.getString(incomeCursor.getColumnIndexOrThrow("name")));
            } while (incomeCursor.moveToNext());
            incomeCursor.close();
        }

        Cursor expenseCursor = dbHelper.getCategoriesByType(loggedInUser, "expense");
        if (expenseCursor != null && expenseCursor.moveToFirst()) {
            do {
                expenseCategoryList.add(expenseCursor.getString(expenseCursor.getColumnIndexOrThrow("name")));
            } while (expenseCursor.moveToNext());
            expenseCursor.close();
        }
    }

    private void setupDropdown(AutoCompleteTextView dropdown, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items);
        dropdown.setAdapter(adapter);
    }

    private void setupTransactionTypeSelection() {
        updateTransactionTypeUI();

        incomeTextView.setOnClickListener(v -> {
            currentTransactionType = TransactionType.INCOME;
            updateTransactionTypeUI();
        });

        transferTextView.setOnClickListener(v -> {
            currentTransactionType = TransactionType.TRANSFER;
            updateTransactionTypeUI();
        });

        expenseTextView.setOnClickListener(v -> {
            currentTransactionType = TransactionType.EXPENSE;
            updateTransactionTypeUI();
        });
    }

    private void updateTransactionTypeUI() {
        incomeTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        transferTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        expenseTextView.setBackgroundColor(android.graphics.Color.TRANSPARENT);

        int highlightColor = ContextCompat.getColor(this, R.color.third);

        switch (currentTransactionType) {
            case INCOME:
                incomeTextView.setBackgroundColor(highlightColor);
                categoryDropdownLayout.setHint("Select Category");
                setupDropdown(categoryDropdown, incomeCategoryList);
                setupDropdown(accountDropdown, accountList);
                break;
            case TRANSFER:
                transferTextView.setBackgroundColor(highlightColor);
                categoryDropdownLayout.setHint("To Account");
                setupDropdown(categoryDropdown, accountList);
                setupDropdown(accountDropdown, accountList);
                break;
            case EXPENSE:
                expenseTextView.setBackgroundColor(highlightColor);
                categoryDropdownLayout.setHint("Select Category");
                setupDropdown(categoryDropdown, expenseCategoryList);
                setupDropdown(accountDropdown, accountList);
                break;
        }
    }

    private void setupCalculatorButtons() {
        int[] numberButtonIds = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9, R.id.button_00, R.id.button_dot};
        for (int id : numberButtonIds) findViewById(id).setOnClickListener(this::onNumberClick);

        int[] operatorButtonIds = {R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide};
        for (int id : operatorButtonIds) findViewById(id).setOnClickListener(this::onOperatorClick);

        findViewById(R.id.button_equal).setOnClickListener(this::onEqualsClick);
        findViewById(R.id.clear_button).setOnClickListener(this::onClearClick);
        findViewById(R.id.back_button).setOnClickListener(this::onBackClick);
    }

    private void setupSaveCancelButtons() {
        findViewById(R.id.save_button).setOnClickListener(v -> saveTransaction());
        findViewById(R.id.cancel_button).setOnClickListener(v -> finish());
    }

    private void saveTransaction() {
        if (!validateInput()) return;

        double amount = Double.parseDouble(resultTextView.getText().toString());
        String fromAccount = accountDropdown.getText().toString();
        String categoryOrTo = categoryDropdown.getText().toString();
        String note = notesEditText.getText().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.getTime());

        boolean success = false;

        switch (currentTransactionType) {
            case INCOME:
                int incomeCatId = dbHelper.getCategoryIdByName(loggedInUser, categoryOrTo, "income");
                success = dbHelper.insertRecord(loggedInUser, incomeCatId, "income", amount, date, note);
                break;
            case EXPENSE:
                int expenseCatId = dbHelper.getCategoryIdByName(loggedInUser, categoryOrTo, "expense");
                success = dbHelper.insertRecord(loggedInUser, expenseCatId, "expense", amount, date, note);
                break;
            case TRANSFER:
                int fromAccountId = dbHelper.getAccountIdByName(loggedInUser, fromAccount);
                int toAccountId = dbHelper.getAccountIdByName(loggedInUser, categoryOrTo);
                success = dbHelper.insertTransfer(loggedInUser, fromAccountId, toAccountId, amount, date, note);
                break;
        }

        if (success) {
            Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving transaction", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        String fromAccount = accountDropdown.getText().toString();
        String to = categoryDropdown.getText().toString();
        String amount = resultTextView.getText().toString();

        if (fromAccount.isEmpty()) {
            accountDropdownLayout.setError("Select account");
            return false;
        }

        if (currentTransactionType == TransactionType.TRANSFER) {
            if (to.isEmpty()) {
                categoryDropdownLayout.setError("Select target account");
                return false;
            }
            if (fromAccount.equals(to)) {
                categoryDropdownLayout.setError("Cannot transfer to same account");
                return false;
            }
        } else {
            if (to.isEmpty()) {
                categoryDropdownLayout.setError("Select category");
                return false;
            }
        }

        if (Double.parseDouble(amount) == 0) {
            Toast.makeText(this, "Amount cannot be zero", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void addTextWatchers() {
        accountDropdown.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { accountDropdownLayout.setError(null); }
            public void afterTextChanged(Editable s) {}
        });

        categoryDropdown.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { categoryDropdownLayout.setError(null); }
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupDateTimePickers() {
        updateDateTimeLabels();

        datePickerTextView.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateTimeLabels();
            };
            new DatePickerDialog(this, dateSetListener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        timePickerTextView.setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener timeSetListener = (view, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                updateDateTimeLabels();
            };
            new TimePickerDialog(this, timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        });
    }

    private void updateDateTimeLabels() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        datePickerTextView.setText(dateFormat.format(calendar.getTime()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        timePickerTextView.setText(timeFormat.format(calendar.getTime()));
    }

    // --- Calculator logic ---
    public void onNumberClick(View view) {
        Button button = (Button) view;
        expressionBuilder.append(button.getText().toString());
        updateResult();
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String operator = button.getText().toString();

        if (expressionBuilder.length() > 0) {
            char lastChar = expressionBuilder.charAt(expressionBuilder.length() - 1);
            if (isOperator(lastChar)) expressionBuilder.setCharAt(expressionBuilder.length() - 1, operator.charAt(0));
            else expressionBuilder.append(operator);
        } else if (operator.equals("-")) expressionBuilder.append(operator);

        updateResult();
    }

    public void onEqualsClick(View view) {
        String expression = expressionBuilder.toString();
        if (expression.isEmpty()) return;

        try {
            double result = evaluate(expression);
            String resultString = result == (long) result ? String.format("%d", (long) result) : String.format("%.2f", result);
            resultTextView.setText(resultString);
            expressionBuilder.setLength(0);
            expressionBuilder.append(resultString);
        } catch (Exception e) {
            resultTextView.setText("Error");
            expressionBuilder.setLength(0);
        }
    }

    public double evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.'))
                    sbuf.append(tokens[i++]);
                values.push(Double.parseDouble(sbuf.toString()));
                i--;
            } else if (isOperator(tokens[i])) {
                if (i == 0 && tokens[i] == '-') { // negative first number
                    StringBuilder sbuf = new StringBuilder();
                    sbuf.append(tokens[i++]);
                    while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.'))
                        sbuf.append(tokens[i++]);
                    values.push(Double.parseDouble(sbuf.toString()));
                    i--;
                    continue;
                }
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    private boolean hasPrecedence(char op1, char op2) { return (op2 != '(' && op2 != ')') && ((op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-')); }
    private double applyOp(char op, double b, double a) { switch (op) { case '+': return a+b; case '-': return a-b; case '*': return a*b; case '/': if(b==0) throw new UnsupportedOperationException("Cannot divide by zero"); return a/b; } return 0; }
    private void onClearClick(View view) { expressionBuilder.setLength(0); updateResult(); }
    private void onBackClick(View view) { if(expressionBuilder.length()>0) expressionBuilder.deleteCharAt(expressionBuilder.length()-1); updateResult(); }
    private void updateResult() { resultTextView.setText(expressionBuilder.length()==0?"0":expressionBuilder.toString()); }
    private boolean isOperator(char c) { return c=='+'||c=='-'||c=='*'||c=='/'; }

}
