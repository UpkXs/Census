package com.example.census;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.database.CRUD;
import com.example.census.database.Database;
import com.example.census.database.PasswordToHash;
import com.example.census.model.Citizen;
import com.example.census.model.CitizenLogin;
import com.example.census.model.Region;
import com.example.census.model.Role;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class CitizenRegistrationActivity extends AppCompatActivity {

    private final Role role = Role.CITIZEN;
    private Region region;
    private Citizen citizen;
    private CitizenLogin citizenLogin;

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

            AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

            List<String> regionList = getRegions();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, regionList);
            autoCompleteTextView.setAdapter(adapter);

            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedRegion = adapterView.getItemAtPosition(i).toString();
                    region = getRegion(selectedRegion);
                    Toast.makeText(CitizenRegistrationActivity.this, "Selected region: " + selectedRegion, Toast.LENGTH_LONG).show();
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

    int rows = 0;
    public void register(View view, CitizenLogin citizenLogin, Citizen citizen) {
        EditText firstName = findViewById(R.id.inputFirstName);
        EditText lastName = findViewById(R.id.inputLastName);
        EditText username = findViewById(R.id.inputUsername);
        EditText password = findViewById(R.id.inputPassword);

        PasswordToHash passwordToHash = new PasswordToHash();
        String hashedPassword = passwordToHash.doHash(String.valueOf(password.getText()));

        citizenLogin.setUsername(username.getText().toString());
        citizenLogin.setPassword(hashedPassword);

        rows = insertIntoCitizenLogin(citizenLogin);
        System.out.println("rows = " + rows);
        if (rows == 0) {
            System.out.println("Citizen username already in use!");
            toastShow("Citizen username already in use!");
            return;
        }
        citizenLogin = selectFromCitizenLogin(citizenLogin.getUsername());

        citizen.setCitizen_fullName(firstName.getText() + " " + lastName.getText());
        citizen.setUsername_id(citizenLogin.getUsername_id());
        citizen.setRegion_id(region.getRegion_id());

        rows = insertIntoCitizen(citizen);
        System.out.println("rows = " + rows);
        citizen = selectFromCitizen(citizen.getUsername_id());

        Intent loginActivity = new Intent(this, CitizenLoginActivity.class);
        startActivity(loginActivity);
    }

    public void goToLoginActivity(View v) {
        Intent loginActivity = new Intent(this, CitizenLoginActivity.class);
        startActivity(loginActivity);
    }

    public List<String> getRegions() {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.selectFromRegion(connection, "select region_name from region order by region_name");
    }

    public Region getRegion(String region_name) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.selectRegionIdFromRegion(connection,
                "select * from region where region_name = '" + region_name + "'");
    }

    public int insertIntoCitizenLogin(CitizenLogin citizenLogin) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.insertInto(connection,
                "insert into citizen_login(username, password, finger_print, facial_print, api_key) " +
                        "values('" + citizenLogin.getUsername() + "', '" +
                        citizenLogin.getPassword()     + "', '" +
                        citizenLogin.getFinger_print() + "', '" +
                        citizenLogin.getFacial_print() + "', " +
                        citizenLogin.getApi_key()      + ")");
    }

    public CitizenLogin selectFromCitizenLogin(String username) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.selectFromCitizenLogin(connection,
                "select * from citizen_login where username = '" + username + "'");
    }

    private int insertIntoCitizen(Citizen citizen) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.insertInto(connection,
                "insert into citizen(citizen_tin, citizen_fullName, username_id, region_id) " +
                        "values(" + citizen.getCitizen_tin() + ", '" +
                        citizen.getCitizen_fullName()        + "', " +
                        citizen.getUsername_id()             + ", " +
                        citizen.getRegion_id()               + ")"
        );
    }

    public Citizen selectFromCitizen(int username_id) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        return crud.selectFromCitizen(connection,
                "select * from citizen where username_id = '" + username_id + "'");
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}