package com.example.myneweyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText Email,Username,Phone,Email2;
    Button btn;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users_Register_Info");

        Email = findViewById(R.id.Email);
        Username = findViewById(R.id.Username);
        Phone = findViewById(R.id.Phone);
        Email2 = findViewById(R.id.EmailOfRelated);
        btn = findViewById(R.id.Save);


        Query query = mDatabase.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Username.setText(snapshot.child("userName").getValue(String.class));
                    Email.setText(snapshot.child("email").getValue(String.class));
                    Phone.setText(snapshot.child("phoneNumber").getValue(String.class));
                    Email2.setText(snapshot.child("related").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = mDatabase.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            snapshot.getRef().child("userName").setValue(Username.getText().toString());
                            snapshot.getRef().child("phoneNumber").setValue(Phone.getText().toString());
                            snapshot.getRef().child("related").setValue(Email2.getText().toString());
                            Toast.makeText(Profile.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

    }
}