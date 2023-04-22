package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.census.MainActivity;
import com.example.census.R;
import com.example.census.enums.Role;
import com.example.census.page.ChangeDataStationaryActivity;

public class ViewInfoStationaryActivity extends AppCompatActivity {

    private Role   role;
    private String username;

    private Button btnChangeUserInfo;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_stationary);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        String roleAndUsername$ = role.label + ": " + username;
        TextView roleAndUsername = findViewById(R.id.roleAndUsername);
        roleAndUsername.setText(roleAndUsername$);

        btnChangeUserInfo = findViewById(R.id.btnChangeUserInfo);
        btnLogOut = findViewById(R.id.btnLogOut);

        btnChangeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeDataStationaryActivity = new Intent(getApplicationContext(), ChangeDataStationaryActivity.class);
                changeDataStationaryActivity.putExtra("role", role);
                changeDataStationaryActivity.putExtra("username", username);
                startActivity(changeDataStationaryActivity);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ViewInfoStationaryActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}