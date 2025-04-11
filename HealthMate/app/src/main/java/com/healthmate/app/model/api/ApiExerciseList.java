package com.healthmate.app.model.api;

import com.google.gson.annotations.SerializedName;
import com.healthmate.app.model.Exercise;
import com.healthmate.app.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for exercise list response from ExerciseDB API
 */
public class ApiExerciseList extends ArrayList<ApiExerciseList.ApiExerciseItem> {

    /**
     * Convert API response to list of Exercise objects for our app
     * @return List of Exercise objects
     */
    public List<Exercise> toExerciseList() {
        List<Exercise> exercises = new ArrayList<>();
        
        for (ApiExerciseItem item : this) {
            Exercise exercise = new Exercise();
            
            exercise.setId(item.getId());
            exercise.setName(item.getName());
            
            // Map body part to our constants
            String mappedBodyPart = mapBodyPart(item.getBodyPart());
            exercise.setBodyPart(mappedBodyPart);
            
            // Set description and instructions
            StringBuilder description = new StringBuilder();
            description.append("Target: ").append(item.getTarget()).append("\n");
            description.append("Equipment: ").append(item.getEquipment());
            exercise.setDescription(description.toString());
            
            // Set instructions
            exercise.setInstructions(formatInstructions(item.getInstructions()));
            
            // Set calories based on body part
            exercise.setCaloriesBurned(calculateCalories(mappedBodyPart));
            
            // Set duration
            exercise.setDurationInMinutes(15); // Default 15 minutes per exercise
            
            // Set difficulty based on equipment and other factors
            exercise.setDifficultyLevel(calculateDifficulty(item));
            
            exercises.add(exercise);
        }
        
        return exercises;
    }
    
    /**
     * Format instructions as a single string
     * @param instructions List of instruction strings
     * @return Formatted instructions
     */
    private String formatInstructions(List<String> instructions) {
        if (instructions == null || instructions.isEmpty()) {
            return "No instructions available.";
        }
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < instructions.size(); i++) {
            builder.append(i + 1).append(". ").append(instructions.get(i));
            if (i < instructions.size() - 1) {
                builder.append("\n");
            }
        }
        
        return builder.toString();
    }
    
    /**
     * Map API body part names to our constants
     * @param bodyPart Body part name from API
     * @return Mapped body part constant
     */
    private String mapBodyPart(String bodyPart) {
        if (bodyPart == null) return Constants.BODY_PART_FULL_BODY;
        
        // Create mapping
        Map<String, String> bodyPartMap = new HashMap<>();
        bodyPartMap.put("back", Constants.BODY_PART_BACK);
        bodyPartMap.put("cardio", Constants.BODY_PART_FULL_BODY);
        bodyPartMap.put("chest", Constants.BODY_PART_CHEST);
        bodyPartMap.put("lower arms", Constants.BODY_PART_ARMS);
        bodyPartMap.put("lower legs", Constants.BODY_PART_LEGS);
        bodyPartMap.put("neck", Constants.BODY_PART_BACK);
        bodyPartMap.put("shoulders", Constants.BODY_PART_ARMS);
        bodyPartMap.put("upper arms", Constants.BODY_PART_ARMS);
        bodyPartMap.put("upper legs", Constants.BODY_PART_LEGS);
        bodyPartMap.put("waist", Constants.BODY_PART_ABS);
        
        String normalizedBodyPart = bodyPart.toLowerCase();
        return bodyPartMap.containsKey(normalizedBodyPart) 
                ? bodyPartMap.get(normalizedBodyPart) 
                : Constants.BODY_PART_FULL_BODY;
    }
    
    /**
     * Calculate calories burned based on body part
     * @param bodyPart Body part being worked
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
        } else if (bodyPart.equals(Constants.BODY_PART_ABS)) {
            return 180;
        } else {
            return 180; // Default
        }
    }
    
    /**
     * Calculate difficulty based on equipment and other factors
     * @param item Exercise item from API
     * @return Difficulty level
     */
    private String calculateDifficulty(ApiExerciseItem item) {
        // Equipment-based difficulty assessment
        if (item.getEquipment() == null) return Constants.DIFFICULTY_MEDIUM;
        
        String equipment = item.getEquipment().toLowerCase();
        
        if (equipment.contains("assisted") || equipment.equals("body weight") || 
                equipment.equals("band") || equipment.equals("stability ball")) {
            return Constants.DIFFICULTY_EASY;
        } else if (equipment.contains("cable") || equipment.contains("dumbbell") || 
                equipment.equals("kettlebell") || equipment.equals("medicine ball")) {
            return Constants.DIFFICULTY_MEDIUM;
        } else if (equipment.contains("barbell") || equipment.contains("leverage") || 
                equipment.equals("sled machine") || equipment.equals("smith machine")) {
            return Constants.DIFFICULTY_HARD;
        }
        
        return Constants.DIFFICULTY_MEDIUM; // Default to medium difficulty
    }
    
    /**
     * Inner class representing an exercise item from the API
     */
    public static class ApiExerciseItem {
        private String bodyPart;
        private String equipment;
        private String gifUrl;
        private String id;
        private List<String> instructions;
        private String name;
        private String target;
        @SerializedName("secondaryMuscles")
        private List<String> secondaryMuscles;
        
        public String getBodyPart() {
            return bodyPart;
        }
        
        public void setBodyPart(String bodyPart) {
            this.bodyPart = bodyPart;
        }
        
        public String getEquipment() {
            return equipment;
        }
        
        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }
        
        public String getGifUrl() {
            return gifUrl;
        }
        
        public void setGifUrl(String gifUrl) {
            this.gifUrl = gifUrl;
        }
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public List<String> getInstructions() {
            return instructions;
        }
        
        public void setInstructions(List<String> instructions) {
            this.instructions = instructions;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getTarget() {
            return target;
        }
        
        public void setTarget(String target) {
            this.target = target;
        }
        
        public List<String> getSecondaryMuscles() {
            return secondaryMuscles;
        }
        
        public void setSecondaryMuscles(List<String> secondaryMuscles) {
            this.secondaryMuscles = secondaryMuscles;
        }
    }
}