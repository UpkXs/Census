package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.enums.Role;
import com.example.census.modelDAO.CitizenDAO;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoControllerActivity;

public class UserDetailsActivity extends AppCompatActivity {

    private Role role;
    private String username;

    private MyDatabaseHelper myDB;

    private CitizenDAO citizenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("3s1RmrO3 :: UserDetailsActivity");
        System.out.println("s882d7Sj :: role : " + role);
        System.out.println("f9rz172F :: username " + username);

        myDB = new MyDatabaseHelper(UserDetailsActivity.this);

        TextView txtFullName = findViewById(R.id.fullName);
        TextView txtUsername = findViewById(R.id.username);
        TextView txtTin = findViewById(R.id.tin);

        Button btnGoToViewControllerPage = findViewById(R.id.btnGoToViewControllerPage);

        btnGoToViewControllerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewInfoControllerActivity = new Intent(UserDetailsActivity.this, ViewInfoControllerActivity.class);
                viewInfoControllerActivity.putExtra("role", role);
                viewInfoControllerActivity.putExtra("username", username);
                startActivity(viewInfoControllerActivity);
            }
        });

        citizenDAO = getCitizenDAOInfo(username);

        if (citizenDAO == null) {
            toastShow("Citizen with username " + username + " not found in DB");
            return;
        }

        System.out.println("VB7k5dMX :: citizenDAO.getFullName() : " + citizenDAO.getFullName());
        System.out.println("0dmqLaMd :: citizenDAO.getUsername() : " + citizenDAO.getUsername());
        System.out.println("Ex787ZE4 :: citizenDAO.getTIN() : " + citizenDAO.getTIN());

        txtFullName.setText(citizenDAO.getFullName());
        txtUsername.setText(citizenDAO.getUsername());
        txtTin.setText(String.valueOf(citizenDAO.getTIN()));
    }

    private CitizenDAO getCitizenDAOInfo(String username) {
        Cursor cursor = myDB.selectFromTable("select citizen_fullName, username, citizen_tin " +
                                                     "from citizen inner join citizen_login " +
                                                         "where citizen_login.username == '" + username + "' " +
                                                             "and citizen.username_id == citizen_login._id");
        CitizenDAO citizenDAO = new CitizenDAO();
        if (cursor.getCount() == 0) {
            return citizenDAO;
        } else {
            while (cursor.moveToNext()) {
                citizenDAO.setFullName(cursor.getString(0));
                citizenDAO.setUsername(cursor.getString(1));
                citizenDAO.setTIN(cursor.getLong(2));
            }
        }
        return citizenDAO;
    }

    private void toastShow(CharSequence text) {
        Context context  = getApplicationContext();
        int     duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}