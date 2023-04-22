package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.enums.Role;

public class StationaryAndControllerDetailsActivity extends AppCompatActivity {

    private Role role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_and_controller_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("xXSl2S8h :: UserDetailsChangeActivity");
        System.out.println("I14K0jgM :: role : " + role);
        System.out.println("jEYv2XXG :: username" + username);
    }
}