package com.bigo143.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnSignup;
    private TextView tvLoginRedirect;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Find views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        // Handle sign up button click
        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirm = etConfirmPassword.getText().toString().trim();

            // Input validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if user already exists
            if (dbHelper.userExists(email)) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register new user
            boolean success = dbHelper.registerUser(username, email, password);
            if (success) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sign up failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        });

        // Redirect to Login page
        tvLoginRedirect.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }
}
