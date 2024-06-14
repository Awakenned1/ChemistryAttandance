package com.example.chemistryattandance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChemDatabase extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "school.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_LECTURERS = "lecturers";
    public static final String TABLE_LECTURES = "lectures";
    public static final String TABLE_QRCODES = "qrcodes";
    public static final String TABLE_ATTENDANCES = "attendances";
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String TABLE_REPORTS = "reports";

    // Create Table Statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            "id TEXT PRIMARY KEY, " +
            "displayName TEXT, " +
            "email TEXT, " +
            "password TEXT, " +
            "role TEXT);";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " (" +
            "studentId TEXT PRIMARY KEY, " +
            "FOREIGN KEY(studentId) REFERENCES " + TABLE_USERS + "(id));";

    private static final String CREATE_TABLE_LECTURERS = "CREATE TABLE " + TABLE_LECTURERS + " (" +
            "lecturerId TEXT PRIMARY KEY, " +
            "FOREIGN KEY(lecturerId) REFERENCES " + TABLE_USERS + "(id));";

    private static final String CREATE_TABLE_LECTURES = "CREATE TABLE " + TABLE_LECTURES + " (" +
            "lectureId TEXT PRIMARY KEY, " +
            "lecturerId TEXT, " +
            "date DATE, " +
            "time TIME, " +
            "room TEXT, " +
            "qrCodeData TEXT, " +
            "FOREIGN KEY(lecturerId) REFERENCES " + TABLE_LECTURERS + "(lecturerId));";

    private static final String CREATE_TABLE_QRCODES = "CREATE TABLE " + TABLE_QRCODES + " (" +
            "lectureId TEXT, " +
            "qrCodeId TEXT PRIMARY KEY, " +
            "data TEXT, " +
            "FOREIGN KEY(lectureId) REFERENCES " + TABLE_LECTURES + "(lectureId));";

    private static final String CREATE_TABLE_ATTENDANCES = "CREATE TABLE " + TABLE_ATTENDANCES + " (" +
            "attendanceId TEXT PRIMARY KEY, " +
            "studentId TEXT, " +
            "timestamp DATETIME, " +
            "lectureId TEXT, " +
            "FOREIGN KEY(studentId) REFERENCES " + TABLE_STUDENTS + "(studentId), " +
            "FOREIGN KEY(lectureId) REFERENCES " + TABLE_LECTURES + "(lectureId));";

    private static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
            "notificationId TEXT PRIMARY KEY, " +
            "userId TEXT, " +
            "message TEXT, " +
            "timestamp DATETIME, " +
            "FOREIGN KEY(userId) REFERENCES " + TABLE_USERS + "(id));";

    private static final String CREATE_TABLE_REPORTS = "CREATE TABLE " + TABLE_REPORTS + " (" +
            "reportId TEXT PRIMARY KEY, " +
            "data TEXT, " +
            "lecturerId TEXT, " +
            "FOREIGN KEY(lecturerId) REFERENCES " + TABLE_LECTURERS + "(lecturerId));";

    public ChemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_LECTURERS);
        db.execSQL(CREATE_TABLE_LECTURES);
        db.execSQL(CREATE_TABLE_QRCODES);
        db.execSQL(CREATE_TABLE_ATTENDANCES);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);
        db.execSQL(CREATE_TABLE_REPORTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QRCODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
        onCreate(db);
    }
}
