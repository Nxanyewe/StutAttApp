package com.example.student_attandance;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Attendance.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_COURSE_ID = "course_id";
    public static final String COLUMN_COURSE_NAME = "course_name";

    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String COLUMN_ATTENDANCE_ID = "attendance_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_DATE = "date";

    private static final String COLUMN_INSTRUCTOR_NAME = "instructor";
    private static final String COLUMN_INSTRUCTOR ="instructor";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " TEXT)";

        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COURSE_NAME + " TEXT,"
                + COLUMN_INSTRUCTOR_NAME + " TEXT" + ")";
        db.execSQL(CREATE_COURSES_TABLE);

        String createTable = "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                COLUMN_DATE + " TEXT PRIMARY KEY)";
        db.execSQL(createTable);
        db.execSQL(createUsersTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    public void markAttendance(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        db.insertWithOnConflict(TABLE_ATTENDANCE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public List<String> getAllAttendanceDates() {
        List<String> attendanceDates = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ATTENDANCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                attendanceDates.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attendanceDates;
    }


    // Adding new course
    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, course.getCourseName());
        values.put(COLUMN_INSTRUCTOR, course.getInstructor());

        // Inserting Row
        db.insert(TABLE_COURSES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single course by ID
    public Course getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSES, new String[]{COLUMN_ID,
                        COLUMN_COURSE_NAME, COLUMN_INSTRUCTOR_NAME}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        @SuppressLint("Range") Course course = new Course(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTOR_NAME)));
        cursor.close();
        return course;
    }

    // Getting all courses

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Course course = new Course(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTOR_NAME))
                );
                courses.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courses;
    }


    // Updating single course
    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, course.getCourseName());
        values.put(COLUMN_INSTRUCTOR_NAME, course.getInstructorName());
        return db.update(TABLE_COURSES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(course.getId())});
    }

    // Deleting single course
    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(course.getId())});
        db.close();
    }

    // Getting courses count
    public int getCoursesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COURSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}