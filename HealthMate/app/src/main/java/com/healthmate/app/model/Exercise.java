package com.healthmate.app.model;

/**
 * Class representing an exercise in the application
 */
public class Exercise {
    private String id;
    private String name;
    private String bodyPart;
    private String description;
    private String instructions;
    private int durationInMinutes;
    private int caloriesBurned; // per 30 minutes for a 70kg person
    private String difficultyLevel; // Easy, Medium, Hard
    
    /**
     * Default constructor required for database operations
     */
    public Exercise() {
    }
    
    /**
     * Constructor for Exercise class
     * @param id Unique identifier
     * @param name Exercise name
     * @param bodyPart Target body part
     * @param description Brief description
     * @param instructions Step-by-step instructions
     * @param durationInMinutes Recommended duration
     * @param caloriesBurned Estimated calories burned
     * @param difficultyLevel Difficulty level
     */
    public Exercise(String id, String name, String bodyPart, String description, 
                   String instructions, int durationInMinutes, int caloriesBurned, 
                   String difficultyLevel) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.description = description;
        this.instructions = instructions;
        this.durationInMinutes = durationInMinutes;
        this.caloriesBurned = caloriesBurned;
        this.difficultyLevel = difficultyLevel;
    }
    
    /**
     * Get exercise ID
     * @return ID as a String
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set exercise ID
     * @param id ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get exercise name
     * @return Name as a String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set exercise name
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get target body part
     * @return Body part as a String
     */
    public String getBodyPart() {
        return bodyPart;
    }
    
    /**
     * Set target body part
     * @param bodyPart Body part to set
     */
    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }
    
    /**
     * Get exercise description
     * @return Description as a String
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set exercise description
     * @param description Description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get exercise instructions
     * @return Instructions as a String
     */
    public String getInstructions() {
        return instructions;
    }
    
    /**
     * Set exercise instructions
     * @param instructions Instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    /**
     * Get recommended duration
     * @return Duration in minutes as an integer
     */
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
    
    /**
     * Set recommended duration
     * @param durationInMinutes Duration in minutes to set
     */
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    
    /**
     * Get estimated calories burned
     * @return Calories as an integer
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    
    /**
     * Set estimated calories burned
     * @param caloriesBurned Calories to set
     */
    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
    
    /**
     * Get difficulty level
     * @return Difficulty level as a String
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    
    /**
     * Set difficulty level
     * @param difficultyLevel Difficulty level to set
     */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
