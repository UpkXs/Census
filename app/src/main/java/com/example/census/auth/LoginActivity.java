package com.example.census.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.database.PasswordToHash;
import com.example.census.model.Controller;
import com.example.census.enums.Role;
import com.example.census.model.Stationary;
import com.example.census.sqliteDatabase.MyDatabaseHelper;
import com.example.census.token.TokenActivity;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private String hashedPassword;
    private Role role;

    private MyDatabaseHelper myDB;

    private boolean isToken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
        }

        String loginPage = role.label + " Login Page";

        TextView textView = findViewById(R.id.roleId);
        textView.setText(loginPage);

        myDB = new MyDatabaseHelper(LoginActivity.this);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

    }

    public void login(View view) {
        EditText usernameEditText = findViewById(R.id.inputUsername);
        username = String.valueOf(usernameEditText.getText());
        System.out.println("username " + username);

        EditText passwordEditText = findViewById(R.id.inputPassword);
        password = String.valueOf(passwordEditText.getText());
        System.out.println("password " + password);

        PasswordToHash passwordToHash = new PasswordToHash();
        hashedPassword = passwordToHash.doHash(password);
        System.out.println("hashedPassword = " + hashedPassword);

        Intent tokenActivity = new Intent(this, TokenActivity.class);

        if (role.label.equals(Role.ADMIN.label)) {
            if (checkIsAdmin(username, password)) {
                tokenActivity.putExtra("username", username);
                tokenActivity.putExtra("role", role);
                startActivity(tokenActivity);
            } else {
                System.out.println("isNull!");
                toastShow("Admin does not found!");
            }
        } else if (role.label.equals(Role.STATIONARY.label)) {
            Stationary stationary = getStationary(username);
            System.out.println("3Xt5oz5o :: username : " + stationary.getStationary_username());
            System.out.println("LP3efY66 :: password : " + stationary.getStationary_password());
            if (stationary.getStationary_username() == null) {
                System.out.println("isNull!");
                toastShow("Stationary does not found!");
                return;
            }

            if (stationary.getStationary_username().equals(username) && stationary.getStationary_password().equals(hashedPassword)) {
                tokenActivity.putExtra("username", username);
                tokenActivity.putExtra("role", role);
                startActivity(tokenActivity);
            } else {
                System.out.println("isNull!");
                System.out.println("34t3443!");
                toastShow("Stationary does not found!");
            }
        } else if (role.label.equals(Role.CONTROLLER.label)) {
            Controller controller = getController(username);
            System.out.println("GniLEb0j :: username : " + controller.getController_username());
            System.out.println("z0xrUVA6 :: password : " + controller.getController_password());
            System.out.println("TIov04Cr :: hashedPassword : " + passwordToHash.doHash(controller.getController_password()));
            if (controller.getController_username() == null) {
                System.out.println("isNull!");
                toastShow("Controller does not found!");
                return;
            }

            if (controller.getController_username().equals(username) && controller.getController_password().equals(hashedPassword)) {
                tokenActivity.putExtra("username", username);
                tokenActivity.putExtra("role", role);
                startActivity(tokenActivity);
            } else {
                System.out.println("isNull!");
                toastShow("Controller does not found!");
            }
        }
    }

    private boolean checkIsAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    public Stationary getStationary(String username) {
        Cursor cursor = myDB.selectFromTable("select * from stationary where stationary_username = '" + username + "'");
        Stationary stationary = new Stationary();
        if (cursor.getCount() == 0) {
            return stationary;
        } else {
            while (cursor.moveToNext()) {
                stationary.setStationary_id(cursor.getInt(0));
                stationary.setStationary_username(cursor.getString(1));
                stationary.setStationary_password(cursor.getString(2));
                stationary.setStationary_apikey(cursor.getInt(3));
                stationary.setRegion_id(cursor.getInt(4));
            }
        }
        return stationary;
    }

    public Controller getController(String username) {
        Cursor cursor = myDB.selectFromTable("select * from controller where controller_username = '" + username + "'");
        Controller controller = new Controller();
        if (cursor.getCount() == 0) {
            return controller;
        } else {
            while (cursor.moveToNext()) {
                controller.setController_id(cursor.getInt(0));
                controller.setController_name(cursor.getString(1));
                controller.setController_username(cursor.getString(2));
                controller.setController_password(cursor.getString(3));
                controller.setController_apikey(cursor.getInt(4));
                controller.setRegion_id(cursor.getInt(5));
            }
        }
        return controller;
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}