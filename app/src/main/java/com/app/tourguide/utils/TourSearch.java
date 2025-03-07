package com.app.tourguide.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.tourguide.db.DatabaseHelper;
import com.app.tourguide.enums.Category5Duration;
import com.app.tourguide.model.Tour;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourSearch {
    private DatabaseHelper dbHelper;

    // Constructor to initialize DatabaseHelper
    public TourSearch(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }


    public List<String> searchMatchingCountries(String category1type, String category2theme,
                                                String category3region, String category4season,
                                                String category5duration) {
        List<String> matchingCountries = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Construct base query
        String query = "SELECT DISTINCT " + DatabaseHelper.COLUMN_COUNTRY + " FROM " + DatabaseHelper.TABLE_TOURS + " WHERE 1=1";
        List<String> selectionArgs = new ArrayList<>();

        if (category1type != null) {
            query += " AND " + DatabaseHelper.COLUMN_CATEGORY + " LIKE ?";
            selectionArgs.add("%\"" + category1type + "\"%");
        }

        if (category2theme != null) {
            query += " AND " + DatabaseHelper.COLUMN_THEME + " LIKE ?";
            selectionArgs.add("%\"" + category2theme + "\"%");
        }

        if (category3region != null) {
            query += " AND " + DatabaseHelper.COLUMN_REGION + " = ?";
            selectionArgs.add(category3region);
        }

        if (category4season != null) {
            query += " AND " + DatabaseHelper.COLUMN_SEASON + " = ?";
            selectionArgs.add(category4season);
        }

        if (category5duration != null) {
            String durationValue = getDurationString(category5duration);
            query += " AND " + DatabaseHelper.COLUMN_DURATION + " = ?";
            selectionArgs.add(durationValue);
        }

        query += " LIMIT 3";

        // Execute query
        Cursor cursor = db.rawQuery(query, selectionArgs.toArray(new String[0]));

        if (cursor.getCount() == 0) {
            Log.w("ABCD Database Query", "No matching countries found! 🚨");
        } else {
            Log.d("ABCD Database Query", "Total matching countries: " + cursor.getCount());
        }

        // Fetch results
        if (cursor.moveToFirst()) {
            do {
                String country = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNTRY));
                matchingCountries.add(country);
                Log.d("ABCD Database Query", "Matching Country: " + country);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matchingCountries;
    }

    public List<Tour> searchTours(String category1type, String category2theme,
                                  String category3region, String category4season,
                                  String category5duration, String country) {
        List<Tour> filteredTours = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT * FROM " + DatabaseHelper.TABLE_TOURS + " WHERE 1=1";
        List<String> selectionArgs = new ArrayList<>();

        Log.d("Database Query", "Initial Query: " + query);

        if (category1type != null) {
            query += " AND " + DatabaseHelper.COLUMN_CATEGORY + " LIKE ?";
            selectionArgs.add("%" + category1type + "%");
        }
        if (category2theme != null) {
            query += " AND " + DatabaseHelper.COLUMN_THEME + " LIKE ?";
            selectionArgs.add("%" + category2theme + "%");
        }
        if (category3region != null) {
            query += " AND " + DatabaseHelper.COLUMN_REGION + " = ?";
            selectionArgs.add(category3region);
        }
        if (category4season != null) {
            query += " AND " + DatabaseHelper.COLUMN_SEASON + " = ?";
            selectionArgs.add(category4season);
        }
        if (category5duration != null) {
            String durationValue = getDurationString(category5duration);
            query += " AND " + DatabaseHelper.COLUMN_DURATION + " = ?";
            selectionArgs.add(durationValue);
        }
        if (country != null) {
            query += " AND " + DatabaseHelper.COLUMN_COUNTRY + " = ?";
            selectionArgs.add(country);
        }

        query += " LIMIT 3"; // Limit to 3 results

        Log.d("ABCD Database Query", "Final Query: " + query);
        Log.d("ABCD Database Query", "Selection Args: " + selectionArgs.toString());

        Cursor cursor = db.rawQuery(query, selectionArgs.toArray(new String[0]));

        Log.d("ABCD Database Query", "Cursor count: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));
                String region = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REGION));
                String theme = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THEME));
                String season = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEASON));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURATION));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));
                String countryName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNTRY));
                String flag = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FLAG));

                // Convert schedule JSON string to List<String>
                String scheduleJson = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCHEDULE));
                Log.d("ABCD Database Query", "Raw schedule JSON from DB: " + scheduleJson);
                List<String> schedule = parseJsonArray(scheduleJson);
                Log.d("ABCD Database Query", "Parsed schedule list: " + schedule);

                // Convert cities JSON string to List<String>
                String citiesJson = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CITIES));
                List<String> cities = parseJsonArray(citiesJson);

                // Get famous locations as JSON string
                String famousLocations = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FAMOUS_LOCATIONS));

                // Create Tour object with new columns
                Tour tour = new Tour(id, title, category, region, theme, season, duration, description, countryName, flag, schedule, cities, famousLocations);
                filteredTours.add(tour);

                Log.d("ABCD Database Query", "Tour added: " + tour.toString());
            } while (cursor.moveToNext());
        } else {
            Log.d("ABCD Database Query", "No results found for the query.");
        }

        cursor.close();
        db.close();
        return filteredTours;
    }

    /**
     * Helper method to parse a JSON string into a List<String>
     */
    private List<String> parseJsonArray(String json) {
        List<String> list = new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return list; // Return empty list if json is null or empty
        }

        try {
            // Check if the string is a valid JSON array
            if (json.startsWith("[") && json.endsWith("]")) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getString(i));
                }
            } else {
                // If not a JSON array, assume it's a comma-separated string
                list = Arrays.asList(json.split(", "));
            }
        } catch (JSONException e) {
            Log.e("Database Query", "Error parsing schedule JSON: " + json, e);
        }

        return list;
    }



    // Helper method to convert Category5Duration to database format
    private String getDurationString(String duration) {
        switch (duration) {
            case "THREEDAYS":
                return "3 days";
            case "FIVEDAYS":
                return "5 days";
            case "SEVENDAYS":
                return "7 days";
            default:
                return "";
        }
    }
}

