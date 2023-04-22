package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.enums.Role;

public class UserDetailsActivity extends AppCompatActivity {

    private Role role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("3s1RmrO3 :: UserDetailsActivity");
        System.out.println("s882d7Sj :: role : " + role);
        System.out.println("f9rz172F :: username" + username);
    }
}