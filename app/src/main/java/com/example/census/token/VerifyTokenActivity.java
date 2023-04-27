package com.example.census.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import com.example.census.interview.OnlineInterviewActivity;
import com.example.census.view.ViewInfoAdminActivity;
import com.example.census.view.ViewInfoCitizenActivity;
import com.example.census.view.ViewInfoControllerActivity;
import com.example.census.view.ViewInfoStationaryActivity;

import java.util.concurrent.ThreadLocalRandom;

public class VerifyTokenActivity extends AppCompatActivity {

    private Role     role;
    private String   username;
    private Action   action;
    private String   mobile;
    private int      code;
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private Button   btnVerify;

    NotificationManagerCompat notificationManagerCompat;
    Notification              notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_token);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role     = (Role) extras.get("role");
            username = (String) extras.get("username");
            action = (Action) extras.get("action");
            mobile   = (String) extras.get("mobile");
            code     = (int) extras.get("code");
        }

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format("+7%s", mobile));

        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        setupCodeInputs();

        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1.getText().toString().trim().isEmpty()
                || inputCode2.getText().toString().trim().isEmpty()
                || inputCode3.getText().toString().trim().isEmpty()
                || inputCode4.getText().toString().trim().isEmpty()
                || inputCode5.getText().toString().trim().isEmpty()
                || inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyTokenActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String inputCode = inputCode1.getText().toString() +
                        inputCode2.getText().toString() +
                        inputCode3.getText().toString() +
                        inputCode4.getText().toString() +
                        inputCode5.getText().toString() +
                        inputCode6.getText().toString();

                System.out.println("5tk2AK0d :: outerCode" + code);
                System.out.println("3m0Qhs9Q :: innerCode" + inputCode);
                System.out.println(String.valueOf(code).equals(inputCode));
                System.out.println(role.label);
                System.out.println(Role.ADMIN);
                if (String.valueOf(code).equals(inputCode)) {
                    if (role.label.equals(Role.ADMIN.label)) {
                        Intent viewInfoAdminActivity = new Intent(getApplicationContext(), ViewInfoAdminActivity.class);
                        startActivity(viewInfoAdminActivity);
                    } else if (role.label.equals(Role.STATIONARY.label)) {
                        Intent viewInfoStationaryActivity = new Intent(getApplicationContext(), ViewInfoStationaryActivity.class);
                        viewInfoStationaryActivity.putExtra("role", role);
                        viewInfoStationaryActivity.putExtra("username", username);
                        startActivity(viewInfoStationaryActivity);
                    } else if (role.label.equals(Role.CONTROLLER.label)) {
                        Intent viewInfoControllerActivity = new Intent(getApplicationContext(), ViewInfoControllerActivity.class);
                        viewInfoControllerActivity.putExtra("role", role);
                        viewInfoControllerActivity.putExtra("username", username);
                        startActivity(viewInfoControllerActivity);
                    } else if (role.label.equals(Role.CITIZEN.label) && action.label.equals(Action.LOGIN.label)) {
                        Intent viewInfoCitizenActivity = new Intent(getApplicationContext(), ViewInfoCitizenActivity.class);
                        viewInfoCitizenActivity.putExtra("role", role);
                        viewInfoCitizenActivity.putExtra("action", action);
                        viewInfoCitizenActivity.putExtra("username", username);
                        startActivity(viewInfoCitizenActivity);
                    } else if (role.label.equals(Role.CITIZEN.label) && action.label.equals(Action.REGISTER.label)) {
                        Intent onlineInterviewActivity = new Intent(getApplicationContext(), OnlineInterviewActivity.class);
                        onlineInterviewActivity.putExtra("role", role);
                        onlineInterviewActivity.putExtra("action", action);
                        onlineInterviewActivity.putExtra("username", username);
                        startActivity(onlineInterviewActivity);
                    }
                } else {
                    Toast.makeText(VerifyTokenActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.textResendCode).setOnClickListener(new View.OnClickListener() { // todo clear edit text inputs
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_HIGH);

                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }

                code = ThreadLocalRandom.current().nextInt(100000, 1000000); // get code(6 digit code)

                NotificationCompat.Builder builder = new NotificationCompat.Builder(VerifyTokenActivity.this, "myCh")
                        .setSmallIcon(android.R.drawable.stat_notify_sync)
                        .setContentTitle("Verification code: ")
                        .setContentText(String.valueOf(code));

                notification = builder.build();

                notificationManagerCompat = NotificationManagerCompat.from(VerifyTokenActivity.this);

                System.out.println("code === " + code);
                notificationManagerCompat.notify(1, notification);
            }
        });
    }

    private void setupCodeInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}
