package com.example.census.view;

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
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewInfoControllerActivity extends AppCompatActivity {

    private Role   role;
    private String username;

    private Button btnLogOut;

    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_controller);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        myDB = new MyDatabaseHelper(ViewInfoControllerActivity.this);

        TextView noOnePassed = findViewById(R.id.noOnePassed);

        btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ViewInfoControllerActivity.this, MainActivity.class);
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
            UserListAdapter adapter = new UserListAdapter(userNames, role, Action.VIEW);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public List<String> getCitizenUsernames() {
        Cursor cursor = myDB.selectFromTable("select username from citizen_login");
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