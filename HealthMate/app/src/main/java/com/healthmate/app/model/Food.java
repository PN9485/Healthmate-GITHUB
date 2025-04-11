package com.healthmate.app.model;

/**
 * Class representing a food item in the application
 */
public class Food {
    private String id;
    private String name;
    private float calories; // per serving
    private float proteins; // in grams
    private float carbs;    // in grams
    private float fats;     // in grams
    private float fiber;    // in grams
    private float baseAmount; // standard serving size
    private String unit;    // unit of measurement (g, ml, etc.)
    private String category; // food category (vegetarian, non-vegetarian, vegan)
    private String cuisine; // cuisine type (Indian, Italian, etc.)
    
    /**
     * Default constructor required for database operations
     */
    public Food() {
    }
    
    /**
     * Constructor for Food class
     * @param id Unique identifier
     * @param name Food name
     * @param calories Calories per serving
     * @param proteins Protein content in grams
     * @param carbs Carbohydrate content in grams
     * @param fats Fat content in grams
     * @param fiber Fiber content in grams
     * @param baseAmount Standard serving size
     * @param unit Unit of measurement
     * @param category Food category
     * @param cuisine Cuisine type
     */
    public Food(String id, String name, float calories, float proteins, float carbs, 
                float fats, float fiber, float baseAmount, String unit, 
                String category, String cuisine) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
        this.baseAmount = baseAmount;
        this.unit = unit;
        this.category = category;
        this.cuisine = cuisine;
    }
    
    /**
     * Get food ID
     * @return ID as a String
     */
    public String getId() {
        return id;
    }
    
    /**
     * Set food ID
     * @param id ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get food name
     * @return Name as a String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set food name
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get calorie content
     * @return Calories as a float
     */
    public float getCalories() {
        return calories;
    }
    
    /**
     * Set calorie content
     * @param calories Calories to set
     */
    public void setCalories(float calories) {
        this.calories = calories;
    }
    
    /**
     * Get protein content
     * @return Protein in grams as a float
     */
    public float getProteins() {
        return proteins;
    }
    
    /**
     * Set protein content
     * @param proteins Protein in grams to set
     */
    public void setProteins(float proteins) {
        this.proteins = proteins;
    }
    
    /**
     * Get carbohydrate content
     * @return Carbs in grams as a float
     */
    public float getCarbs() {
        return carbs;
    }
    
    /**
     * Set carbohydrate content
     * @param carbs Carbs in grams to set
     */
    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }
    
    /**
     * Get fat content
     * @return Fat in grams as a float
     */
    public float getFats() {
        return fats;
    }
    
    /**
     * Set fat content
     * @param fats Fat in grams to set
     */
    public void setFats(float fats) {
        this.fats = fats;
    }
    
    /**
     * Get fiber content
     * @return Fiber in grams as a float
     */
    public float getFiber() {
        return fiber;
    }
    
    /**
     * Set fiber content
     * @param fiber Fiber in grams to set
     */
    public void setFiber(float fiber) {
        this.fiber = fiber;
    }
    
    /**
     * Get standard serving size
     * @return Base amount as a float
     */
    public float getBaseAmount() {
        return baseAmount;
    }
    
    /**
     * Set standard serving size
     * @param baseAmount Base amount to set
     */
    public void setBaseAmount(float baseAmount) {
        this.baseAmount = baseAmount;
    }
    
    /**
     * Get unit of measurement
     * @return Unit as a String
     */
    public String getUnit() {
        return unit;
    }
    
    /**
     * Set unit of measurement
     * @param unit Unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * Get food category
     * @return Category as a String
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Set food category
     * @param category Category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Get cuisine type
     * @return Cuisine as a String
     */
    public String getCuisine() {
        return cuisine;
    }
    
    /**
     * Set cuisine type
     * @param cuisine Cuisine to set
     */
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
