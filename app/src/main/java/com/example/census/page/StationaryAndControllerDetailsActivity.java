package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.database.PasswordToHash;
import com.example.census.enums.Role;
import com.example.census.model.Region;
import com.example.census.modelDAO.ControllerDAO;
import com.example.census.modelDAO.StationaryDAO;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoAdminActivity;

import java.util.ArrayList;
import java.util.List;

public class StationaryAndControllerDetailsActivity extends AppCompatActivity {

    private Role role;
    private String username;
    private MyDatabaseHelper myDB;
    private Button btnUpdate;
    private StationaryDAO stationaryDAO;
    private ControllerDAO controllerDAO;

    private TextView txtPersonID;
    private EditText editTxtUsername;
    private TextView txtRole;

    private EditText editTxtOldPassword;
    private EditText editTxtNewPassword;

    private Region region;
    private List<String> regionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_and_controller_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("6t28OO4E :: StationaryAndControllerDetailsActivity");
        System.out.println("UJDn4XVJ :: role : " + role);
        System.out.println("iF192x72 :: username : " + username);

        myDB = new MyDatabaseHelper(StationaryAndControllerDetailsActivity.this);

        txtPersonID     = findViewById(R.id.personId); // do not change role
        editTxtUsername = findViewById(R.id.username);
        txtRole = findViewById(R.id.role); // do not change role

        Region regionName = new Region();

        if (role.label.equals(Role.STATIONARY.label)) {
            stationaryDAO = getStationary(username);

            if (stationaryDAO == null) {
                toastShow("Stationary with username " + username + " not found in DB");
                return;
            }

            regionName = getRegionName(stationaryDAO.getRegion_id());

            System.out.println("dX89CAUo :: " + stationaryDAO.getStationary_id());
            System.out.println("I1O4VchY :: " + stationaryDAO.getStationary_username());
            System.out.println("4nVTPgzU :: " + stationaryDAO.getRegion_id());
            System.out.println("StIj9T75 :: " + regionName.getRegion_id());
            System.out.println("2eH8qFJn :: " + regionName.getRegion_name());

            if (regionName.equals(new Region())) {
                System.out.println("tmr3eTnG :: regionName is empty = " + regionName.getRegion_name());
                toastShow("Region name is empty");
            }

            txtPersonID.setText(String.valueOf(stationaryDAO.getStationary_id()));
            editTxtUsername.setText(stationaryDAO.getStationary_username());
            txtRole.setText(role.label);
        } else if (role.label.equals(Role.CONTROLLER.label)) {
            controllerDAO = getController(username);

            if (controllerDAO == null) {
                toastShow("Controller with username " + username + " not found in DB");
                return;
            }

            regionName = getRegionName(controllerDAO.getRegion_id());

            System.out.println("tKA98H1Y :: " + controllerDAO.getController_id());
            System.out.println("I1O4VchY :: " + controllerDAO.getController_username());
            System.out.println("4nVTPgzU :: " + controllerDAO.getRegion_id());
            System.out.println("6PJBjFoH :: " + regionName.getRegion_id());
            System.out.println("4PY419yO :: " + regionName.getRegion_name());

            if (regionName.equals(new Region())) {
                System.out.println("tmr3eTnG :: regionName is empty = " + regionName);
                toastShow("Region name is empty");
            }

            txtPersonID.setText(String.valueOf(controllerDAO.getController_id()));
            editTxtUsername.setText(controllerDAO.getController_username());
            txtRole.setText(role.label);
        }

        region = regionName;

        regionList = new ArrayList<>();

        getRegions(); // select all regions from database( * table name region)

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, regionList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            adapter.setAutofillOptions(regionName.getRegion_name()); // todo check default selected region name in DB
        }

        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int regionID = position + 1; // in adapterView item ids starts with zero, we need increment it to one to get from DB
                region = getRegionName(regionID);
                Toast.makeText(StationaryAndControllerDetailsActivity.this, "Selected region: " + region.getRegion_name(), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newHashedPassword = getNewHashedPassword(); // get new password if admin entered old password

                getChangedDataAndUpdate(newHashedPassword); // get changed dates from layout
            }
        });
    }

    public String getNewHashedPassword() { // todo when old password is incorrect do something, and fix visibility
        editTxtOldPassword = findViewById(R.id.oldPassword);
        editTxtNewPassword = findViewById(R.id.newPassword);
        editTxtNewPassword.setVisibility(View.GONE);

        String oldPassword = editTxtOldPassword.getText().toString().trim();
        System.out.println("2JA0WSG0 :: oldPassword : " + oldPassword);

        PasswordToHash passwordToHash = new PasswordToHash();
        String hashedPassword = passwordToHash.doHash(oldPassword);
        System.out.println("wEe2VkC4 :: hashedPassword : " + hashedPassword);

        String newHashedPassword = "";
        if (role.label.equals(Role.STATIONARY.label) && stationaryDAO.getStationary_password().equals(hashedPassword)) {
            editTxtNewPassword.setVisibility(View.VISIBLE);
            String newPassword = editTxtNewPassword.getText().toString().trim();
            newHashedPassword = passwordToHash.doHash(newPassword);
            System.out.println("BT2Y6x50 :: newPassword : " + newPassword);
            System.out.println("14ayUM3y :: newHashedPassword : " + newHashedPassword);
        } else if (role.label.equals(Role.CONTROLLER.label) && controllerDAO.getController_password().equals(hashedPassword)) {
            editTxtNewPassword.setVisibility(View.VISIBLE);
            String newPassword = editTxtNewPassword.getText().toString().trim();
            newHashedPassword = passwordToHash.doHash(newPassword);
            System.out.println("9vBtA07d :: newPassword : " + newPassword);
            System.out.println("MkH1JVO6 :: newHashedPassword : " + newHashedPassword);
        }

        return newHashedPassword;
    }

    public void getChangedDataAndUpdate(String newHashedPassword) {
        if (role.label.equals(Role.STATIONARY.label)) {
            StationaryDAO stationaryDAO = new StationaryDAO();
            stationaryDAO.setStationary_id(Integer.parseInt(txtPersonID.getText().toString().trim()));
            stationaryDAO.setStationary_username(editTxtUsername.getText().toString().trim());

            System.out.println("6SL2Ot5v :: region.getRegion_id() : " + region.getRegion_id());
            System.out.println("pqIwUXa0 :: region.getRegion_name() :" + region.getRegion_name());
            stationaryDAO.setRegion_id(region.getRegion_id());

            System.out.println("7u33mu54 :: newHashedPassword : " + newHashedPassword);
            stationaryDAO.setStationary_password(newHashedPassword);

            updateStationary(stationaryDAO);

        } else if (role.label.equals(Role.CONTROLLER.label)) {
            ControllerDAO controllerDAO = new ControllerDAO();
            controllerDAO.setController_id(Integer.parseInt(txtPersonID.getText().toString().trim()));
            controllerDAO.setController_username(editTxtUsername.getText().toString().trim());

            System.out.println("z8INX2A6 :: region.getRegion_id() : " + region.getRegion_id());
            System.out.println("3ns210I6 :: region.getRegion_name() :" + region.getRegion_name());
            controllerDAO.setRegion_id(region.getRegion_id());

            System.out.println("sOYfLAKZ :: newHashedPassword : " + newHashedPassword);
            controllerDAO.setController_password(newHashedPassword);

            updateController(controllerDAO);
        }
    }

    public void updateStationary(StationaryDAO stationaryDAO) {
        long result = myDB.updateStationary(stationaryDAO);
        if (result == -1) {
            toastShow(role.label + " with username " + stationaryDAO.getStationary_username() + " failed to update");
        } else {
            toastShow(role.label + " with username " + stationaryDAO.getStationary_username() + " successfully updated");
            Intent viewInfoAdminActivity = new Intent(getApplicationContext(), ViewInfoAdminActivity.class);
            startActivity(viewInfoAdminActivity); // after successfully updating go to ViewInfoAdminActivity
        }
    }

    public void updateController(ControllerDAO controllerDAO) {
        long result = myDB.updateController(controllerDAO);
        if (result == -1) {
            toastShow(role.label + " with username " + controllerDAO.getController_username() + " failed to update");
        } else {
            toastShow(role.label + " with username " + controllerDAO.getController_username() + " successfully updated");
            Intent viewInfoAdminActivity = new Intent(getApplicationContext(), ViewInfoAdminActivity.class);
            startActivity(viewInfoAdminActivity); // after successfully updating go to ViewInfoAdminActivity
        }
    }

    public StationaryDAO getStationary(String username) {
        Cursor cursor = myDB.selectFromTable("select _id, stationary_username, region_id, stationary_password from stationary " +
                "where stationary_username = '" + username + "';");
        StationaryDAO stationaryDAO = new StationaryDAO();
        if (cursor.getCount() == 0){
            return stationaryDAO;
        } else {
            while (cursor.moveToNext()) {
                stationaryDAO.setStationary_id(cursor.getInt(0));
                stationaryDAO.setStationary_username(cursor.getString(1));
                stationaryDAO.setRegion_id(cursor.getInt(2));
                stationaryDAO.setStationary_password(cursor.getString(3));
            }
        }
        return stationaryDAO;
    }

    public ControllerDAO getController(String username) {
        Cursor cursor = myDB.selectFromTable("select _id, controller_username, region_id, controller_password from controller " +
                "where controller_username = '" + username + "';");
        ControllerDAO controllerDAO = new ControllerDAO();
        if (cursor.getCount() == 0){
            return controllerDAO;
        } else {
            while (cursor.moveToNext()) {
                controllerDAO.setController_id(cursor.getInt(0));
                controllerDAO.setController_username(cursor.getString(1));
                controllerDAO.setRegion_id(cursor.getInt(2));
                controllerDAO.setController_password(cursor.getString(3));
            }
        }
        return controllerDAO;
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

    private Region getRegionName(int region_id) {
        Cursor cursor = myDB.selectFromTable("select * from region where _id = " + region_id);
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
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}