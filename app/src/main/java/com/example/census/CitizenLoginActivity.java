package com.example.census;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.sql.Connection;
import java.util.List;

public class CitizenLoginActivity extends AppCompatActivity {

    private final Role role = Role.CITIZEN;
    private CitizenLogin citizenLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_login);

        String loginPage = role.label + " Login Page";

        TextView textView = findViewById(R.id.roleId);
        textView.setText(loginPage);

        TextView createAccount = (TextView) findViewById(R.id.createAccount);
        createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegistrationActivity(view);
            }
        });

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void goToRegistrationActivity(View v) {
        Intent registrationActivity = new Intent(this, CitizenRegistrationActivity.class);
        startActivity(registrationActivity);
    }

    public void login(View view) {
        EditText username = findViewById(R.id.inputUsername);
        EditText password = findViewById(R.id.inputPassword);

        PasswordToHash passwordToHash = new PasswordToHash();
        String hashedPassword = passwordToHash.doHash(String.valueOf(password.getText()));

        citizenLogin = selectFromCitizenLogin(username.getText().toString());

        if (citizenLogin.getUsername() == null) {
            System.out.println("isNull!");
            toastShow("Account does not found!");
            return;
        }

        if (citizenLogin.getUsername().contentEquals(username.getText()) && citizenLogin.getPassword().equals(hashedPassword)) {
            Intent tokenActivity = new Intent(this, TokenActivity.class);
            tokenActivity.putExtra("username", citizenLogin.getUsername());
            tokenActivity.putExtra("role", role);
            startActivity(tokenActivity);
        } else {
            System.out.println("isNull!");
            toastShow("Account does not found!");
        }
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public CitizenLogin selectFromCitizenLogin(String username) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();
        System.out.println("db = " + db);
        System.out.println("connection = " + connection);
        return crud.selectFromCitizenLogin(connection,
                "select * from citizen_login where username = '" + username + "'");
    }
}