package com.bigo143.budgettracker.loginActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.bigo143.budgettracker.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnCreateAccount.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, SignupActivity.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}
