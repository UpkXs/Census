package com.example.census.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.database.PasswordToHash;
import com.example.census.model.Controller;
import com.example.census.model.Region;
import com.example.census.enums.Role;
import com.example.census.model.Stationary;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoAdminActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Role role;
    private Region region;
    private Stationary stationary;
    private Controller controller;
    private MyDatabaseHelper myDB;
    private List<String> regionList;

    private CheckBox checkboxStationary;
    private CheckBox checkboxController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkboxStationary = (CheckBox) findViewById(R.id.checkboxStationary);
        checkboxController = (CheckBox) findViewById(R.id.checkboxController);

        String registerPage = Role.ADMIN.label + " Registration Page";
        TextView textView = findViewById(R.id.roleId);
        textView.setText(registerPage);

        myDB = new MyDatabaseHelper(RegisterActivity.this);
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
                Toast.makeText(RegisterActivity.this, "Selected region: " + region.getRegion_name(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add an OnCheckedChangeListener to checkbox1
        checkboxStationary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable checkbox2 if checkbox1 is checked
                checkboxController.setEnabled(!isChecked);
                role = Role.STATIONARY;
            }
        });

        // Add an OnCheckedChangeListener to checkbox2
        checkboxController.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Disable checkbox1 if checkbox2 is checked
                checkboxStationary.setEnabled(!isChecked);
                role = Role.CONTROLLER;
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationary = new Stationary();
                controller = new Controller();
                System.out.println("jsH9I95q :: role : " + role);
                register(view, stationary, controller, region, role);
            }
        });
    }

    long rows = 0;
    public void register(View view, Stationary stationary, Controller controller, Region region, Role role) {
        EditText username = findViewById(R.id.inputUsername);
        EditText password = findViewById(R.id.inputPassword);

        PasswordToHash passwordToHash = new PasswordToHash();
        String hashedPassword = passwordToHash.doHash(String.valueOf(password.getText()));

        System.out.println("QPZ35VxV :: role : " + role);

        if (username.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                region == null ||
                role == null) {
            System.out.println("lrzFGvdD :: Please enter all data");
            toastShow("Please enter all data");
            return;
        }

        if (role.label.equals("Stationary")) {
            stationary.setStationary_username(username.getText().toString());
            stationary.setStationary_password(hashedPassword);
            stationary.setRegion_id(region.getRegion_id());

            rows = myDB.addStationary(stationary); // insert into stationary table new record
        } else if (role.label.equals("Controller")) {
            controller.setController_username(username.getText().toString());
            controller.setController_password(hashedPassword);
            controller.setRegion_id(region.getRegion_id());

            rows = myDB.addController(controller); // insert into controller table new record
        }

        if (rows == -1) {
            toastShow(role.label + " username already in use...");
            return;
        } else {
            toastShow(role.label + " successfully registered!");
        }

        Intent viewInfoAdminActivity = new Intent(this, ViewInfoAdminActivity.class);
        startActivity(viewInfoAdminActivity);
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
                region.setRegion_id(cursor.getInt(0));
                region.setRegion_name(cursor.getString(1));
            }
        }
        return region;
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}