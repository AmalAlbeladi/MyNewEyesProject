package com.example.myneweyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AssistantWelcomeActivity extends AppCompatActivity {

    Button Schedule_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_welcome);

        Schedule_button = findViewById(R.id.Schedule_button);

        Schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantWelcomeActivity.this,AssistantScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickBack(){
        Intent intent = new Intent(AssistantWelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

}