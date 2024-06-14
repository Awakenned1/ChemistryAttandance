package com.example.chemistryattandance;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ChemDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the ChemDatabase
        dbHelper = new ChemDatabase(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button to guide user to role selection page
        findViewById(R.id.roleSelectionButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RoleSelectionActivity.class));
        });

        // Example of database operation: Adding a user
        addUser();
        // Example of database operation: Adding a lecturer
        addLecturer();
    }

    private void addUser() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put("displayName", "John Doe");
        values.put("email", "22287234@dutlife.ac.za");
        values.put("password", "password123");
        values.put("role", "student");

        long newRowId = db.insert(ChemDatabase.TABLE_USERS, null, values);

        if (newRowId != -1) {
            // Log or display a message indicating success
            System.out.println("User added with row id: " + newRowId);
        } else {
            // Log or display a message indicating failure
            System.out.println("Error adding user");
        }

        db.close();
    }

    private void addLecturer() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // First, insert the lecturer as a user
        ContentValues userValues = new ContentValues();
        userValues.put("id", "2");
        userValues.put("displayName", "Jane Smith");
        userValues.put("email", "jane@dut4life.ac.za");
        userValues.put("password", "password456");
        userValues.put("role", "lecturer");

        long newUserRowId = db.insert(ChemDatabase.TABLE_USERS, null, userValues);

        if (newUserRowId != -1) {
            // Log or display a message indicating success
            System.out.println("Lecturer user added with row id: " + newUserRowId);

            // Then, insert the lecturer into the lecturers table
            ContentValues lecturerValues = new ContentValues();
            lecturerValues.put("lecturerId", "2");

            long newLecturerRowId = db.insert(ChemDatabase.TABLE_LECTURERS, null, lecturerValues);

            if (newLecturerRowId != -1) {
                // Log or display a message indicating success
                System.out.println("Lecturer added with row id: " + newLecturerRowId);
            } else {
                // Log or display a message indicating failure
                System.out.println("Error adding lecturer");
            }
        } else {
            // Log or display a message indicating failure
            System.out.println("Error adding lecturer user");
        }

        db.close();
    }
}
