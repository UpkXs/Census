package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;

public class OnlineInterviewActivity extends AppCompatActivity {

    private Role   role;
    private Action action;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            action = (Action) extras.get("action");
            username = (String) extras.get("username");

            System.out.println("Os1ikyI0 :: role : " + role);
            System.out.println("DO55Q48v :: action : " + action);
            System.out.println("Iq22nZ65 :: username : " + username);
        }
    }
}