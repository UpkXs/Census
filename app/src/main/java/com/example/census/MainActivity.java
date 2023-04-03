package com.example.census;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.census.model.Role;

public class MainActivity extends AppCompatActivity {

    int admin;
    int stationary;
    int controller;
    int citizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button adminBtn = findViewById(R.id.adminBtn);
        admin = adminBtn.getId();

        Button stationaryBtn = findViewById(R.id.stationaryBtn);
        stationary = stationaryBtn.getId();

        Button controllerBtn = findViewById(R.id.controllerBtn);
        controller = controllerBtn.getId();

        Button citizenBtn = findViewById(R.id.citizenBtn);
        citizen = citizenBtn.getId();

    }

    @SuppressLint("NonConstantResourceId")
    public void goToLoginActivity(View view) {
        int roleId = view.getId();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        Intent citizenLoginActivity = new Intent(this, CitizenLoginActivity.class);

        switch (roleId) {
            case R.id.adminBtn:
                loginActivity.putExtra("role", Role.ADMIN);
                startActivity(loginActivity);
                break;
            case R.id.stationaryBtn:
                loginActivity.putExtra("role", Role.STATIONARY);
                startActivity(loginActivity);
                break;
            case R.id.controllerBtn:
                loginActivity.putExtra("role", Role.CONTROLLER);
                startActivity(loginActivity);
                break;
            case R.id.citizenBtn:
                startActivity(citizenLoginActivity);
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }
}