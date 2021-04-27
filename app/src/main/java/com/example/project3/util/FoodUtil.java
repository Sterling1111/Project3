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
 package com.example.project3.util;

import android.content.Context;

import com.example.project3.model.Food;
import com.example.project3.R;
import com.example.project3.model.Food;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Utilities for Restaurants.
 */
public class FoodUtil {

    private static final String TAG = "FoodUtil";

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(2, 4, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    /**
     * Create a random Food POJO.
     */
    public static Food getRandom(Context context) {
        Food food = new Food();
        Random random = new Random();
        String[] foods = context.getResources().getStringArray(R.array.foods);
        foods = Arrays.copyOfRange(foods, 1, foods.length);
        double[] servings = new double[]{1, 2, 3};

        food.setFoodName(foods[random.nextInt(foods.length)]);
        food.setServings(getRandomDouble(servings, random));
        food.setCaloriesPerServing(50 + (50 * random.nextDouble()));

        return food;
    }

    private static double getRandomDouble(double[] array, Random random) {
        int ind = random.nextInt(array.length);
        return array[ind];
    }

}
