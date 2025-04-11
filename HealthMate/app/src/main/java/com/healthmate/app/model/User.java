package com.healthmate.app.model;

import com.healthmate.app.util.BMICalculator;

/**
 * Class representing a user in the application
 */
public class User {
    private String name;
    private int age;
    private float height; // in cm
    private float weight; // in kg
    private float bmi;
    
    /**
     * Constructor for User class
     * @param name User's name
     * @param age User's age
     * @param height User's height in cm
     * @param weight User's weight in kg
     */
    public User(String name, int age, float height, float weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = BMICalculator.calculateBMI(height, weight);
    }
    
    /**
     * Get user's name
     * @return Name as a String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set user's name
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get user's age
     * @return Age as an integer
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Set user's age
     * @param age Age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Get user's height
     * @return Height in cm as a float
     */
    public float getHeight() {
        return height;
    }
    
    /**
     * Set user's height
     * @param height Height in cm to set
     */
    public void setHeight(float height) {
        this.height = height;
        updateBMI();
    }
    
    /**
     * Get user's weight
     * @return Weight in kg as a float
     */
    public float getWeight() {
        return weight;
    }
    
    /**
     * Set user's weight
     * @param weight Weight in kg to set
     */
    public void setWeight(float weight) {
        this.weight = weight;
        updateBMI();
    }
    
    /**
     * Get user's BMI
     * @return BMI as a float
     */
    public float getBmi() {
        return bmi;
    }
    
    /**
     * Set user's BMI explicitly
     * @param bmi BMI value to set
     */
    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
    
    /**
     * Update BMI based on current height and weight
     */
    private void updateBMI() {
        this.bmi = BMICalculator.calculateBMI(height, weight);
    }
    
    /**
     * Get BMI category based on current BMI value
     * @return BMI category as a String
     */
    public String getBmiCategory() {
        return BMICalculator.getBMICategory(bmi);
    }
}
