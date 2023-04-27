package com.example.census.interview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;

public class OnlineInterviewLoginPartActivity extends AppCompatActivity {

    private String username;

    private EditText editTxtTin;
    private EditText editTxtFullName;
    private EditText editTxtRegion;

    private Button btnGoToTheNextPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_interview_login_part);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = (String) extras.get("username");

            System.out.println("bkE5lAcB :: username : " + username);
        }

        editTxtTin = findViewById(R.id.tin);
        editTxtFullName = findViewById(R.id.fullName);
        editTxtRegion = findViewById(R.id.region);

        btnGoToTheNextPart = findViewById(R.id.btnGoToTheNextPart);

        btnGoToTheNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTheNextPart(view);
            }
        });

    }

    private void goToTheNextPart(View view) {
        if (editTxtTin.getText().toString().trim().isEmpty() ||
                editTxtFullName.getText().toString().trim().isEmpty() ||
                editTxtRegion.getText().toString().trim().isEmpty()) {
            System.out.println("cmV84h7M :: OnlineInterviewLoginPartActivity : Some field is not entered. Please enter all the details.");
            toastShow("Some field is not entered. Please enter all the details.");
        }
    }

    private void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}