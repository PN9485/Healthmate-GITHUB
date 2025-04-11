package com.healthmate.app.util;

/**
 * Utility class for BMI calculations
 */
public class BMICalculator {
    
    /**
     * Calculate BMI using height in cm and weight in kg
     * Formula: BMI = weight(kg) / (height(m) * height(m))
     * @param heightInCm Height in centimeters
     * @param weightInKg Weight in kilograms
     * @return BMI value as a float
     */
    public static float calculateBMI(float heightInCm, float weightInKg) {
        // Convert height from cm to meters
        float heightInMeters = heightInCm / 100f;
        
        // Calculate BMI
        return weightInKg / (heightInMeters * heightInMeters);
    }
    
    /**
     * Get BMI category based on BMI value
     * @param bmi BMI value
     * @return BMI category as a String
     */
    public static String getBMICategory(float bmi) {
        if (bmi < 16.0) {
            return "Severely Underweight";
        } else if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal Weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else if (bmi < 35.0) {
            return "Obese (Class I)";
        } else if (bmi < 40.0) {
            return "Obese (Class II)";
        } else {
            return "Obese (Class III)";
        }
    }
    
    /**
     * Get a description for the BMI category
     * @param bmi BMI value
     * @return Description as a String
     */
    public static String getBMICategoryDescription(float bmi) {
        if (bmi < 16.0) {
            return "You are severely underweight. Please consult a healthcare professional for a personalized diet plan to gain weight in a healthy way.";
        } else if (bmi < 18.5) {
            return "You are underweight. Focus on nutrient-dense foods to gain weight and build muscle mass.";
        } else if (bmi < 25.0) {
            return "You are at a normal weight. Maintain a balanced diet and regular exercise to stay healthy.";
        } else if (bmi < 30.0) {
            return "You are overweight. Consider moderating your calorie intake and increasing physical activity.";
        } else if (bmi < 35.0) {
            return "You are in Class I obesity. It's recommended to reduce calorie intake and increase exercise under professional guidance.";
        } else if (bmi < 40.0) {
            return "You are in Class II obesity. Please consult a healthcare professional for a personalized weight management plan.";
        } else {
            return "You are in Class III obesity. Medical supervision is strongly recommended for weight management.";
        }
    }
    
    /**
     * Get daily calorie requirement based on BMI, age, and gender
     * This is a simplified calculation
     * @param bmi BMI value
     * @param age Age in years
     * @param isMale Gender (true for male, false for female)
     * @return Daily calorie requirement
     */
    public static int getDailyCalorieRequirement(float bmi, int age, boolean isMale) {
        int baseCalories;
        
        // Base calories by gender and age
        if (isMale) {
            if (age < 30) {
                baseCalories = 2500;
            } else if (age < 50) {
                baseCalories = 2300;
            } else {
                baseCalories = 2100;
            }
        } else {
            if (age < 30) {
                baseCalories = 2000;
            } else if (age < 50) {
                baseCalories = 1900;
            } else {
                baseCalories = 1800;
            }
        }
        
        // Adjust based on BMI
        if (bmi < 18.5) {
            // Underweight: increase calories
            return (int)(baseCalories * 1.1);
        } else if (bmi < 25.0) {
            // Normal weight: maintain
            return baseCalories;
        } else if (bmi < 30.0) {
            // Overweight: slight reduction
            return (int)(baseCalories * 0.9);
        } else {
            // Obese: moderate reduction
            return (int)(baseCalories * 0.8);
        }
    }
}
