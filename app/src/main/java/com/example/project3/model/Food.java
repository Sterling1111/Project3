

package com.example.project3.model;

import java.io.Serializable;

/**
 * Food.java is the main entity that we will use to retrieve the data for the food that is added.
 */
public class Food implements Serializable {

    /**
     *
     */
    private String foodName;

    /**
     *
     */
    private Float servings;

    /**
     *
     */
    private Float caloriesPerServing;

    /**
     *
     */
    private Float totalFat;

    /**
     *
     */
    private Float totalCarb;

    /**
     *
     */
    private Float protein;

    /**
     *
     */
    public Food() {}

    /**
     *
     * @param food
     */
    public Food(Food food) {
        this(food.foodName, food.caloriesPerServing, food.servings, food.protein, food.totalCarb, food.totalFat);
    }

    /**
     * constructor
     *
     * @param foodName a string that represents the name of the food
     * @param caloriesPerServing a float that represents the calories per serving of the food
     * @param servings a float that represents the amount of servings the user inputs
     * @param protein a float to represent the grams of protein
     * @param carbs a float to represents grams of carbs
     * @param fat a float to represent the grams of fat
     */
    public Food(String foodName, Float caloriesPerServing, Float servings, Float protein, Float carbs, Float fat) {
        this.foodName = foodName;
        this.caloriesPerServing = caloriesPerServing;
        this.servings = servings;
        this.protein = protein;
        this.totalCarb = carbs;
        this.totalFat = fat;
    }

    /**
     *
     * @return returns name of the food in a string
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     *
     * @param foodName name of food in a string
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     *
     * @return total calories by multiplying the calories per serving by the serving size
     */
    public Float getCalories() { return caloriesPerServing * servings; }

    /**
     *
     * @return calories per serving as a float
     */
    public Float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    /**
     *
     * @param caloriesPerServing calories per serving as a float
     */
    public void setCaloriesPerServing(Float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    /**
     *
     * @param caloriesPerServing the float of grams in calories per serving
     */
    public void setCalories(Float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    /**
     *
     * @param servings serving size as a float
     */
    public void setServings(Float servings) {
        this.servings = servings;
    }

    /**
     *
     * @return the serving size as a float
     */
    public Float getServings() {
        return servings;
    }

    /**
     *
     * @return total grams of fat as a float
     */
    public Float getTotalFat() {
        return totalFat;
    }

    /**
     *
     * @param totalFat total amount of fat in grams in food
     */
    public void setTotalFat(Float totalFat) {
        this.totalFat = totalFat;
    }

    /**
     *
     * @return Total grams of protein as a float
     */
    public Float getProtein() {
        return protein;
    }

    /**
     *
     * @param protein total grams of protein as a float
     */
    public void setProtein(Float protein) {
        this.protein = protein;
    }

    /**
     *
     * @return total grams of carbs as a float
     */
    public Float getTotalCarb() {
        return totalCarb;
    }

    /**
     *
     * @param totalCarb total grams of carbs as a float
     */
    public void setTotalCarb(Float totalCarb) {
        this.totalCarb = totalCarb;
    }

}
