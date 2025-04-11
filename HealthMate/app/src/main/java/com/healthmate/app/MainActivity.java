package com.healthmate.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnFood, btnExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFood = findViewById(R.id.btnFood);
        btnExercise = findViewById(R.id.btnExercise);

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSuggestionActivity.class);
                startActivity(intent);
            }
        });

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseSuggestionActivity.class);
                startActivity(intent);
            }
        });
    }
}