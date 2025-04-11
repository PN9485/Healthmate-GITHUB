package com.healthmate.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.healthmate.app.ExerciseDetailActivity;
import com.healthmate.app.R;
import com.healthmate.app.model.api.ApiExerciseList;

import java.util.List;

/**
 * Adapter for displaying exercises from the ExerciseDB API in a RecyclerView
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    
    private List<ApiExerciseList.ApiExerciseItem> exerciseList;
    private OnExerciseClickListener clickListener;
    
    /**
     * Interface for handling exercise item clicks
     */
    public interface OnExerciseClickListener {
        void onExerciseClick(ApiExerciseList.ApiExerciseItem exercise);
    }
    
    /**
     * Constructor for ExerciseAdapter
     * @param exerciseList List of exercise items to display
     */
    public ExerciseAdapter(List<ApiExerciseList.ApiExerciseItem> exerciseList) {
        this.exerciseList = exerciseList;
    }
    
    /**
     * Constructor with click listener
     * @param exerciseList List of exercise items to display
     * @param clickListener Listener for exercise clicks
     */
    public ExerciseAdapter(List<ApiExerciseList.ApiExerciseItem> exerciseList, OnExerciseClickListener clickListener) {
        this.exerciseList = exerciseList;
        this.clickListener = clickListener;
    }
    
    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ApiExerciseList.ApiExerciseItem exercise = exerciseList.get(position);
        
        // Set exercise name with capitalization
        String name = exercise.getName();
        if (name != null) {
            holder.exerciseName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        } else {
            holder.exerciseName.setText("Unknown Exercise");
        }
        
        // Set body part with capitalization
        String bodyPart = exercise.getBodyPart();
        if (bodyPart != null) {
            holder.exerciseBodyPart.setText(bodyPart.substring(0, 1).toUpperCase() + bodyPart.substring(1));
        } else {
            holder.exerciseBodyPart.setText("General");
        }
        
        // Set equipment with prefix
        String equipment = exercise.getEquipment();
        if (equipment != null) {
            holder.exerciseEquipment.setText("Equipment: " + equipment.substring(0, 1).toUpperCase() + equipment.substring(1));
        } else {
            holder.exerciseEquipment.setText("Equipment: None");
        }
        
        // Set target muscle
        String target = exercise.getTarget();
        if (target != null) {
            holder.exerciseTarget.setText(target.substring(0, 1).toUpperCase() + target.substring(1));
        } else {
            holder.exerciseTarget.setText("Various muscles");
        }
        
        // Load the exercise gif if available
        if (exercise.getGifUrl() != null && !exercise.getGifUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(exercise.getGifUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.exerciseImage);
            holder.exerciseImage.setVisibility(View.VISIBLE);
        } else {
            holder.exerciseImage.setVisibility(View.GONE);
        }
        
        // Set click listener to open detail activity
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onExerciseClick(exercise);
            } else {
                // Default implementation if no listener provided
                openExerciseDetail(holder.itemView.getContext(), exercise);
            }
        });
    }
    
    /**
     * Open the exercise detail view
     * @param context Context to use for starting activity
     * @param exercise The exercise to show details for
     */
    private void openExerciseDetail(Context context, ApiExerciseList.ApiExerciseItem exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra("exercise_id", exercise.getId());
        context.startActivity(intent);
    }
    
    @Override
    public int getItemCount() {
        return exerciseList != null ? exerciseList.size() : 0;
    }
    
    /**
     * Update the adapter's data
     * @param newExercises New list of exercise items
     */
    public void updateExercises(ApiExerciseList newExercises) {
        this.exerciseList = newExercises;
        notifyDataSetChanged();
    }
    
    /**
     * Set click listener for exercise items
     * @param listener The click listener to set
     */
    public void setOnExerciseClickListener(OnExerciseClickListener listener) {
        this.clickListener = listener;
    }
    
    /**
     * ViewHolder for exercise items
     */
    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseBodyPart;
        TextView exerciseEquipment;
        TextView exerciseTarget;
        ImageView exerciseImage;
        
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            
            exerciseName = itemView.findViewById(R.id.tv_exercise_name);
            exerciseBodyPart = itemView.findViewById(R.id.tv_exercise_body_part);
            exerciseEquipment = itemView.findViewById(R.id.tv_exercise_equipment);
            exerciseTarget = itemView.findViewById(R.id.tv_exercise_target);
            exerciseImage = itemView.findViewById(R.id.iv_exercise_image);
        }
    }
}