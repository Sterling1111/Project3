package com.example.project3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project3.model.Food;

public class Food_Fragment extends Fragment {
    Food food = null;

    private TextView nameField;
    private TextView calField;
    private TextView carbField;
    private TextView proteinField;
    private TextView fatField;
    private TextView servingsField;
    private Button addButton;
    private final String TAG = Food_Fragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.food_fragment, container, false);
        Bundle args = getArguments();
        if (args != null) {
            food = (Food) args.getSerializable("food");
        }

        nameField = v.findViewById(R.id.food_name_field);
        calField = v.findViewById(R.id.calorie_field);
        carbField = v.findViewById(R.id.carbs_field);
        proteinField = v.findViewById(R.id.protein_field);
        fatField = v.findViewById(R.id.fats_field);
        servingsField = v.findViewById(R.id.servings_field);
        addButton = v.findViewById(R.id.quick_add_food_button);
        setFields();
        return v;
    }

    public void setFields() {
        if (food == null) {
            Log.e(TAG, "food is null");
            return;
        }
        nameField.setText(food.getFoodName());
        calField.setText(food.getCaloriesPerServing().toString());
        carbField.setText(food.getTotalCarb().toString());
        proteinField.setText(food.getProtein().toString());
        fatField.setText(food.getTotalFat().toString());
    }
}
