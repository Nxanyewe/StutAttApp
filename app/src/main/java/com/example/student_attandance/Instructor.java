package com.example.student_attandance;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends AppCompatActivity {

    private ListView lvCourses;
    private List<Course> courseList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        lvCourses = findViewById(R.id.lvCourses);
        databaseHelper = new DatabaseHelper(this);

        // Load courses from the database
        loadCourses();

        findViewById(R.id.btnManageCourse).setOnClickListener(v -> {
            // Code to manage course goes here
            Intent intent = new Intent(Instructor.this, Manage_Course.class);
            startActivity(intent);
        });

        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = courseList.get(position);
                Toast.makeText(Instructor.this, "Selected: " + selectedCourse.getCourseName(), Toast.LENGTH_SHORT).show();
                // Handle course item click (e.g., navigate to course details or manage course)
                Intent intent = new Intent(Instructor.this, Manage_Course.class);
                intent.putExtra("courseId", selectedCourse.getId());
                startActivity(intent);
            }
        });
    }

    private void loadCourses() {
        courseList = databaseHelper.getAllCourses();
        List<String> courseNames = new ArrayList<>();
        for (Course course : courseList) {
            courseNames.add(course.getCourseName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        lvCourses.setAdapter(adapter);
    }
}

