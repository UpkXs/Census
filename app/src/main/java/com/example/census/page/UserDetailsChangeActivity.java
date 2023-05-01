package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.census.enums.AnswerType;
import com.example.census.enums.Role;
import com.example.census.model.CensusForm;
import com.example.census.model.Household;
import com.example.census.model.Question;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDetailsChangeActivity extends AppCompatActivity {

    private Role role;
    private String username;

    private int censusFormId;
    private long citizenTIN;
    private int householdId;

    private MyDatabaseHelper myDB;

    private Button btnUpdateCitizenInfoAndGoToStationaryViewPage;

    private TextView txtHouseholdInfo;

    private LinearLayout citizenInfoLayout;
    private LinearLayout fieldLayout;
    private TextView fieldHeader;
    private EditText fieldBody;


    private List<Question> citizenInfoQuestions = new ArrayList() {{
        add(new Question(1, "TIN:", AnswerType.NUMBER, ""));
        add(new Question(2, "Age:", AnswerType.NUMBER, ""));
        add(new Question(3, "Number:", AnswerType.NUMBER, ""));
        add(new Question(4, "Sex:", AnswerType.TEXT, ""));
        add(new Question(5, "Birth:", AnswerType.TEXT, ""));
        add(new Question(6, "Owner rel:", AnswerType.TEXT, ""));
        add(new Question(7, "Citizenship:", AnswerType.TEXT, ""));
        add(new Question(8, "Location:", AnswerType.TEXT, ""));
        add(new Question(9, "Nation:", AnswerType.TEXT, ""));
        add(new Question(10, "Language:", AnswerType.TEXT, ""));
        add(new Question(11, "Other language:", AnswerType.TEXT, ""));
        add(new Question(12, "Education:", AnswerType.TEXT, ""));
        add(new Question(13, "Live period:", AnswerType.NUMBER, ""));
        add(new Question(14, "Other live period:", AnswerType.NUMBER, ""));
        add(new Question(15, "Status:", AnswerType.TEXT, ""));
        add(new Question(16, "Marriage year:", AnswerType.NUMBER, ""));
        add(new Question(17, "Marriage sum:", AnswerType.NUMBER, ""));
        add(new Question(18, "Job period:", AnswerType.NUMBER, ""));
        add(new Question(19, "Job sphere:", AnswerType.TEXT, ""));
        add(new Question(20, "Job location:", AnswerType.TEXT, ""));
        add(new Question(21, "Part time:", AnswerType.TEXT, ""));
        add(new Question(22, "Income sum type:", AnswerType.TEXT, ""));
    }};

    private List<String> censusFormInfo;

    private HashMap<Integer, EditText> citizenInfoEditedAnswers = new HashMap<>();

    private List<Question> houseHoldInfoQuestions = new ArrayList() {{
        add(new Question(1, "Address:", AnswerType.TEXT, ""));
        add(new Question(2, "Type:", AnswerType.NUMBER, ""));
        add(new Question(3, "Year:", AnswerType.NUMBER, ""));
        add(new Question(4, "Floor:", AnswerType.NUMBER, ""));
        add(new Question(5, "Material:", AnswerType.TEXT, ""));
        add(new Question(6, "Landscape:", AnswerType.TEXT, ""));
        add(new Question(7, "Size:", AnswerType.NUMBER, ""));
        add(new Question(8, "Wo size:", AnswerType.NUMBER, ""));
        add(new Question(9, "Room:", AnswerType.NUMBER, ""));
        add(new Question(10, "Owner:", AnswerType.TEXT, ""));
    }};

    private List<String> householdFormInfo;

    private HashMap<Integer, EditText> householdInfoEditedAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_change);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");

            System.out.println("iVUnt857 :: UserDetailsChangeActivity");
            System.out.println("d0AQzNR4 :: role : " + role);
            System.out.println("38X1i5fa :: username " + username);
        }

        myDB = new MyDatabaseHelper(UserDetailsChangeActivity.this);

        citizenTIN = getCitizenTinByUsername(username, citizenTIN);

        System.out.println("d0bdN8oH :: citizenTIN : " + citizenTIN);

        censusFormId = getCensusFormIdByCitizenTin(citizenTIN);

        System.out.println("lEfxIYN8 :: censusFormId : " + censusFormId);

        householdId = getHouseholdIdByCitizenTin(citizenTIN);

        System.out.println("EZJ8dwA5 :: householdId : " + householdId);

        censusFormInfo = getCensusFormByCitizenTIN(citizenTIN);

        if (censusFormInfo.isEmpty()) {
            System.out.println("6SC087VU :: Census Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            toastShow("Census Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            return;
        }

        for (int i = 0; i < citizenInfoQuestions.size(); i++) {
            System.out.println("pH4N6905 :: citizenInfoQuestions.get(i).getId() = " + citizenInfoQuestions.get(i).getId());
            System.out.println("kfL3ek5l :: citizenInfoQuestions.get(i).getQuestion() = " + citizenInfoQuestions.get(i).getQuestion());
        }

        // Get a reference to the parent layout
        LinearLayout parentLayout = findViewById(R.id.parentLayout);

        btnUpdateCitizenInfoAndGoToStationaryViewPage = findViewById(R.id.btnUpdateCitizenInfoAndGoToStationaryViewPage);
        parentLayout.removeView(btnUpdateCitizenInfoAndGoToStationaryViewPage);

        txtHouseholdInfo = findViewById(R.id.txtHouseholdInfo);
        parentLayout.removeView(txtHouseholdInfo);

        LinearLayout censusFormLayout = new LinearLayout(this);
        censusFormLayout.setOrientation(LinearLayout.VERTICAL);

        // Iterate through the questions and inflate the layout for each one
        for (int i = 0; i < citizenInfoQuestions.size(); i++) {
            // Inflate the layout
            View censusFormEditView = LayoutInflater.from(this).inflate(R.layout.activity_citizen_info_edit_layout, null);

            citizenInfoLayout = censusFormEditView.findViewById(R.id.citizenInfoLayout);
            fieldLayout = censusFormEditView.findViewById(R.id.fieldLayout);
            fieldHeader = censusFormEditView.findViewById(R.id.fieldHeader);
            fieldBody = censusFormEditView.findViewById(R.id.fieldBody);

            System.out.println("n70Sd0ae :: citizenInfoQuestions.get(i).getId() = " + citizenInfoQuestions.get(i).getId());
            System.out.println("7ji7c024 :: citizenInfoQuestions.get(i).getQuestion() = " + citizenInfoQuestions.get(i).getQuestion());
            System.out.println("AXHpH0U2 :: censusFormInfo.get(i) = " + censusFormInfo.get(i));

            fieldHeader.setText(citizenInfoQuestions.get(i).getQuestion().toString().trim());
            fieldBody.setText(censusFormInfo.get(i).toString().trim());

            // Add the censusFormEdit view to the parent layout
            censusFormLayout.addView(censusFormEditView);

            EditText questionAnswer = fieldBody;
            questionAnswer.setId(citizenInfoQuestions.get(i).getId());
            if (citizenInfoQuestions.get(i).getAnswerType().label.equals(AnswerType.NUMBER.label)) {
                questionAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                questionAnswer.setHint("enter answer only numbers");
            } else if (citizenInfoQuestions.get(i).getAnswerType().label.equals(AnswerType.TEXT.label)) {
                questionAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            citizenInfoEditedAnswers.put(citizenInfoQuestions.get(i).getId(), questionAnswer);
        }

        parentLayout.addView(censusFormLayout);

        householdFormInfo = getHouseholdFormByCitizenTIN(citizenTIN);

        if (householdFormInfo.isEmpty()) {
            System.out.println("dlPLl2z9 :: Household Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            toastShow("Household Form Info with citizenTIN = " + citizenTIN + " not found in DB");
        } else {
            parentLayout.addView(txtHouseholdInfo);

            LinearLayout householdFormLayout = new LinearLayout(this);
            householdFormLayout.setOrientation(LinearLayout.VERTICAL);

            // Iterate through the questions and inflate the layout for each one
            for (int i = 0; i < houseHoldInfoQuestions.size(); i++) {
                // Inflate the layout
                View householdFormEditView = LayoutInflater.from(this).inflate(R.layout.activity_citizen_info_edit_layout, null);

                citizenInfoLayout = householdFormEditView.findViewById(R.id.citizenInfoLayout);
                fieldLayout = householdFormEditView.findViewById(R.id.fieldLayout);
                fieldHeader = householdFormEditView.findViewById(R.id.fieldHeader);
                fieldBody = householdFormEditView.findViewById(R.id.fieldBody);

                System.out.println("KQR88KyD :: houseHoldInfoQuestions.get(i).getId() = " + houseHoldInfoQuestions.get(i).getId());
                System.out.println("snC447vx :: houseHoldInfoQuestions.get(i).getQuestion() = " + houseHoldInfoQuestions.get(i).getQuestion());
                System.out.println("i8TSFIph :: householdFormInfo.get(i) = " + householdFormInfo.get(i));

                fieldHeader.setText(houseHoldInfoQuestions.get(i).getQuestion().toString().trim());
                fieldBody.setText(householdFormInfo.get(i).toString().trim());

                // Add the householdFormEdit view to the parent layout
                householdFormLayout.addView(householdFormEditView);

                EditText questionAnswer = fieldBody;
                questionAnswer.setId(houseHoldInfoQuestions.get(i).getId());
                if (houseHoldInfoQuestions.get(i).getAnswerType().label.equals(AnswerType.NUMBER.label)) {
                    questionAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                    questionAnswer.setHint("enter answer only numbers");
                } else if (houseHoldInfoQuestions.get(i).getAnswerType().label.equals(AnswerType.TEXT.label)) {
                    questionAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                householdInfoEditedAnswers.put(houseHoldInfoQuestions.get(i).getId(), questionAnswer);
            }

            parentLayout.addView(householdFormLayout);
        }

        parentLayout.addView(btnUpdateCitizenInfoAndGoToStationaryViewPage);

        btnUpdateCitizenInfoAndGoToStationaryViewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAndGoToStationaryViewPage(view, censusFormId, householdId);
            }
        });
    }

    private void updateAndGoToStationaryViewPage(View view, int censusFormId, int householdId) {

        boolean censusFormAllEmpty = false;

        // check if all values are empty
        for (EditText value : citizenInfoEditedAnswers.values()) {
            if (value.getText().toString().isEmpty()) {
                censusFormAllEmpty = true;
                break;
            }
        }

        boolean householdFormAllEmpty = false;

        // check if all values are empty
        for (EditText value : householdInfoEditedAnswers.values()) {
            if (value.getText().toString().isEmpty()) {
                householdFormAllEmpty = true;
                break;
            }
        }

        if (censusFormAllEmpty || householdFormAllEmpty) {
            System.out.println("XFQIxeFN :: All values are empty. Please answer to all question.");
            toastShow("All values are empty. Please answer to all question.");
            return;
        }

        for (int i = 1; i < householdInfoEditedAnswers.size() + 1; i++) { // todo aro remove
            System.out.println("hQWqUlM2 :: householdInfoEditedAnswers.get(i) = " + householdInfoEditedAnswers.get(i));
            System.out.println("4zFJUgYC :: householdInfoEditedAnswers.get(i).getId() = " + householdInfoEditedAnswers.get(i).getId());
            System.out.println("j0oNNz5h :: householdInfoEditedAnswers.get(i).getText() = " + householdInfoEditedAnswers.get(i).getText());
        }

        CensusForm censusForm = new CensusForm();

        censusForm.setId(censusFormId);
        censusForm.setCitizen_tin(Long.parseLong(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(0).getId()).getText().toString().trim()));
        censusForm.setAge(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(1).getId()).getText().toString().trim()));
        censusForm.setNumber(Long.parseLong(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(2).getId()).getText().toString().trim()));
        censusForm.setSex(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(3).getId()).getText().toString().trim());
        censusForm.setBirth(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(4).getId()).getText().toString().trim());
        censusForm.setOwner_rel(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(5).getId()).getText().toString().trim());
        censusForm.setCitizenship(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(6).getId()).getText().toString().trim());
        censusForm.setLocation(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(7).getId()).getText().toString().trim());
        censusForm.setNation(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(8).getId()).getText().toString().trim());
        censusForm.setLanguage(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(9).getId()).getText().toString().trim());
        censusForm.setO_language(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(10).getId()).getText().toString().trim());
        censusForm.setEducation(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(11).getId()).getText().toString().trim());
        censusForm.setLive_period(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(12).getId()).getText().toString().trim()));
        censusForm.setO_live_period(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(13).getId()).getText().toString().trim()));
        censusForm.setStatus(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(14).getId()).getText().toString().trim());
        censusForm.setMarriage_year(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(15).getId()).getText().toString().trim()));
        censusForm.setMarriage_sum(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(16).getId()).getText().toString().trim()));
        censusForm.setJob_period(Integer.parseInt(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(17).getId()).getText().toString().trim()));
        censusForm.setJob_sphere(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(18).getId()).getText().toString().trim());
        censusForm.setJob_location(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(19).getId()).getText().toString().trim());
        censusForm.setParttime(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(20).getId()).getText().toString().trim());
        censusForm.setIncome_sum_type(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(21).getId()).getText().toString().trim());

        int usernameId = getUsernameIdByUsername(username);

        long updateCitizenTINResult = myDB.updateCitizenTIN(usernameId, censusForm.getCitizen_tin());
        if (updateCitizenTINResult == -1) {
            System.out.println("MWuZYWpc :: CitizenTIN to update failed");
            toastShow("CitizenTIN to update failed");
        } else {
            System.out.println("P4OkVv2v :: CitizenTIN successfully updated");
            toastShow("CitizenTIN successfully updated");
        }

        long updateCensusFormResult = myDB.updateCensusForm(censusForm);
        if (updateCensusFormResult == -1) {
            System.out.println("7eTbnKc9 :: Census Form to update failed");
            toastShow("Census Form to update failed");
        } else {
            System.out.println("MW4Hj8Y7 :: Census Form successfully updated");
            toastShow("Census Form successfully updated");
        }

        Household household = new Household();

        household.setId(householdId);
        household.setCitizen_tin(Long.parseLong(citizenInfoEditedAnswers.get(citizenInfoQuestions.get(0).getId()).getText().toString().trim()));
        household.setAddress(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(0).getId()).getText().toString().trim());
        household.setType(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(1).getId()).getText().toString().trim()));
        household.setYear(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(2).getId()).getText().toString().trim()));
        household.setFloor(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(3).getId()).getText().toString().trim()));
        household.setMaterial(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(4).getId()).getText().toString().trim());
        household.setLandscape(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(5).getId()).getText().toString().trim());
        household.setSize(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(6).getId()).getText().toString().trim()));
        household.setWo_size(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(7).getId()).getText().toString().trim()));
        household.setRoom(Integer.parseInt(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(8).getId()).getText().toString().trim()));
        household.setOwner(householdInfoEditedAnswers.get(houseHoldInfoQuestions.get(9).getId()).getText().toString().trim());

        long updateHouseholdResult = myDB.updateHousehold(household);
        if (updateHouseholdResult == -1) {
            System.out.println("qMa0ny6D :: Household to update failed");
            toastShow("Household to update failed");
        } else {
            System.out.println("qbwKM1Ho :: Household successfully updated");
            toastShow("Household successfully updated");
        }

        if (updateCitizenTINResult != -1 && updateCensusFormResult != -1 && updateHouseholdResult != -1) { // if all tables successfully updated go to next activity
            Intent changeDataStationaryActivity = new Intent(UserDetailsChangeActivity.this, ChangeDataStationaryActivity.class);
            changeDataStationaryActivity.putExtra("role", role);
            changeDataStationaryActivity.putExtra("username", username);
            startActivity(changeDataStationaryActivity);
        }
    }

    private long getCitizenTinByUsername(String username, long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select citizen_tin " +
                "from citizen inner join citizen_login " +
                "where citizen_login.username = '" + username + "' " +
                "and citizen.username_id = citizen_login._id");
        if (cursor.getCount() == 0) {
            return citizenTIN;
        } else {
            while (cursor.moveToNext()) {
                citizenTIN = cursor.getLong(0);
            }
        }
        return citizenTIN;
    }

    private int getCensusFormIdByCitizenTin(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select _id from census_form where citizen_tin = " + citizenTIN);
        int censusFormID = 0;
        if (cursor.getCount() == 0) {
            return censusFormID;
        } else {
            while (cursor.moveToNext()) {
                censusFormID = cursor.getInt(0);
            }
        }
        return censusFormID;
    }

    private int getHouseholdIdByCitizenTin(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select _id from household where citizen_tin = " + citizenTIN);
        int householdID = 0;
        if (cursor.getCount() == 0) {
            return householdID;
        } else {
            while (cursor.moveToNext()) {
                householdID = cursor.getInt(0);
            }
        }
        return householdID;
    }

    private int getUsernameIdByUsername(String username) {
        Cursor cursor = myDB.selectFromTable("select _id from citizen_login where username = '" + username + "'");
        int usernameId = 0;
        if (cursor.getCount() == 0) {
            return usernameId;
        } else {
            while (cursor.moveToNext()) {
                usernameId = cursor.getInt(0);
            }
        }
        return usernameId;
    }


    private List<String> getCensusFormByCitizenTIN(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select * from census_form where citizen_tin = " + citizenTIN);
        List<String> censusFormInfoList = new ArrayList<>();
        if (cursor.getCount() == 0) {
            return censusFormInfoList;
        } else {
            while (cursor.moveToNext()) {
                censusFormInfoList.add(String.valueOf(citizenTIN)); // tin
                censusFormInfoList.add(String.valueOf(cursor.getInt(2))); // age
                censusFormInfoList.add(String.valueOf(cursor.getLong(3))); // number
                censusFormInfoList.add(cursor.getString(4)); // sex
                censusFormInfoList.add(cursor.getString(5)); // birth
                censusFormInfoList.add(cursor.getString(6)); // owner_rel
                censusFormInfoList.add(cursor.getString(7)); // citizenship
                censusFormInfoList.add(cursor.getString(8)); // location
                censusFormInfoList.add(cursor.getString(9)); // nation
                censusFormInfoList.add(cursor.getString(10)); // language
                censusFormInfoList.add(cursor.getString(11)); // o_language
                censusFormInfoList.add(cursor.getString(12)); // education
                censusFormInfoList.add(String.valueOf(cursor.getInt(13))); // live_period
                censusFormInfoList.add(String.valueOf(cursor.getInt(14))); // o_live_period
                censusFormInfoList.add(cursor.getString(15)); // status
                censusFormInfoList.add(String.valueOf(cursor.getInt(16))); // marriage_year
                censusFormInfoList.add(String.valueOf(cursor.getInt(17))); // marriage_sum
                censusFormInfoList.add(String.valueOf(cursor.getInt(18))); // job_period
                censusFormInfoList.add(cursor.getString(19)); // job_sphere
                censusFormInfoList.add(cursor.getString(20)); // job_location
                censusFormInfoList.add(cursor.getString(21)); // part_time
                censusFormInfoList.add(cursor.getString(22)); // income_sum_type
            }
        }
        return censusFormInfoList;
    }

    private List<String> getHouseholdFormByCitizenTIN(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select household_address, " +
                "household_type, " +
                "household_year, " +
                "household_floor, " +
                "household_material, " +
                "household_landscape, " +
                "household_size, " +
                "household_wo_size, " +
                "household_room, " +
                "household_owner " +
                "from household where citizen_tin = " + citizenTIN);
        List<String> householdFormInfoList = new ArrayList<>();
        if (cursor.getCount() == 0) {
            return householdFormInfoList;
        } else {
            while (cursor.moveToNext()) {
                householdFormInfoList.add(cursor.getString(0)); // address
                householdFormInfoList.add(String.valueOf(cursor.getInt(1))); // type
                householdFormInfoList.add(String.valueOf(cursor.getInt(2))); // year
                householdFormInfoList.add(String.valueOf(cursor.getInt(3))); // floor
                householdFormInfoList.add(cursor.getString(4)); // material
                householdFormInfoList.add(cursor.getString(5)); // landscape
                householdFormInfoList.add(String.valueOf(cursor.getInt(6))); // size
                householdFormInfoList.add(String.valueOf(cursor.getInt(7))); // wo_size
                householdFormInfoList.add(String.valueOf(cursor.getInt(8))); // room
                householdFormInfoList.add(cursor.getString(9)); // owner
            }
        }
        return householdFormInfoList;
    }

    private void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    // todo aro f4f72tr8 implement logic of change citizen info
}