package com.example.myneweyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EmergncyContact extends AppCompatActivity {

    TextView phoneNumber;
    Button btn;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergncy_contact);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users_Register_Info");

        phoneNumber = findViewById(R.id.phoneNumber);
        btn = findViewById(R.id.Call);
        btn.setEnabled(false);


        Query query = mDatabase.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String Email =  snapshot.child("related").getValue(String.class);
                    Query query2 = mDatabase.orderByChild("email").equalTo(Email);
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot2: snapshot.getChildren()) {
                                phoneNumber.setText(snapshot2.child("phoneNumber").getValue(String.class));
                                btn.setEnabled(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
                Intent callIntent =new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
                startActivity(callIntent);
            }
        });
    }
}