package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.modelInterview.LoginPartDAO;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

public class OnlineInterviewLoginPartActivity extends AppCompatActivity {

    private String username;

    private EditText editTxtTin;
    private EditText editTxtFullName;
    private EditText editTxtRegion;

    private Button btnGoToTheNextPart;

    private MyDatabaseHelper myDB;
    private LoginPartDAO loginPartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_login_part);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");

            System.out.println("9Nj8lXYc :: username : " + username);
        }

        myDB = new MyDatabaseHelper(OnlineInterviewLoginPartActivity.this);

        editTxtTin = findViewById(R.id.tin);
        editTxtFullName = findViewById(R.id.fullName);
        editTxtRegion = findViewById(R.id.region);

        btnGoToTheNextPart = findViewById(R.id.btnGoToTheNextPart);

        int citizenUsernameId = getCitizenUsernameId(username);

        if (citizenUsernameId == 0) {
            System.out.println("BnqxKM1M :: OnlineInterviewLoginPartActivity : " +
                    "Citizen username ID not found with username : " + username);
            toastShow("Citizen username ID not found with username : " + username);
            return;
        }

        loginPartDAO = getLoginPartDAOInfo(citizenUsernameId);

        if (loginPartDAO.equals(new LoginPartDAO())) {
            System.out.println("4FfTLAMy :: OnlineInterviewLoginPartActivity : " +
                    "Citizen username not found with usernameId : " + citizenUsernameId);
            toastShow("Citizen username not found with usernameId : " + citizenUsernameId);
            return;
        }

        editTxtTin.setText(loginPartDAO.getTin());
        editTxtFullName.setText(loginPartDAO.getFullName());
        editTxtRegion.setText(loginPartDAO.getRegionName());

        btnGoToTheNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTheNextPart(view, citizenUsernameId);
            }
        });

    }

    private void goToTheNextPart(View view, int citizenUsernameId) {
        if (editTxtTin.getText().toString().trim().isEmpty() ||
                editTxtFullName.getText().toString().trim().isEmpty() ||
                editTxtRegion.getText().toString().trim().isEmpty()) {
            System.out.println("cmV84h7M :: OnlineInterviewLoginPartActivity : Some field is not entered. Please enter all the details.");
            toastShow("Some field is not entered. Please enter all the details.");
            return;
        }

        String citizenTIN = editTxtTin.getText().toString().trim();
        String citizenFullName = editTxtFullName.getText().toString().trim();
        String citizenRegionName = editTxtRegion.getText().toString().trim();

        int citizenRegionId = getRegionId(citizenRegionName);

        if (citizenRegionId == 0) {
            System.out.println("l115TQzg :: OnlineInterviewLoginPartActivity : " +
                    "Region ID not found with region name : " + citizenRegionName);
            toastShow("Region ID not found with region name : " + citizenRegionName);
            return;
        }

        long result = myDB.updateCitizen(citizenUsernameId, citizenTIN, citizenFullName, citizenRegionId);
        if (result == -1) {
            System.out.println("PFk2h3ZF :: First part of online interview not correctly passed");
            toastShow("First part of online interview not correctly passed");
        } else {
            System.out.println("vmw3jSl5 :: First part of online interview passed successfully");
            toastShow("First part of online interview passed successfully");
            Intent onlineInterviewHouseholdPartActivity = new Intent(OnlineInterviewLoginPartActivity.this, OnlineInterviewHouseholdPartActivity.class);
            onlineInterviewHouseholdPartActivity.putExtra("username", username);
            startActivity(onlineInterviewHouseholdPartActivity);
        }
    }

    private void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private int getRegionId(String regionName) {
        Cursor cursor = myDB.selectFromTable("select _id from region where region_name == '" + regionName + "'");
        int regionId = 0;
        if (cursor.getCount() == 0) {
            return regionId;
        } else {
            while (cursor.moveToNext()) {
                regionId = cursor.getInt(0);
            }
        }
        return regionId;
    }

    private int getCitizenUsernameId(String username) {
        Cursor cursor = myDB.selectFromTable("select _id from citizen_login where username == '" + username + "'");
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

    private LoginPartDAO getLoginPartDAOInfo(int usernameId) {
        Cursor cursor = myDB.selectFromTable("select citizen_tin, citizen_fullName, region_name " +
                "from citizen inner join region " +
                "where citizen.username_id == " + usernameId +
                "and citizen.region_id == region._id");
        LoginPartDAO loginPartDAO = new LoginPartDAO();
        if (cursor.getCount() == 0) {
            return loginPartDAO;
        } else {
            while (cursor.moveToNext()) {
                loginPartDAO.setTin(cursor.getInt(0));
                loginPartDAO.setFullName(cursor.getString(1));
                loginPartDAO.setRegionName(cursor.getString(2));
            }
        }
        return loginPartDAO;
    }
}