package com.example.student_attandance;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Manage_Course extends AppCompatActivity {

    private EditText editCourseName, editInstructorName;
    private DatabaseHelper databaseHelper;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);

        editCourseName = findViewById(R.id.edit_course_name);
        editInstructorName = findViewById(R.id.edit_instructor_name);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnDelete = findViewById(R.id.btn_delete);

        databaseHelper = new DatabaseHelper(this);
        courseId = getIntent().getIntExtra("courseId", -1);

        if (courseId != -1) {
            loadCourseDetails(courseId);
        }

        btnSave.setOnClickListener(v -> saveCourse());
        btnDelete.setOnClickListener(v -> deleteCourse());
    }

    private void loadCourseDetails(int courseId) {
        Course course = databaseHelper.getCourse(courseId);
        if (course != null) {
            editCourseName.setText(course.getCourseName());
            editInstructorName.setText(course.getInstructorName());
        }
    }

    private void saveCourse() {
        String courseName = editCourseName.getText().toString().trim();
        String instructorName = editInstructorName.getText().toString().trim();

        if (!courseName.isEmpty() && !instructorName.isEmpty()) {
            Course course = new Course(courseId, courseName, instructorName);
            if (courseId == -1) {
                databaseHelper.addCourse(course);
                Toast.makeText(this, "Course added", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.updateCourse(course);
                Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            Toast.makeText(this, "Please enter course details", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCourse() {
        if (courseId != -1) {
            Course course = new Course(courseId, editCourseName.getText().toString().trim(), editInstructorName.getText().toString().trim());
            databaseHelper.deleteCourse(course);
            Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
