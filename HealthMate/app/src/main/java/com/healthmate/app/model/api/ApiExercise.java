package com.healthmate.app.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for exercise data from the ExerciseDB API
 */
public class ApiExercise {
    
    private int id;
    private String name;
    private String description;
    private int category;
    
    @SerializedName("muscles")
    private List<Integer> muscles;
    
    @SerializedName("muscles_secondary")
    private List<Integer> secondaryMuscles;
    
    @SerializedName("equipment")
    private List<Integer> equipment;
    
    @SerializedName("language")
    private int languageId;
    
    @SerializedName("license")
    private int licenseId;
    
    private String license_author;
    private String variations;
    
    /**
     * Default constructor
     */
    public ApiExercise() {
    }
    
    /**
     * Get exercise ID
     * @return ID as an integer
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set exercise ID
     * @param id ID to set
     */
    public void setId(int id) {
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
     * Get exercise category ID
     * @return Category ID as an integer
     */
    public int getCategory() {
        return category;
    }
    
    /**
     * Set exercise category ID
     * @param category Category ID to set
     */
    public void setCategory(int category) {
        this.category = category;
    }
    
    /**
     * Get primary muscles worked
     * @return List of muscle IDs
     */
    public List<Integer> getMuscles() {
        return muscles;
    }
    
    /**
     * Set primary muscles worked
     * @param muscles List of muscle IDs to set
     */
    public void setMuscles(List<Integer> muscles) {
        this.muscles = muscles;
    }
    
    /**
     * Get secondary muscles worked
     * @return List of muscle IDs
     */
    public List<Integer> getSecondaryMuscles() {
        return secondaryMuscles;
    }
    
    /**
     * Set secondary muscles worked
     * @param secondaryMuscles List of muscle IDs to set
     */
    public void setSecondaryMuscles(List<Integer> secondaryMuscles) {
        this.secondaryMuscles = secondaryMuscles;
    }
    
    /**
     * Get equipment needed
     * @return List of equipment IDs
     */
    public List<Integer> getEquipment() {
        return equipment;
    }
    
    /**
     * Set equipment needed
     * @param equipment List of equipment IDs to set
     */
    public void setEquipment(List<Integer> equipment) {
        this.equipment = equipment;
    }
    
    /**
     * Get language ID
     * @return Language ID as an integer
     */
    public int getLanguageId() {
        return languageId;
    }
    
    /**
     * Set language ID
     * @param languageId Language ID to set
     */
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    
    /**
     * Get license ID
     * @return License ID as an integer
     */
    public int getLicenseId() {
        return licenseId;
    }
    
    /**
     * Set license ID
     * @param licenseId License ID to set
     */
    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }
    
    /**
     * Get license author
     * @return License author as a String
     */
    public String getLicense_author() {
        return license_author;
    }
    
    /**
     * Set license author
     * @param license_author License author to set
     */
    public void setLicense_author(String license_author) {
        this.license_author = license_author;
    }
    
    /**
     * Get variations of the exercise
     * @return Variations as a String
     */
    public String getVariations() {
        return variations;
    }
    
    /**
     * Set variations of the exercise
     * @param variations Variations to set
     */
    public void setVariations(String variations) {
        this.variations = variations;
    }
}