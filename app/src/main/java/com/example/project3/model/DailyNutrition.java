package com.example.project3.model;

import java.util.Vector;

public class DailyNutrition {
    private final Vector<Food> foods = new Vector<>();

    private float getTotalCarbs() {
        float totalCarb = 0;
        for (Food food : foods) {
            totalCarb += food.getTotalCarb();
        }
        return totalCarb;
    }

    private float getTotalFats() {
        float totalFat = 0;
        for (Food food : foods) {
            totalFat += food.getTotalFat();
        }
        return totalFat;
    }

    private float getTotalProtein() {
        float totalProtein = 0;
        for (Food food : foods) {
            totalProtein += food.getProtein();
        }
        return totalProtein;
    }

    private float getTotalCalories() {
        float totalCalories = 0;
        for (Food food : foods) {
           // totalCalories += food.getCalories();
        }
        return totalCalories;
    }
}
