package com.example.student_attandance;
public class Course {
    private int id;
    private String courseName;
    private String instructorName;

    public Course(int id, String courseName, String instructorName) {
        this.id = id;
        this.courseName = courseName;
        this.instructorName = instructorName;
    }

    public Course(String courseName, String instructorName) {
        this.courseName = courseName;
        this.instructorName = instructorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public byte[] getInstructor() {
        if (instructorName != null) {
            return instructorName.getBytes();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
}
