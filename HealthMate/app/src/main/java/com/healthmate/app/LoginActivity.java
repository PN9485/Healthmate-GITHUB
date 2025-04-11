package com.healthmate.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.healthmate.app.model.User;
import com.healthmate.app.util.BMICalculator;
import com.healthmate.app.util.Constants;

public class LoginActivity extends AppCompatActivity {

    // UI Elements
    private TextInputLayout nameInputLayout, ageInputLayout, heightInputLayout, weightInputLayout;
    private EditText nameEditText, ageEditText, heightEditText, weightEditText;
    private Button calculateBmiButton;
    private TextView bmiResultTextView, bmiCategoryTextView;
    private Button proceedButton;
    
    // User data
    private User user;
    
    // Shared preferences to save user data
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize shared preferences
        preferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        
        // Initialize UI elements
        initializeViews();
        
        // Load saved user data if available
        loadUserData();
        
        // Set click listeners
        setClickListeners();
    }
    
    /**
     * Initialize all UI elements
     */
    private void initializeViews() {
        nameInputLayout = findViewById(R.id.nameInputLayout);
        ageInputLayout = findViewById(R.id.ageInputLayout);
        heightInputLayout = findViewById(R.id.heightInputLayout);
        weightInputLayout = findViewById(R.id.weightInputLayout);
        
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        
        calculateBmiButton = findViewById(R.id.calculateBmiButton);
        bmiResultTextView = findViewById(R.id.bmiResultTextView);
        bmiCategoryTextView = findViewById(R.id.bmiCategoryTextView);
        proceedButton = findViewById(R.id.proceedButton);
        
        // Initially hide BMI result and proceed button
        bmiResultTextView.setVisibility(View.GONE);
        bmiCategoryTextView.setVisibility(View.GONE);
        proceedButton.setVisibility(View.GONE);
    }
    
    /**
     * Set click listeners for buttons
     */
    private void setClickListeners() {
        calculateBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    calculateAndDisplayBMI();
                }
            }
        });
        
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
                navigateToFoodSuggestion();
            }
        });
    }
    
    /**
     * Load user data from shared preferences if available
     */
    private void loadUserData() {
        String name = preferences.getString(Constants.KEY_NAME, "");
        int age = preferences.getInt(Constants.KEY_AGE, 0);
        float height = preferences.getFloat(Constants.KEY_HEIGHT, 0);
        float weight = preferences.getFloat(Constants.KEY_WEIGHT, 0);
        
        if (!TextUtils.isEmpty(name) && age > 0 && height > 0 && weight > 0) {
            nameEditText.setText(name);
            ageEditText.setText(String.valueOf(age));
            heightEditText.setText(String.valueOf(height));
            weightEditText.setText(String.valueOf(weight));
            
            user = new User(name, age, height, weight);
            float bmi = user.getBmi();
            
            if (bmi > 0) {
                displayBMI(bmi);
            }
        }
    }
    
    /**
     * Save user data to shared preferences
     */
    private void saveUserData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_NAME, user.getName());
        editor.putInt(Constants.KEY_AGE, user.getAge());
        editor.putFloat(Constants.KEY_HEIGHT, user.getHeight());
        editor.putFloat(Constants.KEY_WEIGHT, user.getWeight());
        editor.putFloat(Constants.KEY_BMI, user.getBmi());
        editor.apply();
    }
    
    /**
     * Validate user inputs
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        boolean isValid = true;
        
        String name = nameEditText.getText().toString().trim();
        String ageStr = ageEditText.getText().toString().trim();
        String heightStr = heightEditText.getText().toString().trim();
        String weightStr = weightEditText.getText().toString().trim();
        
        // Validate name
        if (TextUtils.isEmpty(name)) {
            nameInputLayout.setError("Name is required");
            isValid = false;
        } else {
            nameInputLayout.setError(null);
        }
        
        // Validate age
        if (TextUtils.isEmpty(ageStr)) {
            ageInputLayout.setError("Age is required");
            isValid = false;
        } else {
            try {
                int age = Integer.parseInt(ageStr);
                if (age < 1 || age > 120) {
                    ageInputLayout.setError("Please enter a valid age (1-120)");
                    isValid = false;
                } else {
                    ageInputLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                ageInputLayout.setError("Please enter a valid number");
                isValid = false;
            }
        }
        
        // Validate height
        if (TextUtils.isEmpty(heightStr)) {
            heightInputLayout.setError("Height is required");
            isValid = false;
        } else {
            try {
                float height = Float.parseFloat(heightStr);
                if (height < 50 || height > 300) {
                    heightInputLayout.setError("Please enter a valid height in cm (50-300)");
                    isValid = false;
                } else {
                    heightInputLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                heightInputLayout.setError("Please enter a valid number");
                isValid = false;
            }
        }
        
        // Validate weight
        if (TextUtils.isEmpty(weightStr)) {
            weightInputLayout.setError("Weight is required");
            isValid = false;
        } else {
            try {
                float weight = Float.parseFloat(weightStr);
                if (weight < 20 || weight > 500) {
                    weightInputLayout.setError("Please enter a valid weight in kg (20-500)");
                    isValid = false;
                } else {
                    weightInputLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                weightInputLayout.setError("Please enter a valid number");
                isValid = false;
            }
        }
        
        return isValid;
    }
    
    /**
     * Calculate and display BMI
     */
    private void calculateAndDisplayBMI() {
        String name = nameEditText.getText().toString().trim();
        int age = Integer.parseInt(ageEditText.getText().toString().trim());
        float height = Float.parseFloat(heightEditText.getText().toString().trim());
        float weight = Float.parseFloat(weightEditText.getText().toString().trim());
        
        user = new User(name, age, height, weight);
        float bmi = BMICalculator.calculateBMI(height, weight);
        user.setBmi(bmi);
        
        displayBMI(bmi);
    }
    
    /**
     * Display BMI result and category
     * @param bmi BMI value to display
     */
    private void displayBMI(float bmi) {
        String bmiCategory = BMICalculator.getBMICategory(bmi);
        
        bmiResultTextView.setText(String.format("Your BMI: %.1f", bmi));
        bmiCategoryTextView.setText(String.format("Category: %s", bmiCategory));
        
        bmiResultTextView.setVisibility(View.VISIBLE);
        bmiCategoryTextView.setVisibility(View.VISIBLE);
        proceedButton.setVisibility(View.VISIBLE);
    }
    
    /**
     * Navigate to the Food Suggestion screen
     */
    private void navigateToFoodSuggestion() {
        Intent intent = new Intent(this, FoodSuggestionActivity.class);
        startActivity(intent);
    }
}
