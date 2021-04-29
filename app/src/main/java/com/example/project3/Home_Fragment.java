package com.example.project3;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project3.model.Food;

import java.util.Vector;

public class Home_Fragment extends Fragment {
    ProgressBar calories_pb, protein_pb, carbs_pb, fat_pb;
    TextView calories_percentageTextView, protein_percentageTextView, carbs_percentageTextView, fat_percentageTextView, caloriesTextView, proteinTextView, carbsTextView, fatTextView;
    Vector<Food> foods;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container,false);

        calories_pb = v.findViewById(R.id.calories_pb);
        protein_pb = v.findViewById(R.id.protein_pb);
        carbs_pb = v.findViewById(R.id.carbs_pb);
        fat_pb = v.findViewById(R.id.fat_pb);

        caloriesTextView = v.findViewById(R.id.calories_number);
        proteinTextView = v.findViewById(R.id.protein_number);
        carbsTextView = v.findViewById(R.id.carbs_number);
        fatTextView = v.findViewById(R.id.fat_number);

        calories_percentageTextView = v.findViewById(R.id.calorie_percentage);
        protein_percentageTextView = v.findViewById(R.id.protein_percentage);
        carbs_percentageTextView = v.findViewById(R.id.carbs_percentage);
        fat_percentageTextView = v.findViewById(R.id.fat_percentage);

        int calories = (int) foods.stream().mapToDouble(Food::getCalories).sum();
        int protein = (int) foods.stream().mapToDouble(Food::getProtein).sum();
        int carbs = (int) foods.stream().mapToDouble(Food::getTotalCarb).sum();
        int fat = (int) foods.stream().mapToDouble(Food::getTotalFat).sum();

        int calorie_percentage = (calories * 100) / 2500;
        int protein_percentage = (protein * 100) / 50;
        int carbs_percentage = (carbs * 100) / 300;
        int fat_percentage = (fat * 100) / 50;

        calories_pb.setProgress(calorie_percentage);
        protein_pb.setProgress(protein_percentage);
        carbs_pb.setProgress(carbs_percentage);
        fat_pb.setProgress(fat_percentage);

        caloriesTextView.setText(String.valueOf(calories) + " / 2500");
        proteinTextView.setText(String.valueOf(protein) + " / 50");
        carbsTextView.setText(String.valueOf(carbs) + " / 300");
        fatTextView.setText(String.valueOf(fat) + " / 50");

        calories_percentageTextView.setText(String.valueOf(calorie_percentage) + " %");
        protein_percentageTextView.setText(String.valueOf(protein_percentage) + " %");
        carbs_percentageTextView.setText(String.valueOf(carbs_percentage) + " %");
        fat_percentageTextView.setText(String.valueOf(fat_percentage) + " %");

        return v;
    }

    public void setFoods(Vector<Food> foods) {
        this.foods = foods;
    }
}
