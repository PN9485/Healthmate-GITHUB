package com.healthmate.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.healthmate.app.api.ApiClient;
import com.healthmate.app.api.ExerciseDbService;
import com.healthmate.app.db.DatabaseHelper;
import com.healthmate.app.model.Exercise;
import com.healthmate.app.model.api.ApiExerciseList;
import com.healthmate.app.util.ApiConfig;
import com.healthmate.app.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for displaying detailed exercise information
 */
public class ExerciseDetailActivity extends AppCompatActivity {
    private static final String TAG = "ExerciseDetail";

    // Views
    private ImageView ivExerciseImage;
    private TextView tvExerciseName;
    private TextView tvBodyPart;
    private TextView tvTarget;
    private TextView tvEquipment;
    private TextView tvInstructions;
    private TextView tvSecondaryMuscles;
    private View loadingIndicator;

    // Data
    private String exerciseId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Setup back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.exercise_detail_title);
        }

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        initViews();

        // Get exercise ID from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("exercise_id")) {
            exerciseId = intent.getStringExtra("exercise_id");
            loadExerciseDetails(exerciseId);
        } else {
            Toast.makeText(this, "Error: No exercise ID provided", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        ivExerciseImage = findViewById(R.id.iv_exercise_detail_image);
        tvExerciseName = findViewById(R.id.tv_exercise_detail_name);
        tvBodyPart = findViewById(R.id.tv_exercise_detail_body_part);
        tvTarget = findViewById(R.id.tv_exercise_detail_target);
        tvEquipment = findViewById(R.id.tv_exercise_detail_equipment);
        tvInstructions = findViewById(R.id.tv_exercise_detail_instructions);
        tvSecondaryMuscles = findViewById(R.id.tv_exercise_detail_secondary_muscles);
        loadingIndicator = findViewById(R.id.loading_indicator);
    }

    private void loadExerciseDetails(String exerciseId) {
        showLoading(true);

        // Try to get from API first
        String apiKey = ApiConfig.EXERCISE_DB_API_KEY;
        if (apiKey != null && !apiKey.isEmpty()) {
            loadExerciseFromApi(exerciseId, apiKey);
        } else {
            // If no API key, fall back to local database
            loadExerciseFromDatabase(exerciseId);
        }
    }

    private void loadExerciseFromApi(String exerciseId, String apiKey) {
        ExerciseDbService service = ApiClient.getExerciseDbService();
        Call<ApiExerciseList.ApiExerciseItem> call = service.getExerciseById(
                exerciseId,
                apiKey,
                Constants.EXERCISE_DB_HOST_VALUE);

        call.enqueue(new Callback<ApiExerciseList.ApiExerciseItem>() {
            @Override
            public void onResponse(Call<ApiExerciseList.ApiExerciseItem> call, Response<ApiExerciseList.ApiExerciseItem> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    displayExerciseDetail(response.body());
                } else {
                    Toast.makeText(ExerciseDetailActivity.this,
                            "Error loading from API: " + response.code(), Toast.LENGTH_SHORT).show();
                    // Fall back to local database
                    loadExerciseFromDatabase(exerciseId);
                }
            }

            @Override
            public void onFailure(Call<ApiExerciseList.ApiExerciseItem> call, Throwable t) {
                showLoading(false);
                Toast.makeText(ExerciseDetailActivity.this,
                        "Failed to load exercise details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Fall back to local database
                loadExerciseFromDatabase(exerciseId);
            }
        });
    }

    private void loadExerciseFromDatabase(String exerciseId) {
        // This is a simplified example; in a real app, you would query the database by ID
        List<Exercise> allExercises = dbHelper.getAllExercises();
        Exercise foundExercise = null;

        for (Exercise exercise : allExercises) {
            if (exercise.getId().equals(exerciseId)) {
                foundExercise = exercise;
                break;
            }
        }

        if (foundExercise != null) {
            displayExerciseFromLocalModel(foundExercise);
        } else {
            Toast.makeText(this, "Exercise not found in local database", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayExerciseDetail(ApiExerciseList.ApiExerciseItem exercise) {
        // Set exercise name with capitalization
        String name = exercise.getName();
        if (name != null) {
            tvExerciseName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        }

        // Set body part
        String bodyPart = exercise.getBodyPart();
        if (bodyPart != null) {
            tvBodyPart.setText(bodyPart.substring(0, 1).toUpperCase() + bodyPart.substring(1));
        }

        // Set target muscle
        String target = exercise.getTarget();
        if (target != null) {
            tvTarget.setText(target.substring(0, 1).toUpperCase() + target.substring(1));
        }

        // Set equipment
        String equipment = exercise.getEquipment();
        if (equipment != null) {
            tvEquipment.setText(equipment.substring(0, 1).toUpperCase() + equipment.substring(1));
        }

        // Set instructions
        List<String> instructions = exercise.getInstructions();
        if (instructions != null && !instructions.isEmpty()) {
            StringBuilder formattedInstructions = new StringBuilder();
            for (int i = 0; i < instructions.size(); i++) {
                formattedInstructions.append(i + 1).append(". ").append(instructions.get(i));
                if (i < instructions.size() - 1) {
                    formattedInstructions.append("\n\n");
                }
            }
            tvInstructions.setText(formattedInstructions.toString());
        } else {
            tvInstructions.setText("No instructions available.");
        }

        // Set secondary muscles
        List<String> secondaryMuscles = exercise.getSecondaryMuscles();
        if (secondaryMuscles != null && !secondaryMuscles.isEmpty()) {
            StringBuilder muscles = new StringBuilder();
            for (int i = 0; i < secondaryMuscles.size(); i++) {
                String muscle = secondaryMuscles.get(i);
                muscles.append(muscle.substring(0, 1).toUpperCase()).append(muscle.substring(1));
                if (i < secondaryMuscles.size() - 1) {
                    muscles.append(", ");
                }
            }
            tvSecondaryMuscles.setText(muscles.toString());
            tvSecondaryMuscles.setVisibility(View.VISIBLE);
        } else {
            tvSecondaryMuscles.setVisibility(View.GONE);
        }

        // Load the exercise gif if available
        if (exercise.getGifUrl() != null && !exercise.getGifUrl().isEmpty()) {
            Glide.with(this)
                    .load(exercise.getGifUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivExerciseImage);
            ivExerciseImage.setVisibility(View.VISIBLE);
        } else {
            ivExerciseImage.setVisibility(View.GONE);
        }
    }

    private void displayExerciseFromLocalModel(Exercise exercise) {
        // Set name
        tvExerciseName.setText(exercise.getName());

        // Set body part
        tvBodyPart.setText(exercise.getBodyPart());

        // Set target (use body part as default)
        tvTarget.setText(exercise.getBodyPart());

        // Set equipment (not available in local model)
        tvEquipment.setText("Body weight");

        // Set instructions
        tvInstructions.setText(exercise.getInstructions());

        // Hide secondary muscles (not available in local model)
        tvSecondaryMuscles.setVisibility(View.GONE);

        // No image available for local model
        ivExerciseImage.setVisibility(View.GONE);
    }

    private void showLoading(boolean show) {
        loadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        ivExerciseImage.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}