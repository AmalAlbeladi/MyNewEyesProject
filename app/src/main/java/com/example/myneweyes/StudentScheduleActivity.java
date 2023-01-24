package com.example.myneweyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StudentScheduleActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String Course_name1,Course_name2,Course_name3,Class_Room1,Class_Room2,Class_Room3,course_Day1,course_Day2,course_Day3,course_Time1,course_Time2,course_Time3;
    TextView txt_CourseName1,txt_CourseName2,txt_CourseName3,txt_ClassRoom1,txt_ClassRoom2,txt_ClassRoom3,txt_Days1,txt_Days2,txt_Days3,txt_Times1,txt_Times2,txt_Times3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Users_Register_Info").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String Email =  snapshot.child("related").getValue(String.class);
                    Query query2 = mDatabase.child("Courses").orderByChild("Assistant").equalTo(Email);
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot2: snapshot.getChildren()) {

                                Course_name1 = snapshot2.child("Course_name1").getValue(String.class);
                                Course_name2 = snapshot2.child("Course_name2").getValue(String.class);
                                Course_name3 = snapshot2.child("Course_name3").getValue(String.class);
                                Class_Room1 = snapshot2.child("Class_Room1").getValue(String.class);
                                Class_Room2 = snapshot2.child("Class_Room2").getValue(String.class);
                                Class_Room3 = snapshot2.child("Class_Room3").getValue(String.class);
                                course_Day1 = snapshot2.child("course_Day1").getValue(String.class);
                                course_Day2 = snapshot2.child("course_Day2").getValue(String.class);
                                course_Day3 = snapshot2.child("course_Day3").getValue(String.class);
                                course_Time1 = snapshot2.child("course_Time1").getValue(String.class);
                                course_Time2 = snapshot2.child("course_Time2").getValue(String.class);
                                course_Time3 = snapshot2.child("course_Time3").getValue(String.class);


                                Toast.makeText(StudentScheduleActivity.this,Course_name1 , Toast.LENGTH_SHORT).show();
                                txt_CourseName1.setText(Course_name1);
                                txt_CourseName2.setText(Course_name2);
                                txt_CourseName3.setText(Course_name3);
                                txt_ClassRoom1.setText(Class_Room1);
                                txt_ClassRoom2.setText(Class_Room2);
                                txt_ClassRoom3.setText(Class_Room3);
                                txt_Days1.setText(course_Day1);
                                txt_Days2.setText(course_Day2);
                                txt_Days3.setText(course_Day3);
                                txt_Times1.setText(course_Time1);
                                txt_Times2.setText(course_Time2);
                                txt_Times3.setText(course_Time3);
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

    public void onClickBack(){
        Intent intent = new Intent(StudentScheduleActivity.this, StudentWelcomeActivity.class);
        startActivity(intent);
    }
}