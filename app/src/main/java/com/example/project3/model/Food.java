package com.example.project3.model;

public class Food {
    private String foodName;
    private double servings;
    private double caloriesPerServing;
    private double totalFat;
    private double satFat;
    private double transFat;
    private double polyFat;
    private double monoFat;
    private double chol;
    private double sodium;
    private double totalCarb;
    private double fiber;
    private double sugar;
    private double protein;
    private double vitD;
    private double calcium;
    private double iron;
    private double potassium;
    private double vitA;
    private double vitC;

    public Food() {}

    public Food(String foodName, double caloriesPerServing, double totalFat, double satFat, double transFat, double polyFat,
                double monoFat, double chol, double sodium, double totalCarb, double fiber, double sugar, double protein,
                double vitD, double calcium, double iron, double potassium, double vitA, double vitC) {
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

    public double getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(double caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setCalories(double caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public double getServings() {
        return servings;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getSatFat() {
        return satFat;
    }

    public void setSatFat(double satFat) {
        this.satFat = satFat;
    }

    public double getTransFat() {
        return transFat;
    }

    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    public double getPolyFat() {
        return polyFat;
    }

    public void setPolyFat(double polyFat) {
        this.polyFat = polyFat;
    }

    public double getMonoFat() {
        return monoFat;
    }

    public void setMonoFat(double monoFat) {
        this.monoFat = monoFat;
    }

    public double getChol() {
        return chol;
    }

    public void setChol(double chol) {
        this.chol = chol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getTotalCarb() {
        return totalCarb;
    }

    public void setTotalCarb(double totalCarb) {
        this.totalCarb = totalCarb;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getVitD() {
        return vitD;
    }

    public void setVitD(double vitD) {
        this.vitD = vitD;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getVitA() {
        return vitA;
    }

    public void setVitA(double vitA) {
        this.vitA = vitA;
    }

    public double getVitC() {
        return vitC;
    }

    public void setVitC(double vitC) {
        this.vitC = vitC;
    }
}
