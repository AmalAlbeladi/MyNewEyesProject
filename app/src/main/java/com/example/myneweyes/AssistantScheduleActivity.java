package com.example.myneweyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AssistantScheduleActivity extends AppCompatActivity {

    Spinner spnr_Days,spnr_Days2,spnr_Days3,spnr_Times,spnr_Times2,spnr_Times3;
    EditText Course_name,Course_name2,Course_name3,Class_Room,Class_Room2,Class_Room3;
    String[] course_Days = {"U","M","T","W","R","U.T.R","M.W"};
    String[] course_Time = {"8:00 am","9:00 am","10:00 am","11:00 am"};
    String course_Day1,course_Day2,course_Day3,course_Time1,course_Time2,course_Time3;
    Button save,back;
    String temp_key;
    DatabaseReference root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_schedule);

        spnr_Days = findViewById(R.id.spnr_Days);
        spnr_Days2 = findViewById(R.id.spnr_Days2);
        spnr_Days3 = findViewById(R.id.spnr_Days3);

        spnr_Times = findViewById(R.id.spnr_Times);
        spnr_Times2 = findViewById(R.id.spnr_Times2);
        spnr_Times3 = findViewById(R.id.spnr_Times3);

        Course_name = findViewById(R.id.Course_name);
        Course_name2 = findViewById(R.id.Course_name2);
        Course_name3 = findViewById(R.id.Course_name3);

        Class_Room = findViewById(R.id.Class_Room);
        Class_Room2 = findViewById(R.id.Class_Room2);
        Class_Room3 = findViewById(R.id.Class_Room3);

        save = findViewById(R.id.Save);
        back = findViewById(R.id.Back);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, course_Days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnr_Days.setAdapter(dataAdapter);

        spnr_Days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Day1 = course_Days[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_Days2.setAdapter(dataAdapter);

        spnr_Days2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Day2 = course_Days[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_Days3.setAdapter(dataAdapter);

        spnr_Days3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Day3 = course_Days[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, course_Time);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnr_Times.setAdapter(dataAdapter2);

        spnr_Times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Time1 = course_Time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_Times2.setAdapter(dataAdapter2);

        spnr_Times2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Time2 = course_Time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_Times3.setAdapter(dataAdapter2);

        spnr_Times3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_Time3 = course_Time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root= FirebaseDatabase.getInstance().getReference().child("Courses");
                Query query = root.orderByChild("Assistant").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }

                        Map<String, Object> map1 = new HashMap<>();
                        temp_key = root.push().getKey();
                        root.updateChildren(map1);

                        Map<String, Object> map = new HashMap<>();
                        temp_key = root.push().getKey();
                        root.updateChildren(map);

                        DatabaseReference user = root.child(temp_key);

                        Map<String, Object> map2 = new HashMap<>();

                        if(!Course_name.getText().toString().isEmpty() || !Course_name2.getText().toString().isEmpty() || !Course_name3.getText().toString().isEmpty()) {
                            map2.put("Assistant", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }

                        if(!Course_name.getText().toString().isEmpty()) {
                            map2.put("Course_name1", Course_name.getText().toString());
                            map2.put("Class_Room1",  Class_Room.getText().toString());
                            map2.put("course_Day1",  course_Day1);
                            map2.put("course_Time1",  course_Time1);
                        }
                        if(!Course_name2.getText().toString().isEmpty()) {
                            map2.put("Course_name2", Course_name2.getText().toString());
                            map2.put("Class_Room2",  Class_Room2.getText().toString());
                            map2.put("course_Day2",  course_Day2);
                            map2.put("course_Time2",  course_Time2);
                        }
                        if(!Course_name3.getText().toString().isEmpty()) {
                            map2.put("Course_name3", Course_name3.getText().toString());
                            map2.put("Class_Room3",  Class_Room3.getText().toString());
                            map2.put("course_Day3",  course_Day3);
                            map2.put("course_Time3",  course_Time3);
                        }

                        user.updateChildren(map2);
                        Toast.makeText(AssistantScheduleActivity.this, "Schedule updated successfully!", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });





    }

    public void onClickBack(){
        Intent intent = new Intent(AssistantScheduleActivity.this, AssistantWelcomeActivity.class);
        startActivity(intent);
    }

}