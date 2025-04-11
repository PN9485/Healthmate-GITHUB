package com.healthmate.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.app.adapter.ExerciseAdapter;
import com.healthmate.app.api.ApiClient;
import com.healthmate.app.api.ExerciseDbService;
import com.healthmate.app.db.DatabaseHelper;
import com.healthmate.app.model.Exercise;
import com.healthmate.app.model.api.ApiExerciseList;
import com.healthmate.app.util.ApiConfig;
import com.healthmate.app.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseSuggestionActivity extends AppCompatActivity {
    private static final String TAG = "ExerciseSuggestion";
    
    private RecyclerView rvExercises;
    private ProgressBar progressBar;
    private TextView tvNoExercises;
    private Button btnAbs, btnArms, btnChest, btnBack, btnLegs, btnFullBody;
    private Map<Button, String> bodyPartMap = new HashMap<>();
    private Map<String, String> apiToAppBodyPartMap = new HashMap<>();
    private ExerciseAdapter adapter;
    private DatabaseHelper dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_suggestion);
        
        // Initialize database helper
        dbHelper = new DatabaseHelper(this);
        
        // Initialize API configuration
        ApiConfig.initialize(getApplicationContext());
        
        // Initialize views
        rvExercises = findViewById(R.id.rvExercises);
        progressBar = findViewById(R.id.progressBar);
        tvNoExercises = findViewById(R.id.tvNoExercises);
        
        // Set up body part to API mapping
        setupBodyPartMapping();
        
        // Set up body part buttons
        setupBodyPartButtons();
        
        // Set up RecyclerView
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(new ArrayList<>());
        rvExercises.setAdapter(adapter);
    }
    
    private void setupBodyPartMapping() {
        // Map our app's body parts to API body parts
        apiToAppBodyPartMap.put("abs", Constants.BODY_PART_ABS);
        apiToAppBodyPartMap.put("arms", Constants.BODY_PART_ARMS);
        apiToAppBodyPartMap.put("back", Constants.BODY_PART_BACK);
        apiToAppBodyPartMap.put("chest", Constants.BODY_PART_CHEST);
        apiToAppBodyPartMap.put("legs", Constants.BODY_PART_LEGS);
        apiToAppBodyPartMap.put("full body", Constants.BODY_PART_FULL_BODY);
    }
    
    private void setupBodyPartButtons() {
        btnAbs = findViewById(R.id.btnAbs);
        btnArms = findViewById(R.id.btnArms);
        btnChest = findViewById(R.id.btnChest);
        btnBack = findViewById(R.id.btnBack);
        btnLegs = findViewById(R.id.btnLegs);
        btnFullBody = findViewById(R.id.btnFullBody);
        
        // Map buttons to body part values for the API
        bodyPartMap.put(btnAbs, "abs");
        bodyPartMap.put(btnArms, "arms");
        bodyPartMap.put(btnChest, "chest");
        bodyPartMap.put(btnBack, "back");
        bodyPartMap.put(btnLegs, "legs");
        bodyPartMap.put(btnFullBody, "full body");
        
        // Set up click listeners for all body part buttons
        for (Button button : bodyPartMap.keySet()) {
            button.setOnClickListener(v -> {
                String bodyPart = bodyPartMap.get(button);
                loadExercisesByBodyPart(bodyPart);
                
                // Reset all buttons to outline style then highlight the selected one
                for (Button b : bodyPartMap.keySet()) {
                    b.setBackgroundResource(android.R.color.transparent);
                    b.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                button.setBackgroundResource(R.color.colorPrimary);
                button.setTextColor(getResources().getColor(android.R.color.white));
            });
        }
    }
    
    private void loadExercisesByBodyPart(String bodyPart) {
        showLoading();
        Log.d(TAG, "Loading exercises for body part: " + bodyPart);
        
        // Use the RapidAPI key
        String apiKey = ApiConfig.EXERCISE_DB_API_KEY;
        
        if (apiKey == null || apiKey.isEmpty()) {
            Log.w(TAG, "API key not found, falling back to local database");
            loadExercisesFromLocalDatabase(bodyPart);
            return;
        }
        
        ExerciseDbService service = ApiClient.getExerciseDbService();
        Call<ApiExerciseList> call = service.getExercisesByBodyPart(
                bodyPart,
                apiKey,
                Constants.EXERCISE_DB_HOST_VALUE);
        
        call.enqueue(new Callback<ApiExerciseList>() {
            @Override
            public void onResponse(Call<ApiExerciseList> call, Response<ApiExerciseList> response) {
                hideLoading();
                
                if (response.isSuccessful() && response.body() != null) {
                    ApiExerciseList exercises = response.body();
                    if (exercises.isEmpty()) {
                        Log.d(TAG, "API returned empty exercise list, falling back to local database");
                        loadExercisesFromLocalDatabase(bodyPart);
                    } else {
                        Log.d(TAG, "Successfully loaded " + exercises.size() + " exercises from API");
                        showExercises();
                        adapter.updateExercises(exercises);
                    }
                } else {
                    Log.w(TAG, "API request failed with code: " + (response.code()));
                    Toast.makeText(ExerciseSuggestionActivity.this, 
                            getString(R.string.error_loading_exercises), Toast.LENGTH_SHORT).show();
                    loadExercisesFromLocalDatabase(bodyPart);
                }
            }
            
            @Override
            public void onFailure(Call<ApiExerciseList> call, Throwable t) {
                hideLoading();
                Log.e(TAG, "API request failed: " + t.getMessage(), t);
                Toast.makeText(ExerciseSuggestionActivity.this, 
                        getString(R.string.error_loading_exercises), Toast.LENGTH_SHORT).show();
                loadExercisesFromLocalDatabase(bodyPart);
            }
        });
    }
    
    private void loadExercisesFromLocalDatabase(String apiBodyPart) {
        String appBodyPart = apiToAppBodyPartMap.get(apiBodyPart);
        if (appBodyPart == null) {
            // If mapping not found, use the default
            appBodyPart = Constants.BODY_PART_FULL_BODY;
        }
        
        Log.d(TAG, "Loading exercises from local database for body part: " + appBodyPart);
        
        // Get exercises from local database
        List<Exercise> exercises = dbHelper.getExercisesByBodyPart(appBodyPart);
        
        if (exercises.isEmpty()) {
            showNoExercises();
            Log.d(TAG, "No exercises found in local database");
        } else {
            showExercises();
            // Convert from local model to API model for display
            ApiExerciseList apiExercises = convertToApiExerciseList(exercises);
            adapter.updateExercises(apiExercises);
            Log.d(TAG, "Successfully loaded " + exercises.size() + " exercises from local database");
        }
    }
    
    private ApiExerciseList convertToApiExerciseList(List<Exercise> exercises) {
        ApiExerciseList apiExercises = new ApiExerciseList();
        
        for (Exercise exercise : exercises) {
            ApiExerciseList.ApiExerciseItem apiExercise = new ApiExerciseList.ApiExerciseItem();
            apiExercise.setId(exercise.getId());
            apiExercise.setName(exercise.getName());
            apiExercise.setBodyPart(exercise.getBodyPart());
            apiExercise.setEquipment("Body weight"); // Default equipment
            apiExercise.setTarget(exercise.getBodyPart()); // Use body part as target
            
            // Create a list of instructions from the instructions string
            List<String> instructionsList = new ArrayList<>();
            String[] instructions = exercise.getInstructions().split("\n");
            for (String instruction : instructions) {
                instructionsList.add(instruction);
            }
            apiExercise.setInstructions(instructionsList);
            
            apiExercises.add(apiExercise);
        }
        
        return apiExercises;
    }
    
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvExercises.setVisibility(View.GONE);
        tvNoExercises.setVisibility(View.GONE);
    }
    
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
    
    private void showExercises() {
        rvExercises.setVisibility(View.VISIBLE);
        tvNoExercises.setVisibility(View.GONE);
    }
    
    private void showNoExercises() {
        rvExercises.setVisibility(View.GONE);
        tvNoExercises.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}