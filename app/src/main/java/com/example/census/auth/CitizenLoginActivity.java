package com.example.census.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.database.PasswordToHash;
import com.example.census.enums.Action;
import com.example.census.model.CitizenLogin;
import com.example.census.enums.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.token.TokenActivity;

public class CitizenLoginActivity extends AppCompatActivity {

    private final Role         role = Role.CITIZEN;
    private final Action       action = Action.LOGIN;
    private       CitizenLogin citizenLogin;

    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_login);

        String loginPage = role.label + " Login Page";

        TextView textView = findViewById(R.id.roleId);
        textView.setText(loginPage);

        TextView createAccount = (TextView) findViewById(R.id.createAccount);
        createAccount.setPaintFlags(createAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        myDB = new MyDatabaseHelper(CitizenLoginActivity.this);

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

        citizenLogin = getCitizenLogin(username.getText().toString());

        if (citizenLogin.getUsername() == null) {
            System.out.println("isNull!");
            toastShow("Account does not found!");
            return;
        }

        if (citizenLogin.getUsername().contentEquals(username.getText()) && citizenLogin.getPassword().equals(hashedPassword)) {
            Intent tokenActivity = new Intent(this, TokenActivity.class);
            tokenActivity.putExtra("username", username.getText().toString());
            tokenActivity.putExtra("role", role);
            tokenActivity.putExtra("action", action);
            startActivity(tokenActivity);
        } else {
            System.out.println("isNull!");
            toastShow("Account does not found!");
        }
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
}