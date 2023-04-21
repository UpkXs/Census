package com.example.census.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.biometricAuth.FaceIDActivity;
import com.example.census.database.PasswordToHash;
import com.example.census.model.Citizen;
import com.example.census.model.CitizenLogin;
import com.example.census.model.Region;
import com.example.census.model.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CitizenRegistrationActivity extends AppCompatActivity {

    private final Role role = Role.CITIZEN;
    private Region region;
    private Citizen citizen;
    private CitizenLogin citizenLogin;

    private MyDatabaseHelper myDB;
    private List<String> regionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_citizen_registration);

            String loginPage = role.label + " Registration Page";

            TextView textView = findViewById(R.id.roleId);
            textView.setText(loginPage);

            TextView createAccount = findViewById(R.id.createAccount);
            createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            myDB = new MyDatabaseHelper(CitizenRegistrationActivity.this);
            regionList = new ArrayList<>();

            getRegions(); // select all regions from database( * table name region)

            AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, regionList);
            autoCompleteTextView.setAdapter(adapter);

            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedRegion = adapterView.getItemAtPosition(i).toString();
                    region = getRegion(selectedRegion);
                    Toast.makeText(CitizenRegistrationActivity.this, "Selected region: " + region.getRegion_name(), Toast.LENGTH_LONG).show();
                }
            });

            Button registerBtn = findViewById(R.id.registerBtn);
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    citizenLogin = new CitizenLogin();
                    citizen = new Citizen();
                    register(v, citizenLogin, citizen);
                }
            });
        }
    }

    long rows = 0;
    public void register(View view, CitizenLogin citizenLogin, Citizen citizen) {
        EditText firstName = findViewById(R.id.inputFirstName);
        EditText lastName = findViewById(R.id.inputLastName);
        EditText username = findViewById(R.id.inputUsername);
        EditText password = findViewById(R.id.inputPassword);

        PasswordToHash passwordToHash = new PasswordToHash();
        String hashedPassword = passwordToHash.doHash(String.valueOf(password.getText()));

        citizenLogin.setUsername(username.getText().toString());
        citizenLogin.setPassword(hashedPassword);
        rows = myDB.addCitizenLogin(citizenLogin); // insert into citizenLogin table new record
        if (rows == -1) {
            toastShow("Citizen username already in use!");
            return;
        }

        citizenLogin = getCitizenLogin(citizenLogin.getUsername());

        citizen.setCitizen_fullName(firstName.getText() + " " + lastName.getText());
        citizen.setUsername_id(citizenLogin.getUsername_id());
        citizen.setRegion_id(region.getRegion_id());

        rows = myDB.addCitizen(citizen);
        citizen = getCitizen(citizen.getUsername_id());

        Intent faceIDActivity = new Intent(this, FaceIDActivity.class);
        faceIDActivity.putExtra("role", role);
        faceIDActivity.putExtra("username", citizenLogin.getUsername());
        startActivity(faceIDActivity);
    }

    public void goToLoginActivity(View v) {
        Intent loginActivity = new Intent(this, CitizenLoginActivity.class);
        startActivity(loginActivity);
    }

    void getRegions() {
        Cursor cursor = myDB.selectFromTable("SELECT * FROM region");
        if (cursor.getCount() == 0) {
            regionList = new ArrayList<>();
        } else {
            while (cursor.moveToNext()) {
                String region = cursor.getString(1);
                regionList.add(region);
            }
        }
    }

    public Region getRegion(String regionName) {
        Cursor cursor = myDB.selectFromTable("SELECT * FROM region WHERE region_name = '" + regionName + "'");
        Region region = new Region();
        if (cursor.getCount() == 0) {
            return region;
        } else {
            while (cursor.moveToNext()) {
                System.out.println("region == " + cursor.getColumnName(0));
                System.out.println("region == " + cursor.getColumnName(1));
                region.setRegion_id(cursor.getInt(0));
                region.setRegion_name(cursor.getString(1));
            }
        }
        return region;
    }

    public CitizenLogin getCitizenLogin(String username) {
        Cursor cursor = myDB.selectFromTable("select * from citizen_login where username = '" + username + "'");
        CitizenLogin citizenLogin = new CitizenLogin();
        if (cursor.getCount() == 0) {
            return citizenLogin;
        } else {
            while (cursor.moveToNext()) {
                citizenLogin.setUsername_id(cursor.getInt(0));
                citizenLogin.setUsername(cursor.getString(1));
                citizenLogin.setPassword(cursor.getString(2));
                citizenLogin.setFinger_print(cursor.getString(3));
                citizenLogin.setFacial_print(cursor.getString(4));
                citizenLogin.setApi_key(cursor.getInt(5));
            }
        }
        return citizenLogin;
    }

    public Citizen getCitizen(int username_id) {
        Cursor cursor = myDB.selectFromTable("select * from citizen where username_id = '" + username_id + "'");
        Citizen citizen = new Citizen();
        if (cursor.getCount() == 0) {
            return citizen;
        } else {
            while (cursor.moveToNext()) {
                citizen.setCitizen_tin(cursor.getInt(0));
                citizen.setCitizen_fullName(cursor.getString(1));
                citizen.setUsername_id(cursor.getInt(2));
                citizen.setRegion_id(cursor.getInt(3));
            }
        }
        return citizen;
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}