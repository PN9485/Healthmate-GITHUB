package com.healthmate.app.api;

import com.healthmate.app.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton client for API services
 */
public class ApiClient {
    
    private static Retrofit foodRetrofit = null;
    private static Retrofit exerciseRetrofit = null;
    private static OpenFoodFactsService openFoodFactsService = null;
    private static ExerciseDbService exerciseDbService = null;
    
    /**
     * Get a retrofit client instance for food API
     * @return Configured Retrofit instance
     */
    private static Retrofit getFoodClient() {
        if (foodRetrofit == null) {
            // Add logging interceptor for debugging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            
            foodRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.OPEN_FOOD_FACTS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return foodRetrofit;
    }
    
    /**
     * Get a retrofit client instance for exercise API
     * @return Configured Retrofit instance
     */
    private static Retrofit getExerciseClient() {
        if (exerciseRetrofit == null) {
            // Add logging interceptor for debugging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            
            exerciseRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EXERCISE_DB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return exerciseRetrofit;
    }
    
    /**
     * Get the Open Food Facts API service
     * @return Configured OpenFoodFactsService
     */
    public static OpenFoodFactsService getOpenFoodFactsService() {
        if (openFoodFactsService == null) {
            openFoodFactsService = getFoodClient().create(OpenFoodFactsService.class);
        }
        return openFoodFactsService;
    }
    
    /**
     * Get the ExerciseDB API service
     * @return Configured ExerciseDbService
     */
    public static ExerciseDbService getExerciseDbService() {
        if (exerciseDbService == null) {
            exerciseDbService = getExerciseClient().create(ExerciseDbService.class);
        }
        return exerciseDbService;
    }
}