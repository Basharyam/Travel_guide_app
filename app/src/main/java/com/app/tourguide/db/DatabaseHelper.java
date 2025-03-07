package com.app.tourguide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tour_guide.db";
    private static final int DATABASE_VERSION = 3; // Updated version

    public static final String TABLE_TOURS = "tours";
    public static final String COLUMN_ID = "_id"; // Primary Key
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_FLAG = "flag"; // URL or resource path
    public static final String COLUMN_SCHEDULE = "schedule"; // Stored as comma-separated string
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_REGION = "region";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CITIES = "cities"; // Stored as comma-separated string
    public static final String COLUMN_FAMOUS_LOCATIONS = "famous_locations"; // Stored as JSON string

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TOURS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COUNTRY + " TEXT, " +
                    COLUMN_FLAG + " TEXT, " +
                    COLUMN_SCHEDULE + " TEXT, " + // Store schedule as a comma-separated string
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_REGION + " TEXT, " +
                    COLUMN_THEME + " TEXT, " +
                    COLUMN_SEASON + " TEXT, " +
                    COLUMN_DURATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_CITIES + " TEXT, " + // Store cities as a comma-separated string
                    COLUMN_FAMOUS_LOCATIONS + " TEXT" + // Store famous locations as JSON
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) { // Upgrade logic for version 3
            db.execSQL("ALTER TABLE " + TABLE_TOURS + " ADD COLUMN " + COLUMN_CITIES + " TEXT;");
            db.execSQL("ALTER TABLE " + TABLE_TOURS + " ADD COLUMN " + COLUMN_FAMOUS_LOCATIONS + " TEXT;");
        }
    }
}
