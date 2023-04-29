package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.census.R;

public class OnlineInterviewCensusFormActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_census_form);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");

            System.out.println("pkrONW7K :: username : " + username);
        }
    }
}