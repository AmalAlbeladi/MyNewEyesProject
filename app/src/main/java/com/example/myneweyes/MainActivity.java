package com.example.myneweyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private Button signupButton;
    private Button loginButton;

    private DatabaseReference mDatabase;
    private String readRFID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        signupButton = findViewById(R.id.mainSignUp_button);
        loginButton = findViewById(R.id.mainSignIn_button);

        // Set up signupButton onClickListener
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Set up loginButton onClickListener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set up Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Set up RFID sensor listener
        mDatabase.child("RFIDsensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readRFID = dataSnapshot.getValue(String.class);

                if (readRFID.equals("false")) {
                    sendLocalNotification("Your class!",
                            "You have arrived to your class!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });

        // Set up ultrasonic sensor listener
        mDatabase.child("UltrasonicSensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String readUltrasonic = dataSnapshot.getValue(String.class);

                if (readUltrasonic.equals("false")) {
                    sendLocalNotification("Obstacles!!", "There are obstacles front of you");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void sendLocalNotification(String notificationTitle, String notificationBody) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
}
