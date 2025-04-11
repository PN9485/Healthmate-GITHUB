package com.healthmate.app.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Model representing a food product from the Open Food Facts API
 */
public class Product {
    
    @SerializedName("id")
    private String id;
    
    @SerializedName("product_name")
    private String name;
    
    @SerializedName("image_url")
    private String imageUrl;
    
    @SerializedName("quantity")
    private String quantity;
    
    @SerializedName("brands")
    private String brands;
    
    @SerializedName("categories")
    private String categories;
    
    // Nutritional information
    @SerializedName("nutriments")
    private Nutriments nutriments;
    
    // Standard getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name != null ? name : "Unknown Food";
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public String getBrands() {
        return brands;
    }
    
    public void setBrands(String brands) {
        this.brands = brands;
    }
    
    public String getCategories() {
        return categories;
    }
    
    public void setCategories(String categories) {
        this.categories = categories;
    }
    
    public Nutriments getNutriments() {
        return nutriments;
    }
    
    public void setNutriments(Nutriments nutriments) {
        this.nutriments = nutriments;
    }
    
    /**
     * Convert the API product to the app's Food model
     * @return Food object with data from this product
     */
    public com.healthmate.app.model.Food toFood() {
        com.healthmate.app.model.Food food = new com.healthmate.app.model.Food();
        food.setId(id != null ? id : "unknown");
        food.setName(getName());
        
        if (nutriments != null) {
            food.setCalories((float)nutriments.getEnergyKcal());
            food.setProteins(nutriments.getProteins());
            food.setCarbs(nutriments.getCarbohydrates());
            food.setFats(nutriments.getFat());
            food.setFiber(nutriments.getFiber());
        }
        
        // Set standard values for the base amount
        food.setBaseAmount(100);
        food.setUnit("g");
        
        // Determine category based on available information
        if (categories != null) {
            String lowerCategories = categories.toLowerCase();
            if (lowerCategories.contains("vegan")) {
                food.setCategory("Vegan");
            } else if (lowerCategories.contains("vegetarian") || 
                    !lowerCategories.contains("meat") && 
                    !lowerCategories.contains("fish") && 
                    !lowerCategories.contains("seafood") && 
                    !lowerCategories.contains("egg")) {
                food.setCategory("Vegetarian");
            } else {
                food.setCategory("Non-Vegetarian");
            }
        } else {
            food.setCategory("Unknown");
        }
        
        return food;
    }
}