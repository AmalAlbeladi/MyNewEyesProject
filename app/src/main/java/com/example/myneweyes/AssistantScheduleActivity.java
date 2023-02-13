package com.example.myneweyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

    private DatabaseReference mDatabase;
    Spinner spnr_Days,spnr_Days2,spnr_Days3,spnr_Times,spnr_Times2,spnr_Times3;
    EditText Course_name,Course_name2,Course_name3,Class_Room,Class_Room2,Class_Room3;
    String[] course_Days = {"U","M","T","W","R","U.T.R","M.W"};
    String[] course_Time = {"8:00 am","9:00 am","10:00 am","11:00 am", "6:06 pm"};
    String course_Day1,course_Day2,course_Day3,course_Time1,course_Time2,course_Time3;
    Button save,Remove;
    ImageView delete1,delete2,delete3;
    String temp_key;
    DatabaseReference root;
    String CourseName1,CourseName2,CourseName3,ClassRoom1,ClassRoom2,ClassRoom3,courseDay1,courseDay2,courseDay3,courseTime1,courseTime2,courseTime3;
    TextView txt_CourseName1,txt_CourseName2,txt_CourseName3,txt_ClassRoom1,txt_ClassRoom2,txt_ClassRoom3,txt_Days1,txt_Days2,txt_Days3,txt_Times1,txt_Times2,txt_Times3;
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

        txt_CourseName1 = findViewById(R.id.txt_CourseName);
        txt_CourseName2 = findViewById(R.id.txt_CourseName2);
        txt_CourseName3 = findViewById(R.id.txt_CourseName3);

        txt_ClassRoom1 = findViewById(R.id.txt_ClassRoom);
        txt_ClassRoom2 = findViewById(R.id.txt_ClassRoom2);
        txt_ClassRoom3 = findViewById(R.id.txt_ClassRoom3);

        txt_Days1 = findViewById(R.id.txt_Days);
        txt_Days2 = findViewById(R.id.txt_Days2);
        txt_Days3 = findViewById(R.id.txt_Days3);

        txt_Times1 = findViewById(R.id.txt_Times);
        txt_Times2 = findViewById(R.id.txt_Times2);
        txt_Times3 = findViewById(R.id.txt_Times3);

        delete1 = findViewById(R.id.delete1);
        delete2 = findViewById(R.id.delete2);
        delete3 = findViewById(R.id.delete3);

        save = findViewById(R.id.Save);
        Remove = findViewById(R.id.Remove);

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

                            txt_CourseName1.setText(Course_name.getText().toString());
                            txt_ClassRoom1.setText(Class_Room.getText().toString());
                            txt_Days1.setText(course_Day1);
                            txt_Times1.setText(course_Time1);
                        }
                        if(!Course_name2.getText().toString().isEmpty()) {
                            map2.put("Course_name2", Course_name2.getText().toString());
                            map2.put("Class_Room2",  Class_Room2.getText().toString());
                            map2.put("course_Day2",  course_Day2);
                            map2.put("course_Time2",  course_Time2);

                            txt_CourseName2.setText(Course_name2.getText().toString());
                            txt_ClassRoom2.setText(Class_Room2.getText().toString());
                            txt_Days2.setText(course_Day2);
                            txt_Times2.setText(course_Time2);
                        }
                        if(!Course_name3.getText().toString().isEmpty()) {
                            map2.put("Course_name3", Course_name3.getText().toString());
                            map2.put("Class_Room3",  Class_Room3.getText().toString());
                            map2.put("course_Day3",  course_Day3);
                            map2.put("course_Time3",  course_Time3);

                            txt_CourseName3.setText(Course_name3.getText().toString());
                            txt_ClassRoom3.setText(Class_Room3.getText().toString());
                            txt_Days3.setText(course_Day3);
                            txt_Times3.setText(course_Time3);
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

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root= FirebaseDatabase.getInstance().getReference().child("Courses");
                Query query = root.orderByChild("Assistant").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                            txt_CourseName1.setText("");
                            txt_CourseName2.setText("");
                            txt_CourseName3.setText("");
                            txt_ClassRoom1.setText("");
                            txt_ClassRoom2.setText("");
                            txt_ClassRoom3.setText("");
                            txt_Days1.setText("");
                            txt_Days2.setText("");
                            txt_Days3.setText("");
                            txt_Times1.setText("");
                            txt_Times2.setText("");
                            txt_Times3.setText("");
                            Toast.makeText(AssistantScheduleActivity.this, "Done ,Removed data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse("Course_name1","Class_Room1","course_Day1","course_Time1");
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse("Course_name2","Class_Room2","course_Day2","course_Time2");
            }
        });

        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse("Course_name3","Class_Room3","course_Day3","course_Time3");
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Users_Register_Info").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String Email =  snapshot.child("related").getValue(String.class);
                    Query query2 = mDatabase.child("Courses").orderByChild("Assistant").equalTo(Email);
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot2: snapshot.getChildren()) {

                                if(snapshot2.child("Course_name1").exists()) {
                                    CourseName1 = snapshot2.child("Course_name1").getValue(String.class);
                                    ClassRoom1 = snapshot2.child("Class_Room1").getValue(String.class);
                                    courseDay1 = snapshot2.child("course_Day1").getValue(String.class);
                                    courseTime1 = snapshot2.child("course_Time1").getValue(String.class);
                                }
                                if(snapshot2.child("Course_name2").exists()) {
                                    CourseName2 = snapshot2.child("Course_name2").getValue(String.class);
                                    ClassRoom2 = snapshot2.child("Class_Room2").getValue(String.class);
                                    courseDay2 = snapshot2.child("course_Day2").getValue(String.class);
                                    courseTime2 = snapshot2.child("course_Time2").getValue(String.class);
                                }
                                if(snapshot2.child("Course_name2").exists()) {
                                    CourseName3 = snapshot2.child("Course_name3").getValue(String.class);
                                    ClassRoom3 = snapshot2.child("Class_Room3").getValue(String.class);
                                    courseDay3 = snapshot2.child("course_Day3").getValue(String.class);
                                    courseTime3 = snapshot2.child("course_Time3").getValue(String.class);
                                }



                                txt_CourseName1.setText(CourseName1);
                                txt_CourseName2.setText(CourseName2);
                                txt_CourseName3.setText(CourseName3);
                                txt_ClassRoom1.setText(ClassRoom1);
                                txt_ClassRoom2.setText(ClassRoom2);
                                txt_ClassRoom3.setText(ClassRoom3);
                                txt_Days1.setText(courseDay1);
                                txt_Days2.setText(courseDay2);
                                txt_Days3.setText(courseDay3);
                                txt_Times1.setText(courseTime1);
                                txt_Times2.setText(courseTime2);
                                txt_Times3.setText(courseTime3);
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

    }

    public void deleteCourse(String CourseName,String CourseRoom,String Day,String Time) {
        root= FirebaseDatabase.getInstance().getReference().child("Courses");
        Query query = root.orderByChild("Assistant").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(CourseName.equals("Course_name1")) {
                        txt_CourseName1.setText("");
                        txt_ClassRoom1.setText("");
                        txt_Days1.setText("");
                        txt_Times1.setText("");
                    }
                    else if(CourseName.equals("Course_name2")) {
                        txt_CourseName2.setText("");
                        txt_ClassRoom2.setText("");
                        txt_Days2.setText("");
                        txt_Times2.setText("");
                    }
                    else if(CourseName.equals("Course_name3")) {
                        txt_CourseName3.setText("");
                        txt_ClassRoom3.setText("");
                        txt_Days3.setText("");
                        txt_Times3.setText("");
                    }

                    snapshot.getRef().child(CourseName).removeValue();
                    snapshot.getRef().child(CourseRoom).removeValue();
                    snapshot.getRef().child(Day).removeValue();
                    snapshot.getRef().child(Time).removeValue();
                    Toast.makeText(AssistantScheduleActivity.this, CourseName, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void onClickBack(){
        Intent intent = new Intent(AssistantScheduleActivity.this, AssistantWelcomeActivity.class);
        startActivity(intent);
    }

}