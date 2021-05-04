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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
import android.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.project3.Dashboard;
import com.example.project3.FoodItemFragment;
import com.example.project3.R;
import com.example.project3.model.Food;
import com.example.project3.util.FoodUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.Vector;

public class FoodAdapter extends FirestoreRecyclerAdapter<Food, FoodAdapter.FoodHolder> {

    public FoodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.name.setText(model.getFoodName());
        holder.servings.setText(String.valueOf(model.getServings()));
        holder.calories.setText(String.valueOf(model.getCalories()));
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new FoodHolder(v);
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        TextView name, servings, calories;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_food);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
        }
    }
}