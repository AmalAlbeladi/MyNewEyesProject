package com.example.myneweyes;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button signupButton;
    private Button loginButton;
    private DatabaseReference mDatabase;
    private String readRFID;
    private String readUltrasonic;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        // Initialize buttons
        signupButton = findViewById(R.id.mainSignUp_button);
        loginButton = findViewById(R.id.mainSignIn_button);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set up signupButton onClickListener
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        scheduleAlarm();
        // Set up loginButton onClickListener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });
        readSensor();



    }

    public void readSensor(){

        // Set up Firebase database reference
       mDatabase = FirebaseDatabase.getInstance().getReference();
        // Set up RFID sensor listener
        mDatabase.child("RFIDsensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readRFID = dataSnapshot.child("rfid").getValue(String.class);

                if (readRFID.equals("true")) {
                 sendLocalNotification("Your class!", "You have arrived to your class!");
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
                readUltrasonic = dataSnapshot.child("ultrasonic").getValue(String.class);
                if (readUltrasonic.equals("true")) {
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

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), MainActivity.class);
        final int flag =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, flag);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        bigText.setBigContentTitle(notificationTitle);
        bigText.setSummaryText(notificationBody);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_baseline_circle_notifications_24);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationBody);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }






    public void scheduleAlarm()
    {

        final int flag =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;
        Intent intent = new Intent(this, AlarmScheduleService.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 2, intent, flag);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, 5000, 60000, sender); // 86400000
    }





    

}






