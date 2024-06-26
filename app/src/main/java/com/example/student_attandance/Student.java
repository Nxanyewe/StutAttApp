package com.example.student_attandance;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Student extends AppCompatActivity {

    private CalendarView calendarView;
    private DatabaseHelper databaseHelper;
    private List<String> markedDates;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        RecyclerView recyclerViewCourses = findViewById(R.id.recycler_view_courses);
        calendarView = findViewById(R.id.calendarView);
        FloatingActionButton fabAttendance = findViewById(R.id.fab_attendance);
        Button btnViewAttendance = findViewById(R.id.btn_view_attendance);

        databaseHelper = new DatabaseHelper(this);
        markedDates = new ArrayList<>();

        // Initialize course list
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Math 101", "Dr. Smith"));
        courseList.add(new Course("History 201", "Prof. Johnson"));
        courseList.add(new Course("Science 301", "Dr. Brown"));

        // Setup RecyclerView
        courseAdapter = new CourseAdapter(courseList);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCourses.setAdapter(courseAdapter);

        // Load previously marked attendance dates
        loadAttendanceDates();

        // Handle FAB click for attendance
        fabAttendance.setOnClickListener(v -> {
            markAttendance();
        });

        // Handle View Attendance button click
        btnViewAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(Student.this, AttendanceActivity.class);
            startActivity(intent);
        });
    }

    private void loadAttendanceDates() {
        markedDates = databaseHelper.getAllAttendanceDates();
        for (String date : markedDates) {
            // Apply some UI changes to mark the dates as attended
            // This part will depend on how you want to represent the marked dates in the UI
            // e.g., changing the background color of the dates on the CalendarView
        }
    }

    private void markAttendance() {
        long selectedDateMillis = calendarView.getDate();
        Date selectedDate = new Date(selectedDateMillis);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate);

        // Mark attendance in database
        databaseHelper.markAttendance(formattedDate);

        // Update UI: Refresh marked dates and update RecyclerView or other UI elements
        markedDates.clear();
        markedDates.addAll(databaseHelper.getAllAttendanceDates());

        // For simplicity, you can log the updated dates
        Log.d("AttendanceDates", "Marked Dates: " + markedDates.toString());
    }
}
