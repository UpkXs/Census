package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.model.Role;

public class ViewInfoCitizenActivity extends AppCompatActivity {

    private Role   role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_citizen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");

            System.out.println("CbBzxWws :: role : " + role);
            System.out.println("22p97FpC :: username : " + username);
        }
    }
}