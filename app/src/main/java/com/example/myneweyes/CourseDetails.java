package com.example.myneweyes;

import java.util.List;

public class CourseDetails {

    private String courseName, courseRoom, courseTime;
    private List<String> courseDays;

    public CourseDetails() {}

    public CourseDetails(String courseName, String courseRoom, String courseTime, List<String> courseDays){
        this.courseName = courseName;
        this.courseRoom = courseRoom;
        this.courseTime = courseTime;
        this.courseDays = courseDays;
    }

    /**
     * Getters And Setters
     */


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public List<String> getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

}
