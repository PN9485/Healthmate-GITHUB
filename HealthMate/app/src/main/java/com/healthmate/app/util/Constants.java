package com.healthmate.app.util;

/**
 * Constants used throughout the application
 */
public class Constants {
    
    // Shared preferences
    public static final String PREFS_NAME = "HealthmatePrefs";
    
    // Shared preferences keys for user data
    public static final String KEY_NAME = "user_name";
    public static final String KEY_AGE = "user_age";
    public static final String KEY_HEIGHT = "user_height";
    public static final String KEY_WEIGHT = "user_weight";
    public static final String KEY_BMI = "user_bmi";
    
    // BMI categories
    public static final double BMI_SEVERELY_UNDERWEIGHT = 16.0;
    public static final double BMI_UNDERWEIGHT = 18.5;
    public static final double BMI_NORMAL = 25.0;
    public static final double BMI_OVERWEIGHT = 30.0;
    public static final double BMI_OBESE_CLASS_1 = 35.0;
    public static final double BMI_OBESE_CLASS_2 = 40.0;
    
    // Food related constants
    public static final String CATEGORY_VEGETARIAN = "Vegetarian";
    public static final String CATEGORY_NON_VEGETARIAN = "Non-Vegetarian";
    public static final String CATEGORY_VEGAN = "Vegan";
    
    // Exercise related constants
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";
    
    // Body parts
    public static final String BODY_PART_ARMS = "Arms";
    public static final String BODY_PART_CHEST = "Chest";
    public static final String BODY_PART_BACK = "Back";
    public static final String BODY_PART_ABS = "Abs";
    public static final String BODY_PART_LEGS = "Legs";
    public static final String BODY_PART_FULL_BODY = "Full Body";
    
    // API related constants
    public static final String OPEN_FOOD_FACTS_BASE_URL = "https://world.openfoodfacts.org/";
    public static final String EXERCISE_DB_BASE_URL = "https://exercisedb.p.rapidapi.com/";
    public static final String EXERCISE_DB_API_KEY_HEADER = "X-RapidAPI-Key";
    public static final String EXERCISE_DB_HOST_HEADER = "X-RapidAPI-Host";
    public static final String EXERCISE_DB_HOST_VALUE = "exercisedb.p.rapidapi.com";
    public static final int DEFAULT_PAGE_SIZE = 20;
}
