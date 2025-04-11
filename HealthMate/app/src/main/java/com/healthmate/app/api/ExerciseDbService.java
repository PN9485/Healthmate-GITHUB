package com.healthmate.app.api;

import com.healthmate.app.model.api.ApiExerciseList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API interface for ExerciseDB API from RapidAPI
 */
public interface ExerciseDbService {

    /**
     * Get all exercises
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of exercises
     */
    @GET("exercises")
    Call<ApiExerciseList> getAllExercises(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
    
    /**
     * Get exercise by ID
     * @param id Exercise ID
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return Exercise details
     */
    @GET("exercises/exercise/{id}")
    Call<ApiExerciseList.ApiExerciseItem> getExerciseById(
            @Path("id") String id,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
    
    /**
     * Get exercises by body part
     * @param bodyPart Body part name
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of exercises for the specified body part
     */
    @GET("exercises/bodyPart/{bodyPart}")
    Call<ApiExerciseList> getExercisesByBodyPart(
            @Path("bodyPart") String bodyPart,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
    
    /**
     * Get exercises by target muscle
     * @param target Target muscle name
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of exercises for the specified target muscle
     */
    @GET("exercises/target/{target}")
    Call<ApiExerciseList> getExercisesByTarget(
            @Path("target") String target,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
    
    /**
     * Get exercises by equipment
     * @param equipment Equipment name
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of exercises for the specified equipment
     */
    @GET("exercises/equipment/{equipment}")
    Call<ApiExerciseList> getExercisesByEquipment(
            @Path("equipment") String equipment,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
    
    /**
     * Get all body parts
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of all body parts
     */
    @GET("exercises/bodyPartList")
    Call<ApiExerciseList> getBodyPartList(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
            
    /**
     * Get exercise by name (partial match)
     * @param name Exercise name to search for
     * @param apiKey RapidAPI key
     * @param host RapidAPI host
     * @return List of exercises matching the name
     */
    @GET("exercises/name/{name}")
    Call<ApiExerciseList> getExercisesByName(
            @Path("name") String name,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host);
}