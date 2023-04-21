package com.example.census.biometricAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.model.Role;
import com.example.census.token.TokenActivity;
import com.example.census.view.ViewInfoAdminActivity;

import java.util.concurrent.Executor;

public class FingerTouchActivity extends AppCompatActivity {

    private Role role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_touch);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            username = (String) extras.get("username");
        }

        TextView msg_txt = findViewById(R.id.txt_msg);
        Button login_btn = findViewById(R.id.login_btn);

        BiometricManager biometricManager = BiometricManager.from(this);
        Intent tokenActivity = new Intent(this, TokenActivity.class);
        tokenActivity.putExtra("role", role);
        tokenActivity.putExtra("username", username);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_txt.setText("You can use the fingerprint sensor to login");
                msg_txt.setTextColor(Color.parseColor("#fafafa"));
                System.out.println("asdfgh :: You can use the fingerprint sensor to login");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_txt.setText("the device don't have a fingerprint sensor");
                login_btn.setVisibility(View.GONE);

                toastShow("The device don't have a fingerprint sensor. \n" +
                        "Now starts verification with token");
                System.out.println("qwerty :: The device don't have a fingerprint sensor. Now starts verification with token");
                startActivity(tokenActivity);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msg_txt.setText("the biometric sensors is currently unavailable");
                login_btn.setVisibility(View.GONE);

                toastShow("The biometric sensors is currently unavailable. \n" +
                        "Now starts verification with token");
                System.out.println("zxcvbn :: The biometric sensors is currently unavailable. Now starts verification with token");
                startActivity(tokenActivity);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_txt.setText("your device don't have any fingerprint saved, please check your security settings");
                login_btn.setVisibility(View.GONE);

                toastShow("Your device don't have any fingerprint saved, please check your security settings. \n" +
                        "Now starts verification with token");
                System.out.println("mnbvcx :: Your device don't have any fingerprint saved, please check your security settings. Now starts verification with token");
                startActivity(tokenActivity);
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(FingerTouchActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Verify Success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FingerTouchActivity.this, ViewInfoAdminActivity.class); // todo change to view page citizen
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        try {
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Finger Touch")
                    .setDescription("Use your fingerprint to pass verification")
                    .build();

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biometricPrompt.authenticate(promptInfo);
                }
            });
        } catch (Exception e) {
            System.out.println("ds3iw1f :: error : " + e.getMessage());
            toastShow("The biometric sensors is currently unavailable. \n" +
                    "Now starts verification with token");
            startActivity(tokenActivity);
        }


    }

    public void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}