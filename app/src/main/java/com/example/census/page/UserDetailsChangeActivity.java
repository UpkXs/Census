package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.enums.Role;

public class UserDetailsChangeActivity extends AppCompatActivity {

    private Role role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_change);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("iVUnt857 :: UserDetailsChangeActivity");
        System.out.println("d0AQzNR4 :: role : " + role);
        System.out.println("38X1i5fa :: username " + username);
    }
}