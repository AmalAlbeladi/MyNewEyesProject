package com.example.myneweyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentWelcomeActivity extends AppCompatActivity {
    private Button signOutButton,Schedule_button,Profile_button,EmergncyContact_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);

        signOutButton = findViewById(R.id.signOut_button);
        Schedule_button = findViewById(R.id.Schedule_button);
        Profile_button = findViewById(R.id.Profile_button);
        EmergncyContact_button = findViewById(R.id.EmergncyContact_button);


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentWelcomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        Schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentWelcomeActivity.this, StudentScheduleActivity.class);
                startActivity(intent);
            }
        });

        Profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentWelcomeActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        EmergncyContact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentWelcomeActivity.this, EmergncyContact.class);
                startActivity(intent);
            }
        });


    }

}