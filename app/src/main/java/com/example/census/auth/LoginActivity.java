package com.example.census.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.database.CRUD;
import com.example.census.database.Database;
import com.example.census.database.PasswordToHash;
import com.example.census.model.Controller;
import com.example.census.model.Role;
import com.example.census.model.Stationary;
import com.example.census.token.TokenActivity;
import com.example.census.view.ViewInfoAdminActivity;

import java.sql.Connection;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private String hashedPassword;
    private Role role;

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

//        Intent tokenActivity = new Intent(this, TokenActivity.class); //todo undo comment after implement token

        if (role.label.equals(Role.ADMIN.label)) {
            if (checkIsAdmin(username, password)) {
                Intent tokenActivity = new Intent(this, ViewInfoAdminActivity.class); //todo remove after implement token
                tokenActivity.putExtra("username", username);
                tokenActivity.putExtra("role", role);
                startActivity(tokenActivity);
            } else {
                System.out.println("isNull!");
                toastShow("Admin does not found!");
            }
        } else if (role.label.equals(Role.STATIONARY.label)) {
            Stationary stationary = selectFromStationary(username);
            if (stationary.getStationary_username() == null) {
                System.out.println("isNull!");
                toastShow("Stationary does not found!");
                return;
            }

            if (stationary.getStationary_username().equals(username) && stationary.getStationary_password().equals(hashedPassword)) {
                Intent tokenActivity = new Intent(this, TokenActivity.class); //todo remove after implement token
                tokenActivity.putExtra("username", username);
                tokenActivity.putExtra("role", role);
                startActivity(tokenActivity);
            } else {
                System.out.println("isNull!");
                toastShow("Stationary does not found!");
            }
        } else if (role.label.equals(Role.CONTROLLER.label)) {
            Controller controller = selectFromController(username);
            if (controller.getController_username() == null) {
                System.out.println("isNull!");
                toastShow("Controller does not found!");
                return;
            }

            if (controller.getController_username().equals(username) && controller.getController_password().equals(hashedPassword)) {
                Intent tokenActivity = new Intent(this, TokenActivity.class); //todo remove after implement token
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

    private Stationary selectFromStationary(String username) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();

        return crud.selectFromStationary(connection, "select * from stationary " +
                "where stationary_username = '" + username + "'");
    }

    private Controller selectFromController(String username) {
        Database db = new Database();
        Connection connection = db.connect();
        CRUD crud = new CRUD();

        return crud.selectFromController(connection, "select * from controller " +
                "where controller_username = '" + username + "'");
    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}