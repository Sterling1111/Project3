/**
 * Food.java is the main entity that we will use to retrieve the data for the food that is added.
 */

package com.example.project3.model;

import android.os.Parcelable;

import java.io.Serializable;


public class Food implements Serializable {
    private String foodName;
    private Float servings;
    private Float caloriesPerServing;
    private Float totalFat;
    private Float satFat;
    private Float transFat;
    private Float polyFat;
    private Float monoFat;
    private Float chol;
    private Float sodium;
    private Float totalCarb;
    private Float fiber;
    private Float sugar;
    private Float protein;
    private Float vitD;
    private Float calcium;
    private Float iron;
    private Float potassium;
    private Float vitA;
    private Float vitC;

    public Food() {}

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
    public Food(Food food) {
        this(food.foodName, food.caloriesPerServing, food.servings, food.protein, food.totalCarb, food.totalFat);
    }

    public Food(String foodName, Float caloriesPerServing, Float servings, Float protein, Float carbs, Float fat) {
        this.foodName = foodName;
        this.caloriesPerServing = caloriesPerServing;
        this.servings = servings;
        this.protein = protein;
        this.totalCarb = carbs;
        this.totalFat = fat;
    }

    public Food(String foodName, Float caloriesPerServing, Float totalFat, Float satFat, Float transFat, Float polyFat,
                Float monoFat, Float chol, Float sodium, Float totalCarb, Float fiber, Float sugar, Float protein,
                Float vitD, Float calcium, Float iron, Float potassium, Float vitA, Float vitC) {
        this.foodName = foodName;
        this.caloriesPerServing = caloriesPerServing;
        this.totalFat = totalFat;
        this.satFat = satFat;
        this.transFat = transFat;
        this.polyFat = polyFat;
        this.monoFat = monoFat;
        this.chol = chol;
        this.sodium = sodium;
        this.totalCarb = totalCarb;
        this.fiber = fiber;
        this.sugar = sugar;
        this.protein = protein;
        this.vitD = vitD;
        this.calcium = calcium;
        this.iron = iron;
        this.potassium = potassium;
        this.vitA = vitA;
        this.vitC = vitC;
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

<<<<<<< HEAD
=======
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
>>>>>>> origin/master
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

//**************************************************************************BELOW ARE MICRONUTRIENTS THAT WE ARE GOING TO REMOVE***********************************************************************************
    public Float getSatFat() {
        return satFat;
    }

    public void setSatFat(Float satFat) {
        this.satFat = satFat;
    }

    public Float getTransFat() {
        return transFat;
    }

    public void setTransFat(Float transFat) {
        this.transFat = transFat;
    }

    public Float getPolyFat() {
        return polyFat;
    }

    public void setPolyFat(Float polyFat) {
        this.polyFat = polyFat;
    }

    public Float getMonoFat() {
        return monoFat;
    }

    public void setMonoFat(Float monoFat) {
        this.monoFat = monoFat;
    }

    public Float getChol() {
        return chol;
    }

    public void setChol(Float chol) {
        this.chol = chol;
    }

    public Float getSodium() {
        return sodium;
    }

    public void setSodium(Float sodium) {
        this.sodium = sodium;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
    }

    public Float getSugar() {
        return sugar;
    }

    public void setSugar(Float sugar) {
        this.sugar = sugar;
    }


    public Float getVitD() {
        return vitD;
    }

    public void setVitD(Float vitD) {
        this.vitD = vitD;
    }

    public Float getCalcium() {
        return calcium;
    }

    public void setCalcium(Float calcium) {
        this.calcium = calcium;
    }

    public Float getIron() {
        return iron;
    }

    public void setIron(Float iron) {
        this.iron = iron;
    }

    public Float getPotassium() {
        return potassium;
    }

    public void setPotassium(Float potassium) {
        this.potassium = potassium;
    }

    public Float getVitA() {
        return vitA;
    }

    public void setVitA(Float vitA) {
        this.vitA = vitA;
    }

    public Float getVitC() {
        return vitC;
    }

    public void setVitC(Float vitC) {
        this.vitC = vitC;
    }
}
