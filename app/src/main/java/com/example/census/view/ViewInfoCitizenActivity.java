package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;

public class ViewInfoCitizenActivity extends AppCompatActivity {

    private Role   role;
    private Action action;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_citizen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            action = (Action) extras.get("action");
            username = (String) extras.get("username");

            System.out.println("CbBzxWws :: role : " + role);
            System.out.println("cAhzA7DS :: action : " + action);
            System.out.println("22p97FpC :: username : " + username);
        }
    }

    // todo aro 5RO2IT3y implement logic of view yourself info citizen

}