package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.census.MainActivity;
import com.example.census.R;
import com.example.census.model.Role;
import com.example.census.view.ViewInfoAdminActivity;

public class SelectActivity extends AppCompatActivity {

    private Role role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
        }

        Button btnViewUserData = findViewById(R.id.btnViewUserData);
        Button btnChangeDataOrOnlineInterview = findViewById(R.id.btnChangeDataOrOnlineInterview);
        Button btnLogOut = findViewById(R.id.btnLogOut);

        if (role.label.equals("Stationary") || role.label.equals("Controller")) {
            btnChangeDataOrOnlineInterview.setText("Change User Data");
        } else if (role.label.equals("Citizen")) {
            btnChangeDataOrOnlineInterview.setText("Online Interview");
        }

        btnViewUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectActivity.this, "Selected: View User Info", Toast.LENGTH_SHORT).show(); // todo
            }
        });

        btnChangeDataOrOnlineInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role.label.equals("Stationary") || role.label.equals("Controller")) {
                    Toast.makeText(SelectActivity.this, "Selected: Change User Data", Toast.LENGTH_SHORT).show(); // todo
                } else if (role.label.equals("Citizen")) {
                    Toast.makeText(SelectActivity.this, "Selected: Online Interview", Toast.LENGTH_SHORT).show(); // todo
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}