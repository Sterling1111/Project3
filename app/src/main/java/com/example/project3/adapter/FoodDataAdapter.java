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
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.project3.R;
import com.example.project3.model.Food;
import com.example.project3.util.FoodUtil;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Vector;

public class FoodDataAdapter extends RecyclerView.Adapter {

    private static String TAG = "FoodDataAdapter";

    private final int MACRONUTRIENT_TARGETS = 0, MINVIT_TARGETS = 1, CALORIES_CONSUMED_TARGETS = 2,
            CALORIES_BURNED = 3, CALORIES_REMAINING = 4, NUTRITION_SCORES = 5, COMPLETE_NUTRITION = 6;

    private int calories, protein, carbs, fat, calorie_percentage, protein_percentage, carbs_percentage, fat_percentage;

    Vector<Food> foods;
    Vector<ExpansionState> expansionStates;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FoodDataAdapter(Vector<Food> foods, Vector<ExpansionState> expansionStates) {
        this.foods = foods;
        this.expansionStates = expansionStates;
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
        if(expansionStates == null) {Log.d(TAG, "expansionStates is null");}
        boolean isExpanded = expansionStates.get(position).getIsExpanded();
        switch(position) {
            case MACRONUTRIENT_TARGETS:
                MacronutrientHolder macHolder = (MacronutrientHolder) holder;
                initializeMacronutrientHolder(macHolder);
                if(isExpanded) {
                    macHolder.expandableLayout.setVisibility(View.VISIBLE);
                    macHolder.arrowDown.setVisibility(View.INVISIBLE);
                    macHolder.arrowUp.setVisibility(View.VISIBLE);
                } else {
                    macHolder.expandableLayout.setVisibility(View.GONE);
                    macHolder.arrowDown.setVisibility(View.VISIBLE);
                    macHolder.arrowUp.setVisibility(View.INVISIBLE);
                }
                break;
            case MINVIT_TARGETS:
                MinVitHolder micHolder = (MinVitHolder) holder;
                initializeMinVitHolder(micHolder);
                if(isExpanded) {
                    micHolder.expandableLayout.setVisibility(View.VISIBLE);
                    micHolder.arrowDown.setVisibility(View.INVISIBLE);
                    micHolder.arrowUp.setVisibility(View.VISIBLE);
                } else {
                    micHolder.expandableLayout.setVisibility(View.GONE);
                    micHolder.arrowDown.setVisibility(View.VISIBLE);
                    micHolder.arrowUp.setVisibility(View.INVISIBLE);
                }
                break;
            case CALORIES_CONSUMED_TARGETS:
                initializeCaloriesConsumedHOlder((CaloriesConsumedHolder) holder);
                ((CaloriesConsumedHolder) holder).expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                break;
            case CALORIES_BURNED:
                initializeCaloriesBurnedHolder((CaloriesBurnedHolder) holder);
                ((CaloriesBurnedHolder) holder).expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                break;
            case CALORIES_REMAINING:
                 initializeCaloriesRemainingHolder((CaloriesRemainingHolder) holder);
                ((CaloriesRemainingHolder) holder).expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                 break;
            case NUTRITION_SCORES:
                initializeNutritionScoresHolder((NutritionScoresHolder) holder);
                ((NutritionScoresHolder) holder).expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                break;
            case COMPLETE_NUTRITION:
                initializeNutritionTotalHolder((NutritionTotalHolder) holder);
                ((NutritionTotalHolder) holder).expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class MacronutrientHolder extends RecyclerView.ViewHolder {
        ImageView arrowUp, arrowDown;
        ProgressBar calories_pb, protein_pb, carbs_pb, fat_pb;
        TextView calories_percentageTextView, protein_percentageTextView, carbs_percentageTextView, fat_percentageTextView,
                caloriesTextView, proteinTextView, carbsTextView, fatTextView;
        LinearLayout expandableLayout;
        RelativeLayout titleLayout;

        public MacronutrientHolder(@NonNull View itemView) {
            super(itemView);

            arrowUp = itemView.findViewById(R.id.arrowUp);
            arrowDown = itemView.findViewById(R.id.arrowDown);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            titleLayout = itemView.findViewById(R.id.title);

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

            titleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ExpansionState expansionState = expansionStates.get(position);
                    boolean arrowUp = expansionState.getIsExpanded();
                    expansionState.setIsExpanded(!arrowUp);
                    notifyItemChanged(getAdapterPosition());
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

    class MinVitHolder extends RecyclerView.ViewHolder {
        ImageView arrowUp, arrowDown;
        ProgressBar calories_pb, protein_pb, carbs_pb, fat_pb;
        TextView calories_percentageTextView, protein_percentageTextView, carbs_percentageTextView, fat_percentageTextView,
                caloriesTextView, proteinTextView, carbsTextView, fatTextView;
        LinearLayout expandableLayout;
        RelativeLayout titleLayout;

        public MinVitHolder(@NonNull View itemView) {
            super(itemView);

            arrowUp = itemView.findViewById(R.id.arrowUp);
            arrowDown = itemView.findViewById(R.id.arrowDown);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            titleLayout = itemView.findViewById(R.id.title);

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

            titleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ExpansionState expansionState = expansionStates.get(position);
                    boolean arrowUp = expansionState.getIsExpanded();
                    expansionState.setIsExpanded(!arrowUp);
                    notifyItemChanged(getAdapterPosition());
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
        LinearLayout expandableLayout;

        public CaloriesConsumedHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
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
        LinearLayout expandableLayout;

        public CaloriesBurnedHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
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
        LinearLayout expandableLayout;

        public CaloriesRemainingHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
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
        LinearLayout expandableLayout;

        public NutritionScoresHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
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
        LinearLayout expandableLayout;

        public NutritionTotalHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
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

