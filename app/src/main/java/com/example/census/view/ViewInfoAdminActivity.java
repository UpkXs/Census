package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.census.R;
import com.example.census.model.Role;
import com.example.census.page.ChangeDataActivity;
import com.example.census.page.DeleteActivity;
import com.example.census.auth.RegisterActivity;

public class ViewInfoAdminActivity extends AppCompatActivity {

    private Role   role;
    private  String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_admin);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");
            role = (Role) extras.get("role");
        }

        Button btnRegisterNewStationary = findViewById(R.id.btnRegisterNewStationary);
        Button btnRegisterNewController = findViewById(R.id.btnRegisterNewController);
        Button btnDeleteStationary = findViewById(R.id.btnDeleteStationary);
        Button btnDeleteController = findViewById(R.id.btnDeleteController);
        Button btnChangeUserInfo = findViewById(R.id.btnChangeUserInfo);

        btnRegisterNewStationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity(view, Role.STATIONARY);
            }
        });

        btnRegisterNewController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity(view, Role.CONTROLLER);
            }
        });

        btnDeleteStationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDeleteActivity(view, Role.STATIONARY);
            }
        });

        btnDeleteController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDeleteActivity(view, Role.CONTROLLER);
            }
        });

        btnChangeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChangeDataActivity(view, Role.CITIZEN);
            }
        });
    }

    public void goToRegisterActivity(View view, Role role) {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        registerActivity.putExtra("role", role);
        startActivity(registerActivity);
    }

    public void goToDeleteActivity(View view, Role role) {
        Intent deleteActivity = new Intent(this, DeleteActivity.class);
        deleteActivity.putExtra("role", role);
        startActivity(deleteActivity);
    }

    public void goToChangeDataActivity(View view, Role role) {
        Intent changeDataActivity = new Intent(this, ChangeDataActivity.class);
        changeDataActivity.putExtra("role", role);
        startActivity(changeDataActivity);
    }
}