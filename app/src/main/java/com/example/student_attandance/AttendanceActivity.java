package com.example.student_attandance;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAttendance;
    private AttendanceAdapter attendanceAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        recyclerViewAttendance = findViewById(R.id.recycler_view_attendance);
        databaseHelper = new DatabaseHelper(this);

        // Load attendance dates from database
        List<String> attendanceDates = databaseHelper.getAllAttendanceDates();

        // Setup RecyclerView
        attendanceAdapter = new AttendanceAdapter(attendanceDates);
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAttendance.setAdapter(attendanceAdapter);
    }
}
