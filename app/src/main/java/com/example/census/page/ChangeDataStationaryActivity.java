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

import com.example.census.MainActivity;
import com.example.census.R;
import com.example.census.adapter.UserListAdapter;
import com.example.census.model.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.view.ViewInfoControllerActivity;

import java.util.ArrayList;
import java.util.List;

public class ChangeDataStationaryActivity extends AppCompatActivity {

    private Role   role;
    private String username;

    private Button btnLogOut;

    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data_stationary);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        System.out.println("5E5n78vR :: ChangeDataStationaryActivity");

        myDB = new MyDatabaseHelper(ChangeDataStationaryActivity.this);

        TextView noOnePassed = findViewById(R.id.noOnePassed);

        btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ChangeDataStationaryActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        List<String> userNames    = getCitizenUsernames(); // Populate list of CITIZEN usernames
        if (userNames.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noOnePassed.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noOnePassed.setVisibility(View.GONE);
            System.out.println("3S23Fe5f :: role : " + role);
            UserListAdapter adapter = new UserListAdapter(userNames, role);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public List<String> getCitizenUsernames() {
        Cursor       cursor           = myDB.selectFromTable("select username from citizen_login");
        List<String> citizenUsernames = new ArrayList<>();
        if (cursor.getCount() == 0){
            return citizenUsernames;
        } else {
            while (cursor.moveToNext()) {
                String citizenUsername = cursor.getString(0);
                citizenUsernames.add(citizenUsername);
            }
        }
        return citizenUsernames;
    }

}