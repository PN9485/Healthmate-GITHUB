package com.healthmate.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.healthmate.app.model.Exercise;
import com.healthmate.app.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database helper for the application
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    
    // Database information
    private static final String DATABASE_NAME = "healthmate.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table names
    private static final String TABLE_EXERCISES = "exercises";
    
    // Common column names
    private static final String KEY_ID = "id";
    
    // Exercises table column names
    private static final String KEY_NAME = "name";
    private static final String KEY_BODY_PART = "body_part";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_INSTRUCTIONS = "instructions";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_DIFFICULTY = "difficulty";
    
    // Table creation statements
    private static final String CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISES + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_BODY_PART + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_INSTRUCTIONS + " TEXT,"
            + KEY_DURATION + " INTEGER,"
            + KEY_CALORIES + " INTEGER,"
            + KEY_DIFFICULTY + " TEXT"
            + ")";
    
    /**
     * Constructor
     * @param context Application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create required tables
        db.execSQL(CREATE_TABLE_EXERCISES);
        
        // Add default exercises
        addDefaultExercises(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        
        // Create tables again
        onCreate(db);
    }
    
    /**
     * Add default exercises to the database
     * @param db Database instance
     */
    private void addDefaultExercises(SQLiteDatabase db) {
        // Add default exercises for Arms
        insertExercise(db, new Exercise(
                "arm-1",
                "Push-Ups",
                Constants.BODY_PART_ARMS,
                "Classic bodyweight exercise for upper body strength",
                "1. Start in a plank position with hands shoulder-width apart\n2. Lower your body until your chest nearly touches the floor\n3. Push yourself back up\n4. Repeat",
                15,
                150,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        insertExercise(db, new Exercise(
                "arm-2",
                "Bicep Curls",
                Constants.BODY_PART_ARMS,
                "Isolation exercise targeting the biceps",
                "1. Stand with feet shoulder-width apart holding dumbbells\n2. Keeping elbows close to your body, curl the weights up\n3. Lower back down with control\n4. Repeat",
                10,
                120,
                Constants.DIFFICULTY_EASY
        ));
        
        // Add default exercises for Chest
        insertExercise(db, new Exercise(
                "chest-1",
                "Chest Press",
                Constants.BODY_PART_CHEST,
                "Compound exercise for chest development",
                "1. Lie on a bench with feet on the floor\n2. Hold dumbbells at chest level\n3. Press weights up until arms are extended\n4. Lower weights back to chest\n5. Repeat",
                12,
                180,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        insertExercise(db, new Exercise(
                "chest-2",
                "Chest Fly",
                Constants.BODY_PART_CHEST,
                "Isolation exercise for chest muscles",
                "1. Lie on a bench holding dumbbells above your chest\n2. Lower arms out to sides in an arc\n3. Return to starting position\n4. Repeat",
                12,
                150,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        // Add default exercises for Back
        insertExercise(db, new Exercise(
                "back-1",
                "Bent Over Row",
                Constants.BODY_PART_BACK,
                "Compound exercise for back strength",
                "1. Stand with feet shoulder-width apart\n2. Bend at the waist keeping back straight\n3. Pull weights up to your ribs\n4. Lower weights with control\n5. Repeat",
                12,
                200,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        insertExercise(db, new Exercise(
                "back-2",
                "Pull-Ups",
                Constants.BODY_PART_BACK,
                "Advanced bodyweight exercise for upper back",
                "1. Grip a pull-up bar with palms facing away\n2. Pull yourself up until chin is over the bar\n3. Lower with control\n4. Repeat",
                8,
                180,
                Constants.DIFFICULTY_HARD
        ));
        
        // Add default exercises for Abs
        insertExercise(db, new Exercise(
                "abs-1",
                "Crunches",
                Constants.BODY_PART_ABS,
                "Basic exercise for abdominal muscles",
                "1. Lie on your back with knees bent\n2. Place hands behind head\n3. Lift shoulders off the ground using abs\n4. Lower with control\n5. Repeat",
                20,
                120,
                Constants.DIFFICULTY_EASY
        ));
        
        insertExercise(db, new Exercise(
                "abs-2",
                "Plank",
                Constants.BODY_PART_ABS,
                "Isometric exercise for core stability",
                "1. Start in a push-up position with arms straight\n2. Lower onto your forearms\n3. Keep body in a straight line\n4. Hold position",
                60, // seconds
                150,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        // Add default exercises for Legs
        insertExercise(db, new Exercise(
                "legs-1",
                "Squats",
                Constants.BODY_PART_LEGS,
                "Compound exercise for leg strength",
                "1. Stand with feet shoulder-width apart\n2. Lower your body as if sitting in a chair\n3. Keep back straight and knees over toes\n4. Return to standing\n5. Repeat",
                15,
                220,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        insertExercise(db, new Exercise(
                "legs-2",
                "Lunges",
                Constants.BODY_PART_LEGS,
                "Unilateral exercise for legs and balance",
                "1. Stand with feet together\n2. Step forward with one leg\n3. Lower until both knees are at 90 degrees\n4. Push back to starting position\n5. Alternate legs\n6. Repeat",
                12,
                200,
                Constants.DIFFICULTY_MEDIUM
        ));
        
        // Add default exercises for Full Body
        insertExercise(db, new Exercise(
                "full-1",
                "Burpees",
                Constants.BODY_PART_FULL_BODY,
                "High-intensity full body exercise",
                "1. Start standing\n2. Drop to a squat position\n3. Kick feet back to a plank\n4. Perform a push-up\n5. Return to squat position\n6. Jump up\n7. Repeat",
                10,
                250,
                Constants.DIFFICULTY_HARD
        ));
        
        insertExercise(db, new Exercise(
                "full-2",
                "Mountain Climbers",
                Constants.BODY_PART_FULL_BODY,
                "Dynamic full body exercise",
                "1. Start in plank position\n2. Bring one knee toward chest\n3. Switch legs in a running motion\n4. Maintain plank position\n5. Repeat quickly",
                30,
                200,
                Constants.DIFFICULTY_MEDIUM
        ));
    }
    
    /**
     * Insert a single exercise into the database
     * @param db Database instance
     * @param exercise Exercise to insert
     */
    private void insertExercise(SQLiteDatabase db, Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, exercise.getId());
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_BODY_PART, exercise.getBodyPart());
        values.put(KEY_DESCRIPTION, exercise.getDescription());
        values.put(KEY_INSTRUCTIONS, exercise.getInstructions());
        values.put(KEY_DURATION, exercise.getDurationInMinutes());
        values.put(KEY_CALORIES, exercise.getCaloriesBurned());
        values.put(KEY_DIFFICULTY, exercise.getDifficultyLevel());
        
        db.insert(TABLE_EXERCISES, null, values);
    }
    
    /**
     * Get all exercises from the database
     * @return List of exercises
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                exercise.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                exercise.setBodyPart(cursor.getString(cursor.getColumnIndex(KEY_BODY_PART)));
                exercise.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                exercise.setInstructions(cursor.getString(cursor.getColumnIndex(KEY_INSTRUCTIONS)));
                exercise.setDurationInMinutes(cursor.getInt(cursor.getColumnIndex(KEY_DURATION)));
                exercise.setCaloriesBurned(cursor.getInt(cursor.getColumnIndex(KEY_CALORIES)));
                exercise.setDifficultyLevel(cursor.getString(cursor.getColumnIndex(KEY_DIFFICULTY)));
                
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return exercises;
    }
    
    /**
     * Get exercises by body part
     * @param bodyPart Body part to filter by
     * @return List of exercises targeting the specified body part
     */
    public List<Exercise> getExercisesByBodyPart(String bodyPart) {
        List<Exercise> exercises = new ArrayList<>();
        
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES +
                " WHERE " + KEY_BODY_PART + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{bodyPart});
        
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                exercise.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                exercise.setBodyPart(cursor.getString(cursor.getColumnIndex(KEY_BODY_PART)));
                exercise.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                exercise.setInstructions(cursor.getString(cursor.getColumnIndex(KEY_INSTRUCTIONS)));
                exercise.setDurationInMinutes(cursor.getInt(cursor.getColumnIndex(KEY_DURATION)));
                exercise.setCaloriesBurned(cursor.getInt(cursor.getColumnIndex(KEY_CALORIES)));
                exercise.setDifficultyLevel(cursor.getString(cursor.getColumnIndex(KEY_DIFFICULTY)));
                
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return exercises;
    }
    
    /**
     * Add a new exercise to the database or update if it already exists
     * @param exercise Exercise to add
     * @return ID of the inserted/updated row
     */
    public long addOrUpdateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_BODY_PART, exercise.getBodyPart());
        values.put(KEY_DESCRIPTION, exercise.getDescription());
        values.put(KEY_INSTRUCTIONS, exercise.getInstructions());
        values.put(KEY_DURATION, exercise.getDurationInMinutes());
        values.put(KEY_CALORIES, exercise.getCaloriesBurned());
        values.put(KEY_DIFFICULTY, exercise.getDifficultyLevel());
        
        // Check if exercise already exists
        Cursor cursor = db.query(TABLE_EXERCISES, new String[]{KEY_ID},
                KEY_ID + " = ?", new String[]{exercise.getId()},
                null, null, null, null);
        
        long id;
        
        if (cursor != null && cursor.moveToFirst()) {
            // Update existing exercise
            id = db.update(TABLE_EXERCISES, values, KEY_ID + " = ?",
                    new String[]{exercise.getId()});
            cursor.close();
        } else {
            // Insert new exercise
            values.put(KEY_ID, exercise.getId());
            id = db.insert(TABLE_EXERCISES, null, values);
        }
        
        return id;
    }
}