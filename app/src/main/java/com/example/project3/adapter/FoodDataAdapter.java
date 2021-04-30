/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.project3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.project3.R;
import com.example.project3.model.Food;
import com.example.project3.util.FoodUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Vector;

public class FoodDataAdapter extends RecyclerView.Adapter {

    private static String TAG = "FoodDataAdapter";

    private final int MACRONUTRIENT_TARGETS = 0, MINVIT_TARGETS = 1, CALORIES_CONSUMED_TARGETS = 2,
            CALORIES_BURNED = 3, CALORIES_REMAINING = 4, NUTRITION_SCORES = 5, COMPLETE_NUTRITION = 6;

    private int calories, protein, carbs, fat, calorie_percentage, protein_percentage, carbs_percentage, fat_percentage;

    private Vector<Food> foods;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FoodDataAdapter(Vector<Food> foods) {
        this.foods = foods;
        calories = (int) foods.stream().mapToDouble(Food::getCalories).sum();
        protein = (int) foods.stream().mapToDouble(Food::getProtein).sum();
        carbs = (int) foods.stream().mapToDouble(Food::getTotalCarb).sum();
        fat = (int) foods.stream().mapToDouble(Food::getTotalFat).sum();

        calorie_percentage = (calories * 100) / 2500;
        protein_percentage = (protein * 100) / 50;
        carbs_percentage = (carbs * 100) / 300;
        fat_percentage = (fat * 100) / 50;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "value of position in getItemViewType is: " + String.valueOf(position));
        switch (position) {
            case MACRONUTRIENT_TARGETS: return R.layout.macronutrient_card_view;
            case MINVIT_TARGETS: return R.layout.minvit_card_view;
            case CALORIES_CONSUMED_TARGETS: return R.layout.calories_consumed;
            case CALORIES_BURNED: return R.layout.calories_burned;
            case CALORIES_REMAINING: return R.layout.calories_remaining;
            case NUTRITION_SCORES: return R.layout.nutrition_score;
            case COMPLETE_NUTRITION: return R.layout.nutrient_total;
            default:
                return -1;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public RecyclerView.ViewHolder generateViewHolder(View v, int viewType) {
        Log.d(TAG, "The value of viewType in generateViewHolder is " + String.valueOf(viewType));
        switch (viewType) {
            case R.layout.macronutrient_card_view: return new MacronutrientHolder(v);
            case R.layout.minvit_card_view: return new MinVitHolder(v);
            case R.layout.calories_consumed: return new CaloriesConsumedHolder(v);
            case R.layout.calories_burned: return new CaloriesBurnedHolder(v);
            case R.layout.calories_remaining: return new CaloriesRemainingHolder(v);
            case R.layout.nutrition_score: return new NutritionScoresHolder(v);
            case R.layout.nutrient_total: return new NutritionTotalHolder(v);
            default: return null;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "value of viewType in onCreateViewHolder is: " + String.valueOf(viewType));
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return generateViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "The value of position in onBindViewHolder is: " + String.valueOf(position));
        switch(position) {
            case MACRONUTRIENT_TARGETS:
                initializeMacronutrientHolder((MacronutrientHolder) holder);
                break;
            case MINVIT_TARGETS:
                initializeMinVitHolder((MinVitHolder) holder);
                break;
            case CALORIES_CONSUMED_TARGETS:
                initializeCaloriesConsumedHOlder((CaloriesConsumedHolder) holder);
                break;
            case CALORIES_BURNED:
                initializeCaloriesBurnedHolder((CaloriesBurnedHolder) holder);
                break;
            case CALORIES_REMAINING:
                 initializeCaloriesRemainingHolder((CaloriesRemainingHolder) holder);
                 break;
            case NUTRITION_SCORES:
                initializeNutritionScoresHolder((NutritionScoresHolder) holder);
                break;
            case COMPLETE_NUTRITION:
                initializeNutritionTotalHolder((NutritionTotalHolder) holder);
                break;
        }

        /*Food food = foods.get(position);
        if ((position & 1) == 1) {
            holder.getItemView().setBackgroundColor(Color.rgb(238, 233, 233));
        } else {
            holder.getItemView().setBackgroundColor(Color.rgb(255, 255, 255));
        }
        holder.foodName.setText(food.getFoodName());
        holder.servings.setText(String.valueOf(food.getServings()) + " servings");
        holder.calories.setText(String.valueOf(food.getServings() * food.getCaloriesPerServing()));
        holder.calorie_number.setText("kcal");*/
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class MacronutrientHolder extends RecyclerView.ViewHolder {
        ProgressBar calories_pb, protein_pb, carbs_pb, fat_pb;
        TextView calories_percentageTextView, protein_percentageTextView, carbs_percentageTextView, fat_percentageTextView, caloriesTextView, proteinTextView, carbsTextView, fatTextView;

        public MacronutrientHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "Just called constructor for MacronutrientHolder");
            calories_pb = itemView.findViewById(R.id.calories_pb);
            protein_pb = itemView.findViewById(R.id.protein_pb);
            carbs_pb = itemView.findViewById(R.id.carbs_pb);
            fat_pb = itemView.findViewById(R.id.fat_pb);

            caloriesTextView = itemView.findViewById(R.id.calories_number);
            proteinTextView = itemView.findViewById(R.id.protein_number);
            carbsTextView = itemView.findViewById(R.id.carbs_number);
            fatTextView = itemView.findViewById(R.id.fat_number);

            calories_percentageTextView = itemView.findViewById(R.id.calorie_percentage);
            protein_percentageTextView = itemView.findViewById(R.id.protein_percentage);
            carbs_percentageTextView = itemView.findViewById(R.id.carbs_percentage);
            fat_percentageTextView = itemView.findViewById(R.id.fat_percentage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }
        public View getItemView() {
            return itemView;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initializeMacronutrientHolder(MacronutrientHolder holder) {
        holder.calories_pb.setProgress(calorie_percentage);
        holder.protein_pb.setProgress(protein_percentage);
        holder.carbs_pb.setProgress(carbs_percentage);
        holder.fat_pb.setProgress(fat_percentage);

        holder.caloriesTextView.setText(String.valueOf(calories) + " / 2500");
        holder.proteinTextView.setText(String.valueOf(protein) + " / 50");
        holder.carbsTextView.setText(String.valueOf(carbs) + " / 300");
        holder.fatTextView.setText(String.valueOf(fat) + " / 50");

        holder.calories_percentageTextView.setText(String.valueOf(calorie_percentage) + " %");
        holder.protein_percentageTextView.setText(String.valueOf(protein_percentage) + " %");
        holder.carbs_percentageTextView.setText(String.valueOf(carbs_percentage) + " %");
        holder.fat_percentageTextView.setText(String.valueOf(fat_percentage) + " %");
    }

    public static class MinVitHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public MinVitHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void initializeMinVitHolder(MinVitHolder holder) {

    }

    public static class CaloriesConsumedHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public CaloriesConsumedHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void initializeCaloriesConsumedHOlder(CaloriesConsumedHolder holder) {

    }

    public static class CaloriesBurnedHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public CaloriesBurnedHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void initializeCaloriesBurnedHolder(CaloriesBurnedHolder holder) {

    }

    public static class CaloriesRemainingHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public CaloriesRemainingHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void initializeCaloriesRemainingHolder(CaloriesRemainingHolder holder) {

    }


    public static class NutritionScoresHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public NutritionScoresHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() {
            return itemView;
        }
    }

    private void initializeNutritionScoresHolder(NutritionScoresHolder holder) {

    }

    public static class NutritionTotalHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public NutritionTotalHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
            calorie_number = itemView.findViewById(R.id.calories_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }

        public View getItemView() { return itemView; }
    }

    private void initializeNutritionTotalHolder(NutritionTotalHolder holder) {
    }
}

