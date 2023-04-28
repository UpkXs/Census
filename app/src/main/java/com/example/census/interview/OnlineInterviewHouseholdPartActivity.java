package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.census.R;
import com.example.census.enums.AnswerType;
import com.example.census.model.Question;

import java.util.ArrayList;
import java.util.List;

public class OnlineInterviewHouseholdPartActivity extends AppCompatActivity {

    private String username;

    private LinearLayout question;
    private LinearLayout questionHead;
    private LinearLayout questionBody;

    private TextView questionId;
    private TextView questionName;
    private EditText questionAnswer;

    private List<Question> householdQuestions = new ArrayList(){{
        add(new Question(1, "Registration address:", AnswerType.TEXT));
        add(new Question(2, "Specify the type of room used for accommodation:", AnswerType.NUMBER));
        add(new Question(3, "Specify the year of construction of the residential buildings:", AnswerType.NUMBER));
        add(new Question(4, "Specify the number of floors of residential buildings:", AnswerType.NUMBER));
        add(new Question(5, "Specify the materials of exterior walls of residential buildings:", AnswerType.TEXT));
        add(new Question(6, "What kind of amenities you have in your home (electricity, gas, heating, water):", AnswerType.TEXT));
        add(new Question(7, "Specify the size of the total area:", AnswerType.NUMBER));
        add(new Question(8, "Specify the size of the living area:", AnswerType.NUMBER));
        add(new Question(9, "How many rooms are there in the living quarters (excluding kitchen, bath, toilet, hallway, storage rooms)?", AnswerType.NUMBER));
        add(new Question(10, "Who owns the dwelling in which you live (private, individual, legal entity)?", AnswerType.TEXT));
    }};

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

        for (Question question: householdQuestions) {
            System.out.println("Vv5qc9Lf :: " + question.getId() + " : " + question.getQuestion() + " : " + question.getAnswerType());
        }
    }
}