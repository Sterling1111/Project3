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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.project3.R;
import com.example.project3.model.Food;
import com.example.project3.util.FoodUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.Vector;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Vector<Food> foods;

    public FoodAdapter(Vector<Food> foods) {
        this.foods = foods;
    }

    private String[] foodNames = {
            "Trader Joe's, Honeycrisp Apples",
            "Bananas, Raw",
            "Pear, Home Canned",
            "Cherries, Sweet, Raw",
            "Plums, Dried",
            "Oranges, Raw",
            "Water Chestnuts, Raw",
            "Soy Milk, Plain or Original, Unsweetened, Not Fortified, Ready-to-Drink",
            "Dave's Killer Bread, 21 Whole Grains Bread"
    };

    private String[] servings = {
            "1 medium apple",
            "1 medium - 7\" to 7 7/8 long",
            "1 half - with liquid",
            "1 each - pitted",
            "1 each",
            "1 medium - 2 5/8\" diameter",
            "1 cup, sliced",
            "1 cup",
            "1 slice"
    };

    private float[] calories = {80.0f, 105.0f, 56.2f, 5.2f, 22.8f, 61.6f, 120.3f, 87.7f, 120.0f};


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food food = foods.get(position);
        if((position & 1) == 1) {
            holder.getItemView().setBackgroundColor(Color.rgb(238, 233, 233));
        } else {
            holder.getItemView().setBackgroundColor(Color.rgb(255, 255, 255));
        }
        holder.foodName.setText(food.getFoodName());
        holder.servings.setText(String.valueOf(food.getServings()) + " servings");
        holder.calories.setText(String.valueOf(food.getServings() * food.getCaloriesPerServing()));
        holder.calorie_number.setText("kcal");
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, servings, calories, calorie_number;
        Food food;

        public ViewHolder(@NonNull View itemView) {
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
}