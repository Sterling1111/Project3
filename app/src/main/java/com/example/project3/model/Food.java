package com.example.project3.model;

public class Food {
    private String foodName;
    private float servings;
    private float caloriesPerServing;
    private float totalFat;
    private float satFat;
    private float transFat;
    private float polyFat;
    private float monoFat;
    private float chol;
    private float sodium;
    private float totalCarb;
    private float fiber;
    private float sugar;
    private float protein;
    private float vitD;
    private float calcium;
    private float iron;
    private float potassium;
    private float vitA;
    private float vitC;

    public Food() {}

    public Food(String foodName, float caloriesPerServing, float servings, float protein, float carbs, float fat) {
        this.foodName = foodName;
        this.caloriesPerServing = caloriesPerServing;
        this.servings = servings;
        this.protein = protein;
        this.totalCarb = carbs;
        this.totalFat = fat;
    }

    public Food(String foodName, float caloriesPerServing, float totalFat, float satFat, float transFat, float polyFat,
                float monoFat, float chol, float sodium, float totalCarb, float fiber, float sugar, float protein,
                float vitD, float calcium, float iron, float potassium, float vitA, float vitC) {
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

    public float getCalories() { return caloriesPerServing * servings; }

    public float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setCalories(float caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setServings(float servings) {
        this.servings = servings;
    }

    public float getServings() {
        return servings;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }

    public float getSatFat() {
        return satFat;
    }

    public void setSatFat(float satFat) {
        this.satFat = satFat;
    }

    public float getTransFat() {
        return transFat;
    }

    public void setTransFat(float transFat) {
        this.transFat = transFat;
    }

    public float getPolyFat() {
        return polyFat;
    }

    public void setPolyFat(float polyFat) {
        this.polyFat = polyFat;
    }

    public float getMonoFat() {
        return monoFat;
    }

    public void setMonoFat(float monoFat) {
        this.monoFat = monoFat;
    }

    public float getChol() {
        return chol;
    }

    public void setChol(float chol) {
        this.chol = chol;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getTotalCarb() {
        return totalCarb;
    }

    public void setTotalCarb(float totalCarb) {
        this.totalCarb = totalCarb;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getVitD() {
        return vitD;
    }

    public void setVitD(float vitD) {
        this.vitD = vitD;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getVitA() {
        return vitA;
    }

    public void setVitA(float vitA) {
        this.vitA = vitA;
    }

    public float getVitC() {
        return vitC;
    }

    public void setVitC(float vitC) {
        this.vitC = vitC;
    }
}
