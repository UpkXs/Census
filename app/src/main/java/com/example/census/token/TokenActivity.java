package com.example.census.token;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import java.util.concurrent.ThreadLocalRandom;

public class TokenActivity extends AppCompatActivity {

    private Role     role;
    private String   username;
    private Action   action;
    private int      code;
    private EditText inputMobile;
    private Button   btnGetToken;

    NotificationManagerCompat notificationManagerCompat;
    Notification              notification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");
            role = (Role) extras.get("role");
            action = (Action) extras.get("action");
        }

        inputMobile = findViewById(R.id.inputMobile);
        btnGetToken = findViewById(R.id.btnGetToken);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myCh")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Verification code: ")
                .setContentText(String.valueOf(code));

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobile.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Enter correct phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("code === " + code);
                push(view);
            }
        });

    }

    public void push(View view) {
        notificationManagerCompat.notify(1, notification);

        Intent verifyTokenActivity = new Intent(getApplicationContext(), VerifyTokenActivity.class);
        verifyTokenActivity.putExtra("role", role);
        verifyTokenActivity.putExtra("username", username);
        verifyTokenActivity.putExtra("action", action);
        verifyTokenActivity.putExtra("mobile", inputMobile.getText().toString());
        verifyTokenActivity.putExtra("code", code);
        startActivity(verifyTokenActivity);
    }
}
