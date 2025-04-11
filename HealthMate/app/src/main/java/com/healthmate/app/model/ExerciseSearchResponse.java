package com.healthmate.app.model;

import com.healthmate.app.model.api.ApiExercise;
import com.healthmate.app.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Response from the Wger exercise API
 */
public class ExerciseSearchResponse {
    private int count;
    private String next;
    private String previous;
    private List<ApiExercise> results;
    
    /**
     * Default constructor
     */
    public ExerciseSearchResponse() {
        results = new ArrayList<>();
    }
    
    /**
     * Get count of total results
     * @return Count as an integer
     */
    public int getCount() {
        return count;
    }
    
    /**
     * Set count of total results
     * @param count Count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * Get URL for next page of results
     * @return URL as a String
     */
    public String getNext() {
        return next;
    }
    
    /**
     * Set URL for next page of results
     * @param next URL to set
     */
    public void setNext(String next) {
        this.next = next;
    }
    
    /**
     * Get URL for previous page of results
     * @return URL as a String
     */
    public String getPrevious() {
        return previous;
    }
    
    /**
     * Set URL for previous page of results
     * @param previous URL to set
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    
    /**
     * Get list of exercise results
     * @return List of ApiExercise objects
     */
    public List<ApiExercise> getResults() {
        return results;
    }
    
    /**
     * Set list of exercise results
     * @param results List of ApiExercise objects to set
     */
    public void setResults(List<ApiExercise> results) {
        this.results = results;
    }
    
    /**
     * Convert API response to a list of Exercise objects
     * @return List of Exercise objects
     */
    public List<Exercise> toExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();
        
        if (results != null) {
            for (ApiExercise apiExercise : results) {
                Exercise exercise = new Exercise();
                exercise.setId(String.valueOf(apiExercise.getId()));
                exercise.setName(apiExercise.getName());
                
                // Determine body part from categories
                String bodyPart = determineBodyPart(apiExercise.getCategory());
                exercise.setBodyPart(bodyPart);
                
                exercise.setDescription(apiExercise.getDescription());
                exercise.setInstructions(apiExercise.getDescription()); // Using description as instructions
                
                // Set default values for fields not provided by API
                exercise.setDurationInMinutes(15); // Default duration
                exercise.setCaloriesBurned(calculateCalories(bodyPart)); // Estimate calories based on body part
                exercise.setDifficultyLevel(determineDifficulty(apiExercise)); // Estimate difficulty
                
                exerciseList.add(exercise);
            }
        }
        
        return exerciseList;
    }
    
    /**
     * Determine body part from exercise category
     * @param category Category from API
     * @return Body part as a String
     */
    private String determineBodyPart(int category) {
        // Map wger API categories to our body parts
        switch (category) {
            case 8:
            case 9:
            case 10:
                return Constants.BODY_PART_ARMS;
            case 11:
            case 12:
                return Constants.BODY_PART_LEGS;
            case 13:
                return Constants.BODY_PART_CHEST;
            case 14:
                return Constants.BODY_PART_BACK;
            case 10:
                return Constants.BODY_PART_ABS;
            default:
                return Constants.BODY_PART_FULL_BODY;
        }
    }
    
    /**
     * Calculate estimated calories burned based on body part
     * @param bodyPart Body part being exercised
     * @return Estimated calories burned in 30 minutes
     */
    private int calculateCalories(String bodyPart) {
        // Estimated calories burned in 30 minutes based on body part
        if (bodyPart.equals(Constants.BODY_PART_FULL_BODY)) {
            return 300; // Full body workouts burn more calories
        } else if (bodyPart.equals(Constants.BODY_PART_LEGS)) {
            return 250; // Legs are large muscle groups
        } else if (bodyPart.equals(Constants.BODY_PART_BACK)) {
            return 220;
        } else if (bodyPart.equals(Constants.BODY_PART_CHEST)) {
            return 200;
        } else if (bodyPart.equals(Constants.BODY_PART_ARMS)) {
            return 150; // Arms are smaller muscle groups
        } else {
            return 180; // Default
        }
    }
    
    /**
     * Determine difficulty level based on exercise data
     * @param apiExercise Exercise data from API
     * @return Difficulty level as a String
     */
    private String determineDifficulty(ApiExercise apiExercise) {
        // Simple heuristic: look for keywords in the description
        String description = apiExercise.getDescription().toLowerCase();
        
        if (description.contains("advanced") || description.contains("difficult") ||
                description.contains("challenging")) {
            return Constants.DIFFICULTY_HARD;
        } else if (description.contains("beginner") || description.contains("easy") ||
                description.contains("simple")) {
            return Constants.DIFFICULTY_EASY;
        } else {
            return Constants.DIFFICULTY_MEDIUM; // Default to medium difficulty
        }
    }
}