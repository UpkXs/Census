package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.census.MainActivity;
import com.example.census.R;
import com.example.census.enums.Role;
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

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnChangeData = findViewById(R.id.btnChangeData);
        Button btnLogOut = findViewById(R.id.btnLogOut);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity(view);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDeleteActivity(view);
            }
        });

        btnChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChangeDataActivity(view, Role.CITIZEN); // todo
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ViewInfoAdminActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    public void goToRegisterActivity(View view) {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }

    public void goToDeleteActivity(View view) {
        Intent deleteActivity = new Intent(this, DeleteActivity.class);
        startActivity(deleteActivity);
    }

    public void goToChangeDataActivity(View view, Role role) {
        Intent changeDataActivity = new Intent(this, ChangeDataActivity.class);
        changeDataActivity.putExtra("role", role);
        startActivity(changeDataActivity);
    }
}