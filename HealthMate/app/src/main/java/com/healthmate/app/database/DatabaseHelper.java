package com.healthmate.app.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.healthmate.app.R;
import com.healthmate.app.model.Exercise;
import com.healthmate.app.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for database operations with food and exercise data
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String TAG = "DatabaseHelper";
    
    // Database name and version
    private static final String DATABASE_NAME = "healthmate.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table names
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_EXERCISE = "exercise";
    
    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    
    // Food table columns
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_PROTEINS = "proteins";
    private static final String COLUMN_CARBS = "carbs";
    private static final String COLUMN_FATS = "fats";
    private static final String COLUMN_FIBER = "fiber";
    private static final String COLUMN_BASE_AMOUNT = "base_amount";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_CUISINE = "cuisine";
    
    // Exercise table columns
    private static final String COLUMN_BODY_PART = "body_part";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_INSTRUCTIONS = "instructions";
    private static final String COLUMN_DURATION = "duration_minutes";
    private static final String COLUMN_CALORIES_BURNED = "calories_burned";
    private static final String COLUMN_DIFFICULTY = "difficulty_level";
    
    // Create table statements
    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_CALORIES + " REAL,"
            + COLUMN_PROTEINS + " REAL,"
            + COLUMN_CARBS + " REAL,"
            + COLUMN_FATS + " REAL,"
            + COLUMN_FIBER + " REAL,"
            + COLUMN_BASE_AMOUNT + " REAL,"
            + COLUMN_UNIT + " TEXT,"
            + COLUMN_CATEGORY + " TEXT,"
            + COLUMN_CUISINE + " TEXT"
            + ")";
    
    private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE " + TABLE_EXERCISE + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_BODY_PART + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_INSTRUCTIONS + " TEXT,"
            + COLUMN_DURATION + " INTEGER,"
            + COLUMN_CALORIES_BURNED + " INTEGER,"
            + COLUMN_DIFFICULTY + " TEXT"
            + ")";
    
    private Context context;
    
    /**
     * Constructor for DatabaseHelper
     * @param context Application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_EXERCISE);
        
        // Load data from JSON files
        loadFoodData(db);
        loadExerciseData(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }
    
    /**
     * Load food data from JSON file into the database
     * @param db SQLiteDatabase instance
     */
    private void loadFoodData(SQLiteDatabase db) {
        try {
            // Read JSON file and parse data
            String jsonString = readFromResource(R.raw.food_database);
            JSONArray foodArray = new JSONArray(jsonString);
            
            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject foodObj = foodArray.getJSONObject(i);
                
                // Insert food data into the database
                String insertQuery = "INSERT INTO " + TABLE_FOOD + " VALUES("
                        + "'" + foodObj.getString("id") + "',"
                        + "'" + foodObj.getString("name") + "',"
                        + foodObj.getDouble("calories") + ","
                        + foodObj.getDouble("proteins") + ","
                        + foodObj.getDouble("carbs") + ","
                        + foodObj.getDouble("fats") + ","
                        + foodObj.getDouble("fiber") + ","
                        + foodObj.getDouble("baseAmount") + ","
                        + "'" + foodObj.getString("unit") + "',"
                        + "'" + foodObj.getString("category") + "',"
                        + "'" + foodObj.getString("cuisine") + "'"
                        + ")";
                
                db.execSQL(insertQuery);
            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "Error loading food data: " + e.getMessage());
        }
    }
    
    /**
     * Load exercise data from JSON file into the database
     * @param db SQLiteDatabase instance
     */
    private void loadExerciseData(SQLiteDatabase db) {
        try {
            // Read JSON file and parse data
            String jsonString = readFromResource(R.raw.exercise_database);
            JSONArray exerciseArray = new JSONArray(jsonString);
            
            for (int i = 0; i < exerciseArray.length(); i++) {
                JSONObject exerciseObj = exerciseArray.getJSONObject(i);
                
                // Insert exercise data into the database
                String insertQuery = "INSERT INTO " + TABLE_EXERCISE + " VALUES("
                        + "'" + exerciseObj.getString("id") + "',"
                        + "'" + exerciseObj.getString("name") + "',"
                        + "'" + exerciseObj.getString("bodyPart") + "',"
                        + "'" + exerciseObj.getString("description") + "',"
                        + "'" + exerciseObj.getString("instructions") + "',"
                        + exerciseObj.getInt("durationInMinutes") + ","
                        + exerciseObj.getInt("caloriesBurned") + ","
                        + "'" + exerciseObj.getString("difficultyLevel") + "'"
                        + ")";
                
                db.execSQL(insertQuery);
            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "Error loading exercise data: " + e.getMessage());
        }
    }
    
    /**
     * Read content from a raw resource file
     * @param resourceId Resource ID
     * @return File content as a String
     * @throws IOException If file can't be read
     */
    private String readFromResource(int resourceId) throws IOException {
        StringBuilder content = new StringBuilder();
        Resources resources = context.getResources();
        
        try (InputStream inputStream = resources.openRawResource(resourceId);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        }
        
        return content.toString();
    }
    
    /**
     * Get a food item by name
     * @param foodName Name of the food to find
     * @return Food object if found, null otherwise
     */
    public Food getFoodByName(String foodName) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Query for food with exact name match or partial match
        String query = "SELECT * FROM " + TABLE_FOOD + 
                " WHERE " + COLUMN_NAME + " LIKE ? LIMIT 1";
        
        Cursor cursor = db.rawQuery(query, new String[]{"%" + foodName + "%"});
        
        Food food = null;
        
        if (cursor.moveToFirst()) {
            food = new Food(
                    cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_CALORIES)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_PROTEINS)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_CARBS)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_FATS)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_FIBER)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_BASE_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CUISINE))
            );
        }
        
        cursor.close();
        
        return food;
    }
    
    /**
     * Get exercises by body part
     * @param bodyPart Target body part
     * @return List of Exercise objects
     */
    public List<Exercise> getExercisesByBodyPart(String bodyPart) {
        List<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Query for exercises for the given body part or "Full Body"
        String query = "SELECT * FROM " + TABLE_EXERCISE + 
                " WHERE " + COLUMN_BODY_PART + " = ? OR " + 
                COLUMN_BODY_PART + " = 'Full Body'";
        
        Cursor cursor = db.rawQuery(query, new String[]{bodyPart});
        
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(
                        cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BODY_PART)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTIONS)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES_BURNED)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY))
                );
                
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        
        return exerciseList;
    }
}
