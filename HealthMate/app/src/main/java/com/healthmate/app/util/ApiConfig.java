package com.healthmate.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

/**
 * Configuration class for API keys and other sensitive information
 */
public class ApiConfig {
    private static final String TAG = "ApiConfig";
    
    // RapidAPI key for ExerciseDB API - direct use of environment variable
    // The default will be overridden in initialize()
    public static String EXERCISE_DB_API_KEY = System.getenv("RAPID_API_KEY") != null ? 
            System.getenv("RAPID_API_KEY") : "";
    
    /**
     * Initialize API keys from the application metadata if not already set
     * @param context Application context
     */
    public static void initialize(Context context) {
        // If the API key is already set from environment variable, we're done
        if (!EXERCISE_DB_API_KEY.isEmpty()) {
            Log.d(TAG, "API key already initialized from environment");
            return;
        }
        
        try {
            // Try to get from system environment variables again just to be sure
            String apiKey = System.getenv("RAPID_API_KEY");
            
            // If not found in environment, try to get from manifest metadata
            if (apiKey == null || apiKey.isEmpty()) {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(
                        context.getPackageName(), PackageManager.GET_META_DATA);
                
                if (packageInfo.applicationInfo.metaData != null) {
                    apiKey = packageInfo.applicationInfo.metaData.getString("com.healthmate.app.RAPID_API_KEY");
                }
            }
            
            // Set the API key if found
            if (apiKey != null && !apiKey.isEmpty()) {
                EXERCISE_DB_API_KEY = apiKey;
                Log.d(TAG, "API key initialized successfully");
            } else {
                // This might be a release build without actual key in code
                Log.d(TAG, "No API key found in environment or manifest");
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Failed to initialize API key", e);
        }
    }
    
    /**
     * Set the API key directly (useful for testing or when provided by user)
     * @param apiKey The API key to set
     */
    public static void setApiKey(String apiKey) {
        if (apiKey != null && !apiKey.isEmpty()) {
            EXERCISE_DB_API_KEY = apiKey;
            Log.d(TAG, "API key set manually");
        }
    }
}