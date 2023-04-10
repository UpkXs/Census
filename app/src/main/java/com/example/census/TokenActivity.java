package com.example.census;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.census.model.Role;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class TokenActivity extends AppCompatActivity {

    private Role role;
    private String username;
    private EditText edToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken token;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");
            role = (Role) extras.get("role");
        }

        final EditText inputMobile = findViewById(R.id.inputMobile);
        Button btnGetToken = findViewById(R.id.btnGetToken);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(View.GONE);
                btnGetToken.setVisibility(View.VISIBLE);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                btnGetToken.setVisibility(View.VISIBLE);
                Toast.makeText(TokenActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                progressBar.setVisibility(View.GONE);
                btnGetToken.setVisibility(View.VISIBLE);

                token = forceResendingToken;

                Intent verifyTokenActivity = new Intent(getApplicationContext(), VerifyTokenActivity.class);
                verifyTokenActivity.putExtra("mobile", inputMobile.getText().toString());
                verifyTokenActivity.putExtra("verificationId", verificationId);
                startActivity(verifyTokenActivity);
            }
        };

        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+7" + inputMobile.getText().toString().trim();
                if(phoneNumber.equals("+7") || phoneNumber.length() < 12) {
                    Toast.makeText(TokenActivity.this, "Enter Mobile", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btnGetToken.setVisibility(View.INVISIBLE);

                System.out.println("phone number :: == " + phoneNumber);
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(1L, TimeUnit.SECONDS)
                                .setActivity(TokenActivity.this)
                                .setCallbacks(mCallbacks)
                                .setForceResendingToken(token)
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });
    }
}