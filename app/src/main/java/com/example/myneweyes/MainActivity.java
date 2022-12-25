package com.example.myneweyes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
//------------------------------------------------------------------------------------------------------
        // firebase notifications


        mDatabase = FirebaseDatabase.getInstance().getReference();
        boolean rfid = false;

        do{

            mDatabase.child("RFIsensor").child(String.valueOf(rfid)).get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {

                        if (rfid == false){
                            Toast.makeText(MainActivity.this, "Reading good",
                            Toast.LENGTH_SHORT).show();

                            pushNofification p = new pushNofification();
                            p.sendLocalNotification("Your class!",
                                    "You have arrived to your class");
                        }
                    }
                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    }
                }
            });

        } while (rfid != false);

//------------------------------------------------------------------------------------------------------



        signupButton = findViewById(R.id.mainSignUp_button);
        loginButton = findViewById(R.id.mainSignIn_button);



        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}