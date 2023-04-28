package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.census.R;

public class OnlineInterviewHouseholdPartActivity extends AppCompatActivity {

    private String username;

    private LinearLayout question;
    private LinearLayout questionHead;
    private LinearLayout questionBody;

    private TextView questionId;
    private TextView questionName;
    private EditText questionAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_household_part);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");

            System.out.println("pkrONW7K :: username : " + username);
        }

        question = findViewById(R.id.question);
        questionHead = findViewById(R.id.questionHead);
        questionBody = findViewById(R.id.questionBody);

        questionId = findViewById(R.id.questionId);
        questionName = findViewById(R.id.questionName);
        questionAnswer = findViewById(R.id.questionAnswer);


    }
}