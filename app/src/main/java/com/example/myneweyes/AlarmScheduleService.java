package com.example.myneweyes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlarmScheduleService extends BroadcastReceiver {

    Map<String, Integer> weekDaysMap = new HashMap<>();
    private DatabaseReference fbDataBaseRef;

    @Override
    public void onReceive(Context context, Intent intent) {

        fbDataBaseRef = FirebaseDatabase.getInstance().getReference();
        Query query = fbDataBaseRef.child("Users_Register_Info").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String Email = snapshot.child("related").getValue(String.class);
                    Query query2 = fbDataBaseRef.child("Courses").orderByChild("Assistant").equalTo(Email);
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot record: dataSnapshot.getChildren()) {
                                CourseDetails courseDetails = new CourseDetails();
                                courseDetails.setCourseName(record.child("Course_name1").getValue(String.class));
                                courseDetails.setCourseRoom(record.child("Class_Room1").getValue(String.class));
                                courseDetails.setCourseDays(Arrays.stream(record.child("course_Day1").getValue(String.class).split("\\.")).collect(Collectors.toList()));
                                courseDetails.setCourseTime(record.child("course_Time1").getValue(String.class));
                                if (isCourseToday(courseDetails.getCourseDays()) && isCourseAvailable(courseDetails.getCourseTime())) {
                                    sendLocalNotification(context, "Course Alarm", handleDisplay(courseDetails));
                                }
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
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private boolean isCourseAvailable(String courseTime){
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTimeNow = parseDateTimeFormat(currentTime);
        return checkTimeTypeAvailable(formattedTimeNow, courseTime) && checkHourAvailable(formattedTimeNow, courseTime);

    }

    private boolean checkTimeTypeAvailable(String currTimeType, String courseTime) {
        String currentTimeType = currTimeType.split(" ")[1];
        String courseTimeType = courseTime.split(" ")[1];
        if (currentTimeType.toLowerCase(Locale.ROOT).equals("pm") && courseTimeType.toLowerCase(Locale.ROOT).equals("am")) {
            return false;
        }
        return true;
    }
    private boolean checkHourAvailable(String currTimeType, String courseTime) {
        Integer currentTimeHour = Integer.parseInt(currTimeType.split(":")[0]);
        Integer courseTimeHour = Integer.parseInt(courseTime.split(":")[0]);
        if (currentTimeHour > courseTimeHour) {
            return false;
        }
        return true;
    }

    private String parseDateTimeFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date);
    }


    public void sendLocalNotification(Context context, String notificationTitle, String notificationBody) {

        NotificationManager notificationManager;
        String channelId = UUID.randomUUID().toString();
        final int flag =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flag);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        bigText.setBigContentTitle(notificationTitle);
        bigText.setSummaryText(notificationBody);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.education_cap);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationBody);
        mBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        mBuilder.setStyle(bigText);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Course Alarm Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        notificationManager.notify(new Random().nextInt(9000) + 1000, mBuilder.build());
    }



    private String handleDisplay(CourseDetails courseDetails) {
        return String.format("CourseName: %s - Room: %s - Time: %s", courseDetails.getCourseName(), courseDetails.getCourseRoom(),
                courseDetails.getCourseTime());
    }

    private void mappingWeekDays(){
//      "U","M","T","W","R","U.T.R","M.W";
        weekDaysMap.put("S", 7); // Saturday
        weekDaysMap.put("SU", 1); // Sunday
        weekDaysMap.put("M", 2); // Monday
        weekDaysMap.put("T", 3); // Tuesday
        weekDaysMap.put("W", 4); // Wednesday
        weekDaysMap.put("R", 5); // Thursday
        weekDaysMap.put("F", 6); // Friday
    }

    private boolean isCourseToday(List<String> days){
        mappingWeekDays();
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        return days.stream().anyMatch(s -> weekDaysMap.get(s).equals(currentDay));
    }




}
