package com.healthmate.app.api;

import com.healthmate.app.model.ExerciseSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API interface for Wger Workout Manager API
 */
public interface WgerService {

    /**
     * Get exercises from the API
     * @param language Language code (e.g., "en" for English)
     * @param limit Number of results to return
     * @param offset Offset for pagination
     * @return API response containing exercise data
     */
    @GET("exercise/")
    Call<ExerciseSearchResponse> searchExercises(
            @Query("language") int language,
            @Query("limit") int limit,
            @Query("offset") int offset);
    
    /**
     * Search exercises by name
     * @param term Search term
     * @param language Language code (e.g., "en" for English)
     * @param limit Number of results to return
     * @return API response containing exercise data
     */
    @GET("exercise/search/")
    Call<ExerciseSearchResponse> searchExercisesByName(
            @Query("term") String term,
            @Query("language") int language,
            @Query("limit") int limit);
            
    /**
     * Get exercises by muscle group
     * @param muscleId ID of the muscle group
     * @param language Language code (e.g., "en" for English)
     * @param limit Number of results to return
     * @return API response containing exercise data
     */
    @GET("exercise/")
    Call<ExerciseSearchResponse> getExercisesByMuscle(
            @Query("muscles") int muscleId,
            @Query("language") int language,
            @Query("limit") int limit);
}