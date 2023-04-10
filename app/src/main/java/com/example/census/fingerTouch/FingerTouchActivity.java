package com.example.census.fingerTouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.R;

import java.util.concurrent.Executor;

public class FingerTouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_touch);

        TextView msg_txt = findViewById(R.id.txt_msg);
        Button login_btn = findViewById(R.id.login_btn);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_txt.setText("You can use the fingerprint sensor to login");
                msg_txt.setTextColor(Color.parseColor("#fafafa"));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_txt.setText("the device don't have a fingerprint sensor"); //todo add tokenActivity here
                login_btn.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msg_txt.setText("the biometric sensors is currently unavailable");
                login_btn.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_txt.setText("your device don't have any fingerprint saved, please check your securty settings");
                login_btn.setVisibility(View.GONE);
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
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("User your fingerprint to login to your app")
                .setNegativeButtonText("Cancel")
                .build();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}