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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Float getCalories() { return caloriesPerServing * servings; }

    public Float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(Float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setCalories(Float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setServings(Float servings) {
        this.servings = servings;
    }

    public Float getServings() {
        return servings;
    }

    public Float getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Float totalFat) {
        this.totalFat = totalFat;
    }

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

    public Float getTotalCarb() {
        return totalCarb;
    }

    public void setTotalCarb(Float totalCarb) {
        this.totalCarb = totalCarb;
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

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
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
