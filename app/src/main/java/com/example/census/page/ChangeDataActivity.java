package com.example.census.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.census.R;
import com.example.census.adapter.UserListAdapter;
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoAdminActivity;

import java.util.ArrayList;
import java.util.List;

public class ChangeDataActivity extends AppCompatActivity {

    private Button btnGoToViewAdminPage;

    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        myDB = new MyDatabaseHelper(ChangeDataActivity.this);

        TextView noOnePassed = findViewById(R.id.noOnePassed);

        btnGoToViewAdminPage = findViewById(R.id.btnGoToViewAdminPage);

        btnGoToViewAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewInfoAdminActivity = new Intent(ChangeDataActivity.this, ViewInfoAdminActivity.class);
                startActivity(viewInfoAdminActivity);
            }
        });

        TextView txtStationary = findViewById(R.id.txtStationary);
        TextView txtController = findViewById(R.id.txtController);

        RecyclerView recyclerViewStationary = findViewById(R.id.recycler_view_stationary);
        RecyclerView recyclerViewController = findViewById(R.id.recycler_view_controller);

        List<String> stationaryUserNames    = getStationaryUsernames(); // Populate list of Stationary usernames
        List<String> controllerUserNames    = getControllerUsernames(); // Populate list of Controller usernames

        if (stationaryUserNames.isEmpty() && controllerUserNames.isEmpty()) {
            noOnePassed.setVisibility(View.VISIBLE);
        }

        if (stationaryUserNames.isEmpty()) {
            txtStationary.setVisibility(View.GONE);
            recyclerViewStationary.setVisibility(View.GONE);
        } else {
            txtStationary.setVisibility(View.VISIBLE);
            recyclerViewStationary.setVisibility(View.VISIBLE);

            noOnePassed.setVisibility(View.GONE);

            UserListAdapter adapterStationary = new UserListAdapter(stationaryUserNames, Role.STATIONARY, Action.CHANGE_ADMIN);
            recyclerViewStationary.setAdapter(adapterStationary);
            recyclerViewStationary.setLayoutManager(new LinearLayoutManager(this));
        }

        if (controllerUserNames.isEmpty()) {
            txtController.setVisibility(View.GONE);
            recyclerViewController.setVisibility(View.GONE);
        } else {
            txtController.setVisibility(View.VISIBLE);
            recyclerViewController.setVisibility(View.VISIBLE);

            noOnePassed.setVisibility(View.GONE);

            UserListAdapter adapterController = new UserListAdapter(controllerUserNames, Role.CONTROLLER, Action.CHANGE_ADMIN);
            recyclerViewController.setAdapter(adapterController);
            recyclerViewController.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public List<String> getStationaryUsernames() {
        Cursor cursor = myDB.selectFromTable("select stationary_username from stationary");
        List<String> stationaryUsernames = new ArrayList<>();
        if (cursor.getCount() == 0){
            return stationaryUsernames;
        } else {
            while (cursor.moveToNext()) {
                String stationaryUsername = cursor.getString(0);
                stationaryUsernames.add(stationaryUsername);
            }
        }
        return stationaryUsernames;
    }

    public List<String> getControllerUsernames() {
        Cursor cursor = myDB.selectFromTable("select controller_username from controller");
        List<String> controllerUsernames = new ArrayList<>();
        if (cursor.getCount() == 0){
            return controllerUsernames;
        } else {
            while (cursor.moveToNext()) {
                String controllerUsername = cursor.getString(0);
                controllerUsernames.add(controllerUsername);
            }
        }
        return controllerUsernames;
    }
}