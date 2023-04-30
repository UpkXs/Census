package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.enums.AnswerType;
import com.example.census.model.Household;
import com.example.census.model.HouseholdType;
import com.example.census.model.HouseholdTypee;
import com.example.census.model.Question;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnlineInterviewHouseholdPartActivity extends AppCompatActivity {

    private String username;
    private int citizenTIN;
    private int citizenRegionId;

    private LinearLayout questionLayout;
    private LinearLayout questionHead;
    private LinearLayout questionBody;

    private TextView questionId;
    private TextView questionName;

    private Button btnGoToTheNextPart;

    private MyDatabaseHelper myDB;

    private List<Question> householdQuestions = new ArrayList(){{
        add(new Question(1, "Registration address:", AnswerType.TEXT, ""));
        add(new Question(2, "Specify the type of room used for accommodation:", AnswerType.NUMBER, ""));
        add(new Question(3, "Specify the year of construction of the residential buildings:", AnswerType.NUMBER, ""));
        add(new Question(4, "Specify the number of floors of residential buildings:", AnswerType.NUMBER, ""));
        add(new Question(5, "Specify the materials of exterior walls of residential buildings:", AnswerType.TEXT, ""));
        add(new Question(6, "What kind of amenities you have in your home (electricity, gas, heating, water):", AnswerType.TEXT, ""));
        add(new Question(7, "Specify the size of the total area:", AnswerType.NUMBER, ""));
        add(new Question(8, "Specify the size of the living area:", AnswerType.NUMBER, ""));
        add(new Question(9, "How many rooms are there in the living quarters (excluding kitchen, bath, toilet, hallway, storage rooms)?", AnswerType.NUMBER, ""));
        add(new Question(10, "Who owns the dwelling in which you live (private, individual, legal entity)?", AnswerType.TEXT, ""));
    }};

    private HashMap<Integer, EditText> answers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_household_part);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");
            citizenTIN = (int) extras.get("citizenTIN");
            citizenRegionId = (int) extras.get("citizenRegionId");

            System.out.println("pkrONW7K :: username : " + username);
            System.out.println("Wtp69OXC :: citizenTIN : " + citizenTIN);
            System.out.println("BhNQsjNA :: citizenRegionId : " + citizenRegionId);
        }

        myDB = new MyDatabaseHelper(OnlineInterviewHouseholdPartActivity.this);

        LinearLayout questionsLayout = new LinearLayout(this);
        questionsLayout.setOrientation(LinearLayout.VERTICAL);

        // Get a reference to the parent layout
        LinearLayout parentLayout = findViewById(R.id.parentLayout);

        View checkboxView = LayoutInflater.from(this).inflate(R.layout.activity_checkbox_houshold_type, null);

        // Iterate through the questions and inflate the layout for each one
        for (Question question : householdQuestions) {
            // Inflate the layout
            View questionView = LayoutInflater.from(this).inflate(R.layout.activity_question_layout, null);

            questionLayout = findViewById(R.id.questionLayout);
            questionHead = findViewById(R.id.questionHead);
            questionBody = findViewById(R.id.questionBody);

            if (question.getId() == 2) {
                System.out.println("6C41v9Bo :: question.getId() == 2 : question.getQuestion() = " + question.getQuestion());
                questionsLayout.addView(checkboxView);
                continue;
            }

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


        CheckBox checkbox1 = (CheckBox) checkboxView.findViewById(R.id.checkbox1);
        CheckBox checkbox21 = (CheckBox) checkboxView.findViewById(R.id.checkbox21);
        CheckBox checkbox22 = (CheckBox) checkboxView.findViewById(R.id.checkbox22);
        CheckBox checkbox3 = (CheckBox) checkboxView.findViewById(R.id.checkbox3);
        CheckBox checkbox4 = (CheckBox) checkboxView.findViewById(R.id.checkbox4);
        CheckBox checkbox5 = (CheckBox) checkboxView.findViewById(R.id.checkbox5);

        TextView questionId1 = checkboxView.findViewById(R.id.questionId1);
        TextView questionId2 = checkboxView.findViewById(R.id.questionId2);
        TextView questionId21 = checkboxView.findViewById(R.id.questionId21);
        TextView questionId22 = checkboxView.findViewById(R.id.questionId22);
        TextView questionId3 = checkboxView.findViewById(R.id.questionId3);
        TextView questionId4 = checkboxView.findViewById(R.id.questionId4);
        TextView questionId5 = checkboxView.findViewById(R.id.questionId5);


        TextView questionName1 = checkboxView.findViewById(R.id.questionName1);
        TextView questionName2 = checkboxView.findViewById(R.id.questionName2);
        TextView questionName21 = checkboxView.findViewById(R.id.questionName21);
        TextView questionName22 = checkboxView.findViewById(R.id.questionName22);
        TextView questionName3 = checkboxView.findViewById(R.id.questionName3);
        TextView questionName4 = checkboxView.findViewById(R.id.questionName4);
        TextView questionName5 = checkboxView.findViewById(R.id.questionName5);

        HouseholdType householdType = new HouseholdType();
        HouseholdTypee householdTypee = new HouseholdTypee();

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox1 is checked
                checkbox21.setEnabled(!isChecked);
                checkbox22.setEnabled(!isChecked);
                checkbox3.setEnabled(!isChecked);
                checkbox4.setEnabled(!isChecked);
                checkbox5.setEnabled(!isChecked);

                householdType.setId(1);
                householdType.setName(questionName1.getText().toString().trim());
                householdType.setType(0);
            }
        });

        checkbox21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox21 is checked
                checkbox1.setEnabled(!isChecked);
                checkbox22.setEnabled(!isChecked);
                checkbox3.setEnabled(!isChecked);
                checkbox4.setEnabled(!isChecked);
                checkbox5.setEnabled(!isChecked);

                householdTypee.setId(1);
                householdTypee.setName(questionName21.getText().toString().trim());

                householdType.setId(2);
                householdType.setName(questionName2.getText().toString().trim());
                householdType.setType(householdTypee.getId());
            }
        });

        checkbox22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox22 is checked
                checkbox1.setEnabled(!isChecked);
                checkbox21.setEnabled(!isChecked);
                checkbox3.setEnabled(!isChecked);
                checkbox4.setEnabled(!isChecked);
                checkbox5.setEnabled(!isChecked);

                householdTypee.setId(2);
                householdTypee.setName(questionName22.getText().toString().trim());

                householdType.setId(2);
                householdType.setName(questionName2.getText().toString().trim());
                householdType.setType(householdTypee.getId());
            }
        });

        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox3 is checked
                checkbox1.setEnabled(!isChecked);
                checkbox21.setEnabled(!isChecked);
                checkbox22.setEnabled(!isChecked);
                checkbox4.setEnabled(!isChecked);
                checkbox5.setEnabled(!isChecked);

                householdType.setId(3);
                householdType.setName(questionName3.getText().toString().trim());
                householdType.setType(0);
            }
        });

        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox4 is checked
                checkbox1.setEnabled(!isChecked);
                checkbox21.setEnabled(!isChecked);
                checkbox22.setEnabled(!isChecked);
                checkbox3.setEnabled(!isChecked);
                checkbox5.setEnabled(!isChecked);

                householdType.setId(4);
                householdType.setName(questionName4.getText().toString().trim());
                householdType.setType(0);
            }
        });

        checkbox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable other checkbox if checkbox5 is checked
                checkbox1.setEnabled(!isChecked);
                checkbox21.setEnabled(!isChecked);
                checkbox22.setEnabled(!isChecked);
                checkbox3.setEnabled(!isChecked);
                checkbox4.setEnabled(!isChecked);

                householdType.setId(5);
                householdType.setName(questionName5.getText().toString().trim());
                householdType.setType(0);
            }
        });

        btnGoToTheNextPart = findViewById(R.id.btnGoToTheNextPart);

        parentLayout.removeView(btnGoToTheNextPart);

        parentLayout.addView(btnGoToTheNextPart);

        btnGoToTheNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTheNextPart(view, householdType, householdTypee);
            }
        });

    }

    private void goToTheNextPart(View view, HouseholdType householdType, HouseholdTypee householdTypee) {

        boolean allEmpty = false;

        // check if all values are empty
        for (EditText value : answers.values()) {
            if (value.getText().toString().isEmpty()) {
                allEmpty = true;
                break;
            }
        }

        if (allEmpty) {
            System.out.println("pnTpc5pJ :: All values are empty. Please answer to all question.");
            toastShow("All values are empty. Please answer to all question.");
            return;
        }

        for (int i = 1; i < answers.size() + 1; i++) {
            if (i == 2) {
                System.out.println("9DQ7B8kr :: i == " + 2);
                continue;
            }
            System.out.println("Z3BSpi0U :: " + answers.get(i));
            System.out.println("voalAxSD :: " + answers.get(i).getId());
            System.out.println("PtX5dxx4 :: " + answers.get(i).getText());
        }

        Household household = new Household();

        // todo aro warning todo aro remove all sout

        System.out.println("cLN7fCLJ :: " + citizenTIN);
        household.setCitizen_tin(citizenTIN);

        System.out.println("KriEOmwu :: " + answers.get(householdQuestions.get(0).getId()).getText().toString().trim());
        household.setAddress(answers.get(householdQuestions.get(0).getId()).getText().toString().trim());

        System.out.println("EHQ5LK6K :: " + householdType.getId());
        System.out.println("EHQ5LK6K :: " + householdType.getName());
        System.out.println("EHQ5LK6K :: " + householdType.getType());
        System.out.println("EHQ5LK6K :: " + householdTypee.getId());
        System.out.println("EHQ5LK6K :: " + householdTypee.getName());

        System.out.println("3Gkq0AtU :: " + householdType.getType());
        household.setType(householdType.getType());

        System.out.println("uQM39wLO :: " + citizenRegionId);
        household.setRegion(citizenRegionId);

        System.out.println("24QOozir :: " + Integer.parseInt(answers.get(householdQuestions.get(2).getId()).getText().toString().trim()));
        household.setYear(Integer.parseInt(answers.get(householdQuestions.get(2).getId()).getText().toString().trim()));

        System.out.println("Jsy68baU :: " + Integer.parseInt(answers.get(householdQuestions.get(3).getId()).getText().toString().trim()));
        household.setFloor(Integer.parseInt(answers.get(householdQuestions.get(3).getId()).getText().toString().trim()));

        System.out.println("1tp38p9A :: " + answers.get(householdQuestions.get(4).getId()).getText().toString().trim());
        household.setMaterial(answers.get(householdQuestions.get(4).getId()).getText().toString().trim());

        System.out.println("4xqIwl31 :: " + answers.get(householdQuestions.get(5).getId()).getText().toString().trim());
        household.setLandscape(answers.get(householdQuestions.get(5).getId()).getText().toString().trim());

        System.out.println("5u753EWo :: " + Integer.parseInt(answers.get(householdQuestions.get(6).getId()).getText().toString().trim()));
        household.setSize(Integer.parseInt(answers.get(householdQuestions.get(6).getId()).getText().toString().trim()));

        System.out.println("2nRXCu22 :: " + Integer.parseInt(answers.get(householdQuestions.get(7).getId()).getText().toString().trim()));
        household.setSize(Integer.parseInt(answers.get(householdQuestions.get(7).getId()).getText().toString().trim()));

        System.out.println("L4dL22O6 :: " + Integer.parseInt(answers.get(householdQuestions.get(8).getId()).getText().toString().trim()));
        household.setSize(Integer.parseInt(answers.get(householdQuestions.get(8).getId()).getText().toString().trim()));

        System.out.println("MQCgGuh6 :: " + answers.get(householdQuestions.get(9).getId()).getText().toString().trim());
        household.setLandscape(answers.get(householdQuestions.get(9).getId()).getText().toString().trim());

        myDB.addHousehold(household);
        myDB.addHouseholdType(householdType);
        myDB.addHouseholdTypee(householdTypee);

        Intent onlineInterviewCensusFormActivity = new Intent(OnlineInterviewHouseholdPartActivity.this, OnlineInterviewCensusFormActivity.class);
        onlineInterviewCensusFormActivity.putExtra("username", username);
        startActivity(onlineInterviewCensusFormActivity);
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}