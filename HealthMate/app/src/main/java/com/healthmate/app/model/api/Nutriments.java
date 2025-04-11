package com.healthmate.app.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Model for nutritional information from Open Food Facts API
 */
public class Nutriments {
    
    @SerializedName("energy-kcal_100g")
    private double energyKcal;
    
    @SerializedName("proteins_100g")
    private float proteins;
    
    @SerializedName("carbohydrates_100g")
    private float carbohydrates;
    
    @SerializedName("fat_100g")
    private float fat;
    
    @SerializedName("fiber_100g")
    private float fiber;
    
    @SerializedName("salt_100g")
    private float salt;
    
    @SerializedName("sugars_100g")
    private float sugars;
    
    // Getters and setters
    public double getEnergyKcal() {
        return energyKcal;
    }
    
    public void setEnergyKcal(double energyKcal) {
        this.energyKcal = energyKcal;
    }
    
    public float getProteins() {
        return proteins;
    }
    
    public void setProteins(float proteins) {
        this.proteins = proteins;
    }
    
    public float getCarbohydrates() {
        return carbohydrates;
    }
    
    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    
    public float getFat() {
        return fat;
    }
    
    public void setFat(float fat) {
        this.fat = fat;
    }
    
    public float getFiber() {
        return fiber;
    }
    
    public void setFiber(float fiber) {
        this.fiber = fiber;
    }
    
    public float getSalt() {
        return salt;
    }
    
    public void setSalt(float salt) {
        this.salt = salt;
    }
    
    public float getSugars() {
        return sugars;
    }
    
    public void setSugars(float sugars) {
        this.sugars = sugars;
    }
}