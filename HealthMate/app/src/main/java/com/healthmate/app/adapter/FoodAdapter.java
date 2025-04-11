package com.healthmate.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.app.R;
import com.healthmate.app.model.Food;

import java.util.List;

/**
 * Adapter for displaying food items in a RecyclerView
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    
    private List<Food> foodList;
    private Context context;
    private OnFoodItemClickListener listener;
    
    /**
     * Interface for handling food item clicks
     */
    public interface OnFoodItemClickListener {
        void onFoodItemClick(Food food);
    }
    
    /**
     * Constructor for FoodAdapter
     * @param context Context for inflating views
     * @param foodList List of food items to display
     * @param listener Click listener for food items
     */
    public FoodAdapter(Context context, List<Food> foodList, OnFoodItemClickListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        
        holder.foodName.setText(food.getName());
        holder.foodCalories.setText(String.format("%.0f cal", food.getCalories()));
        holder.foodCategory.setText(food.getCategory());
        
        // Format nutrition facts
        holder.foodNutritionFacts.setText(String.format(
                "P: %.1fg | C: %.1fg | F: %.1fg | Fiber: %.1fg",
                food.getProteins(), food.getCarbs(), food.getFats(), food.getFiber()));
        
        // Show serving size
        holder.foodServingSize.setText(String.format(
                "Serving size: %.0f%s", food.getBaseAmount(), food.getUnit()));
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodItemClick(food);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }
    
    /**
     * Update the adapter's data
     * @param newFoodList New list of food items
     */
    public void updateData(List<Food> newFoodList) {
        this.foodList = newFoodList;
        notifyDataSetChanged();
    }
    
    /**
     * ViewHolder for food items
     */
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodCalories;
        TextView foodCategory;
        TextView foodNutritionFacts;
        TextView foodServingSize;
        
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            
            foodName = itemView.findViewById(R.id.tv_food_name);
            foodCalories = itemView.findViewById(R.id.tv_food_calories);
            foodCategory = itemView.findViewById(R.id.tv_food_category);
            foodNutritionFacts = itemView.findViewById(R.id.tv_food_nutrition_facts);
            foodServingSize = itemView.findViewById(R.id.tv_food_serving_size);
        }
    }
}