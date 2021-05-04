/*
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.project3.R;
import com.example.project3.model.Food;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * used to display the information stored in Firestore for a particular day that is automatically
 * updated when a document is added to the collection
 */
public class FoodAdapter extends FirestoreRecyclerAdapter<Food, FoodAdapter.FoodHolder> {

    /**
     * constructor for FoodAdapter
     * @param options sets the collection that the RecyclerView will display the contents of
     */
    public FoodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    /**
     * sets the fields of a food_list_item for each cell of the RecyclerView
     * @param holder a FoodHolder that will be initialized with its respective Food
     * @param position the position in the collection, depending on what order the Foods were added
     * @param model the Food object at that position
     */
    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.name.setText(model.getFoodName());
        holder.servings.setText(String.valueOf(model.getServings()));
        holder.calories.setText(String.valueOf(model.getCalories()));
    }

    /**
     * creates a FoodHolder when called
     * @param parent used to get the food_list_item layout and pass it to the FoodHolder constructor
     * @param viewType not used
     * @return an initialized FoodHolder
     */
    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new FoodHolder(v);
    }

    /**
     * inner class that initializes the fields of a food_list_item layout
     */
    class FoodHolder extends RecyclerView.ViewHolder {
        /**
         * displays the name of the Food object
         */
        TextView name;

        /**
         * displays the number of servings
         */
        TextView servings;

        /**
         * displays the number of calories the Food object has
         */
        TextView calories;

        /**
         * initializes the TextViews to the ones in food_list_item
         * @param itemView
         */
        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_food);
            servings = itemView.findViewById(R.id.servings);
            calories = itemView.findViewById(R.id.calories_number);
        }
    }
}