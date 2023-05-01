package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.AnswerType;
import com.example.census.enums.Role;
import com.example.census.model.CensusForm;
import com.example.census.model.Question;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoCitizenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnlineInterviewCensusFormActivity extends AppCompatActivity {

    private String username;
    private long citizenTIN;

    private MyDatabaseHelper myDB;

    private Button btnFinishOnlineInterview;

    private LinearLayout questionLayout;
    private LinearLayout questionHead;
    private LinearLayout questionBody;

    private TextView questionId;
    private TextView questionName;

    private List<Question> censusFormQuestions = new ArrayList(){{
        add(new Question(1, "Age:", AnswerType.NUMBER, ""));
        add(new Question(2, "Phone number:", AnswerType.NUMBER, ""));
        add(new Question(3, "Sex (female, male):", AnswerType.TEXT, ""));
        add(new Question(4, "Date of birth:", AnswerType.TEXT, ""));
        add(new Question(5, "Indicate your attitude toward the respondent:", AnswerType.TEXT, ""));
        add(new Question(6, "State your nationality:", AnswerType.TEXT, ""));
        add(new Question(7, "State your location at the time of enumeration:", AnswerType.TEXT, ""));
        add(new Question(8, "State your nationality:", AnswerType.TEXT, ""));
        add(new Question(9, "Specify your native language:", AnswerType.TEXT, ""));
        add(new Question(10, "What languages do you know?", AnswerType.TEXT, ""));
        add(new Question(11, "Specify your level of educational attainment:", AnswerType.TEXT, ""));
        add(new Question(12, "How long have you been a citizen of a place of permanent residence?", AnswerType.NUMBER, ""));
        add(new Question(13, "Have you lived alone or more in other countries (Yes = 1 or No = 0)?", AnswerType.NUMBER, ""));
        add(new Question(14, "Specify your marital status:", AnswerType.TEXT, ""));
        add(new Question(15, "Indicate the date of marriage (if you are not married, put 0):", AnswerType.NUMBER, ""));
        add(new Question(16, "How many times have you been married?", AnswerType.NUMBER, ""));
        add(new Question(17, "Indicate the period of work before the national census of RK:", AnswerType.NUMBER, ""));
        add(new Question(18, "What field do you work in?", AnswerType.TEXT, ""));
        add(new Question(19, "Specify the location of your work:", AnswerType.TEXT, ""));
        add(new Question(20, "Did you have any other additional work besides your main job?", AnswerType.TEXT, ""));
        add(new Question(21, "What are your sources of livelihood (pension, scholarship, allowance, individual entrepreneur)?", AnswerType.TEXT, ""));
    }};

    private HashMap<Integer, EditText> answers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_census_form);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");
            citizenTIN = (long) extras.get("citizenTIN");

            System.out.println("2S8mw3u3 :: username : " + username);
            System.out.println("b8wD4pBr :: citizenTIN : " + citizenTIN);
        }

        myDB = new MyDatabaseHelper(OnlineInterviewCensusFormActivity.this);

        LinearLayout questionsLayout = new LinearLayout(this);
        questionsLayout.setOrientation(LinearLayout.VERTICAL);

        // Get a reference to the parent layout
        LinearLayout parentLayout = findViewById(R.id.parentLayout);

        // Iterate through the questions and inflate the layout for each one
        for (Question question : censusFormQuestions) {
            // Inflate the layout
            View questionView = LayoutInflater.from(this).inflate(R.layout.activity_question_layout, null);

            questionLayout = findViewById(R.id.questionLayout);
            questionHead = findViewById(R.id.questionHead);
            questionBody = findViewById(R.id.questionBody);

            // Set the question ID and name
            questionId = questionView.findViewById(R.id.questionId);
            questionId.setText(question.getId() + ".");

            questionName = questionView.findViewById(R.id.questionName);
            questionName.setText(question.getQuestion());

            // Add the question view to the parent layout
            questionsLayout.addView(questionView);

            EditText questionAnswer = questionView.findViewById(R.id.questionAnswer);
            questionAnswer.setId(question.getId());
            if (question.getAnswerType().label.equals(AnswerType.NUMBER.label)) {
                questionAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                questionAnswer.setHint("enter answer only numbers");
            } else if (question.getAnswerType().label.equals(AnswerType.TEXT.label)) {
                questionAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            answers.put(question.getId(), questionAnswer);
        }

        parentLayout.addView(questionsLayout);

        btnFinishOnlineInterview = findViewById(R.id.btnFinishOnlineInterview);

        parentLayout.removeView(btnFinishOnlineInterview);

        parentLayout.addView(btnFinishOnlineInterview);

        btnFinishOnlineInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOnlineInterview(view);
            }
        });
    }

    private void finishOnlineInterview(View view) {

        boolean allEmpty = false;

        // check if all values are empty
        for (EditText value : answers.values()) {
            if (value.getText().toString().isEmpty()) {
                allEmpty = true;
                break;
            }
        }

        if (allEmpty) {
            System.out.println("9f9C94Ij :: All values are empty. Please answer to all question.");
            toastShow("All values are empty. Please answer to all question.");
            return;
        }

        for (int i = 1; i < answers.size() + 1; i++) {
            System.out.println("bgD2si10 :: answers.get(i) = " + answers.get(i));
            System.out.println("6849ptDG :: answers.get(i).getId() = " + answers.get(i).getId());
            System.out.println("RJmS03z8 :: answers.get(i).getText() = " + answers.get(i).getText());
        }

        CensusForm censusForm = new CensusForm();

        censusForm.setCitizen_tin(citizenTIN);
        censusForm.setAge(Integer.parseInt(answers.get(censusFormQuestions.get(0).getId()).getText().toString().trim()));
        censusForm.setNumber(Long.parseLong(answers.get(censusFormQuestions.get(1).getId()).getText().toString().trim()));
        censusForm.setSex(answers.get(censusFormQuestions.get(2).getId()).getText().toString().trim());
        censusForm.setBirth(answers.get(censusFormQuestions.get(3).getId()).getText().toString().trim());
        censusForm.setOwner_rel(answers.get(censusFormQuestions.get(4).getId()).getText().toString().trim());
        censusForm.setCitizenship(answers.get(censusFormQuestions.get(5).getId()).getText().toString().trim());
        censusForm.setLocation(answers.get(censusFormQuestions.get(6).getId()).getText().toString().trim());
        censusForm.setNation(answers.get(censusFormQuestions.get(7).getId()).getText().toString().trim());
        censusForm.setLanguage(answers.get(censusFormQuestions.get(8).getId()).getText().toString().trim());
        censusForm.setO_language(answers.get(censusFormQuestions.get(9).getId()).getText().toString().trim());
        censusForm.setEducation(answers.get(censusFormQuestions.get(10).getId()).getText().toString().trim());
        censusForm.setLive_period(Integer.parseInt(answers.get(censusFormQuestions.get(11).getId()).getText().toString().trim()));
        censusForm.setO_live_period(Integer.parseInt(answers.get(censusFormQuestions.get(12).getId()).getText().toString().trim()));
        censusForm.setStatus(answers.get(censusFormQuestions.get(13).getId()).getText().toString().trim());
        censusForm.setMarriage_year(Integer.parseInt(answers.get(censusFormQuestions.get(14).getId()).getText().toString().trim()));
        censusForm.setMarriage_sum(Integer.parseInt(answers.get(censusFormQuestions.get(15).getId()).getText().toString().trim()));
        censusForm.setJob_period(Integer.parseInt(answers.get(censusFormQuestions.get(16).getId()).getText().toString().trim()));
        censusForm.setJob_sphere(answers.get(censusFormQuestions.get(17).getId()).getText().toString().trim());
        censusForm.setJob_location(answers.get(censusFormQuestions.get(18).getId()).getText().toString().trim());
        censusForm.setParttime(answers.get(censusFormQuestions.get(19).getId()).getText().toString().trim());
        censusForm.setIncome_sum_type(answers.get(censusFormQuestions.get(20).getId()).getText().toString().trim());

        myDB.addCensusForm(censusForm);

        Intent viewInfoCitizenActivity = new Intent(OnlineInterviewCensusFormActivity.this, ViewInfoCitizenActivity.class);
        viewInfoCitizenActivity.putExtra("role", Role.CITIZEN);
        viewInfoCitizenActivity.putExtra("action", Action.REGISTER);
        viewInfoCitizenActivity.putExtra("username", username);
        startActivity(viewInfoCitizenActivity);
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}