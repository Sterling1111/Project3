package com.example.project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project3.model.Food;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewFoodActivity extends AppCompatActivity {

    private TextView nameField;
    private TextView calField;
    private TextView carbField;
    private TextView proteinField;
    private TextView fatField;
    private TextView servingsField;
    private Button addButton;
    private final String TAG = Food_Fragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        nameField = findViewById(R.id.food_name_field);
        calField = findViewById(R.id.calorie_field);
        carbField = findViewById(R.id.carbs_field);
        proteinField = findViewById(R.id.protein_field);
        fatField = findViewById(R.id.fats_field);
        servingsField = findViewById(R.id.servings_field);
        addButton = findViewById(R.id.quick_add_food_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createFood();
            }
        });

    }



    public void createFood() {
        Food food = new Food();
        food.setFoodName(nameField.getText().toString());
        food.setCalories(Float.parseFloat(calField.getText().toString()));
        food.setTotalCarb(Float.parseFloat(carbField.getText().toString()));
        food.setProtein(Float.parseFloat(proteinField.getText().toString()));
        food.setTotalFat(Float.parseFloat(fatField.getText().toString()));
        food.setServings(Float.parseFloat(carbField.getText().toString()));
        food.setCaloriesPerServing(food.getCalories() / food.getServings());

        CollectionReference foodsRef = FirebaseFirestore.getInstance().collection("Foods");
        foodsRef.add(new Food(food));
        finish();
    }
}


