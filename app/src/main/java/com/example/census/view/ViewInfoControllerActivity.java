package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;
import com.example.census.model.Role;

public class ViewInfoControllerActivity extends AppCompatActivity {

    private Role   role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_controller);
    }
}