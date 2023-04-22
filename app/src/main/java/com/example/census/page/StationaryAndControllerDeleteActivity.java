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
import com.example.census.modelDAO.ControllerDAO;
import com.example.census.modelDAO.StationaryDAO;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoAdminActivity;

public class StationaryAndControllerDeleteActivity extends AppCompatActivity {

    private Role role;
    private String username;
    private MyDatabaseHelper myDB;
    private Button btnDelete;
    private StationaryDAO stationaryDAO;
    private ControllerDAO controllerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_and_controller_delete);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("6t28OO4E :: UserDetailsChangeActivity");
        System.out.println("UJDn4XVJ :: role : " + role);
        System.out.println("iF192x72 :: username : " + username);

        myDB = new MyDatabaseHelper(StationaryAndControllerDeleteActivity.this);

        TextView txtPersonID = findViewById(R.id.personId);
        TextView txtUsername = findViewById(R.id.username);
        TextView txtRegion = findViewById(R.id.region);
        TextView txtRole = findViewById(R.id.role);

        if (role.label.equals(Role.STATIONARY.label)) {
            stationaryDAO = getStationary(username);

            if (stationaryDAO == null) {
                toastShow("Stationary with username " + username + " not found in DB");
                return;
            }

            String regionName = getRegionName(stationaryDAO.getRegion_id());

            System.out.println("Lcak9N5O :: " + stationaryDAO.getStationary_id());
            System.out.println("I1O4VchY :: " + stationaryDAO.getStationary_username());
            System.out.println("4nVTPgzU :: " + stationaryDAO.getRegion_id());
            System.out.println("njxe1qus :: " + regionName);
            System.out.println("sQr1FPE3 :: " + regionName.isEmpty());

            if (regionName.isEmpty()) {
                System.out.println("tmr3eTnG :: regionName is empty = " + regionName);
                toastShow("Region name is empty");
            }

            txtPersonID.setText(String.valueOf(stationaryDAO.getStationary_id()));
            txtUsername.setText(stationaryDAO.getStationary_username());
            txtRole.setText(role.label);
            txtRegion.setText(regionName);
        } else if (role.label.equals(Role.CONTROLLER.label)) {
            controllerDAO = getController(username);

            if (controllerDAO == null) {
                toastShow("Controller with username " + username + " not found in DB");
                return;
            }

            String regionName = getRegionName(controllerDAO.getRegion_id());

            System.out.println("Lcak9N5O :: " + controllerDAO.getController_id());
            System.out.println("I1O4VchY :: " + controllerDAO.getController_username());
            System.out.println("4nVTPgzU :: " + controllerDAO.getRegion_id());
            System.out.println("njxe1qus :: " + regionName);
            System.out.println("sQr1FPE3 :: " + regionName.isEmpty());

            if (regionName.isEmpty()) {
                System.out.println("tmr3eTnG :: regionName is empty = " + regionName);
                toastShow("Region name is empty");
            }

            txtPersonID.setText(String.valueOf(controllerDAO.getController_id()));
            txtUsername.setText(controllerDAO.getController_username());
            txtRole.setText(role.label);
            txtRegion.setText(regionName);
        }

        btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "";
                String tableName = "";
                if (role.label.equals(Role.STATIONARY.label)) {
                    id = String.valueOf(stationaryDAO.getStationary_id());
                    tableName = role.label;
                } else {
                    id = String.valueOf(controllerDAO.getController_id());
                    tableName = role.label;
                }
                delete(view, id, tableName);
            }
        });
    }

    public void delete(View view, String id, String tableName) {
        long result = myDB.deleteOneRow(tableName, id);
        if (result == -1) {
            toastShow(role.label + " with id " + id + " not found in DB");
        } else {
            toastShow(role.label + " with id " + id + " successfully deleted");
        }

        Intent viewInfoAdminActivity = new Intent(getApplicationContext(), ViewInfoAdminActivity.class);
        startActivity(viewInfoAdminActivity); // after delete go to ViewInfoAdminActivity
    }

    public StationaryDAO getStationary(String username) {
        Cursor cursor = myDB.selectFromTable("select _id, stationary_username, region_id from stationary " +
                                                    "where stationary_username = '" + username + "';");
        StationaryDAO stationaryDAO = new StationaryDAO();
        if (cursor.getCount() == 0){
            return stationaryDAO;
        } else {
            while (cursor.moveToNext()) {
                stationaryDAO.setStationary_id(cursor.getInt(0));
                stationaryDAO.setStationary_username(cursor.getString(1));
                stationaryDAO.setRegion_id(cursor.getInt(2));
            }
        }
        return stationaryDAO;
    }

    public ControllerDAO getController(String username) {
        Cursor cursor = myDB.selectFromTable("select _id, controller_username, region_id from controller " +
                "where controller_username = '" + username + "';");
        ControllerDAO controllerDAO = new ControllerDAO();
        if (cursor.getCount() == 0){
            return controllerDAO;
        } else {
            while (cursor.moveToNext()) {
                controllerDAO.setController_id(cursor.getInt(0));
                controllerDAO.setController_username(cursor.getString(1));
                controllerDAO.setRegion_id(cursor.getInt(2));
            }
        }
        return controllerDAO;
    }

    private String getRegionName(int region_id) {
        Cursor cursor = myDB.selectFromTable("select region_name from region where _id = " + region_id);
        String regionName = "";
        if (cursor.getCount() == 0) {
            return regionName;
        } else {
            while (cursor.moveToNext()) {
                regionName = cursor.getString(0);
            }
        }
        return regionName;
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}