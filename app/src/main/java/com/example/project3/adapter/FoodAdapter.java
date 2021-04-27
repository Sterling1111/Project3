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

import android.content.res.Resources;
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

/**
 * RecyclerView adapter for a list of Restaurants.
 */
public class FoodAdapter extends FirestoreAdapter<com.example.project3.adapter.FoodAdapter.ViewHolder> {

    public FoodAdapter(Query query) {
        super(query);
    }

    public interface OnFoodSelectedListener {

        void onFoodSelected(DocumentSnapshot food);

    }

    private OnFoodSelectedListener mListener;

    public FoodAdapter(Query query, OnFoodSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.food_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;
        TextView numServings;
        TextView calories;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name_textView);
            numServings = itemView.findViewById(R.id.num_servings_textView);
            calories = itemView.findViewById(R.id.calories_textView);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnFoodSelectedListener listener) {

            Food food = snapshot.toObject(Food.class);
            Resources resources = itemView.getResources();

            foodName.setText(food.getFoodName());
            numServings.setText(String.valueOf(food.getServings()));
            calories.setText(String.valueOf(food.getServings() * food.getCaloriesPerServing()));
            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onFoodSelected(snapshot);
                    }
                }
            });
        }

    }
}
