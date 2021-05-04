package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project3.model.Food;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 *
 */
public class NewFoodActivity extends AppCompatActivity {

    /**
     *
     */
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     *
     */
    private TextView nameField;

    /**
     *
     */
    private TextView calField;

    /**
     *
     */
    private TextView carbField;

    /**
     *
     */
    private TextView proteinField;

    /**
     *
     */
    private TextView fatField;

    /**
     *
     */
    private TextView servingsField;

    /**
     *
     */
    private Button addButton;

    /**
     *
     */
    Food food = null;

    /**
     *
     */
    private final String TAG = NewFoodActivity.class.getSimpleName();

    /**
     *
     * @param savedInstanceState
     */
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

        food = (Food) getIntent().getSerializableExtra("food");
        if (food != null) {
            food = (Food) getIntent().getSerializableExtra("food");
            nameField.setText(food.getFoodName());
            calField.setText(food.getCaloriesPerServing().toString());
            carbField.setText(food.getTotalCarb().toString());
            proteinField.setText(food.getProtein().toString());
            fatField.setText(food.getTotalFat().toString());
        } else
            Log.e(TAG, "savedInstanceState is null");

        addButton.setOnClickListener(view -> createFood());

    }

    /**
     *
     */
    public void createFood() {
        Float calories = Float.parseFloat(calField.getText().toString());
        food = new Food();
        food.setFoodName(nameField.getText().toString());
        food.setTotalCarb(Float.parseFloat(carbField.getText().toString()));
        food.setProtein(Float.parseFloat(proteinField.getText().toString()));
        food.setTotalFat(Float.parseFloat(fatField.getText().toString()));
        food.setServings(Float.parseFloat(servingsField.getText().toString()));
        food.setCaloriesPerServing(calories / food.getServings());

        Task<DocumentReference> foodsRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("Dates").
                document(Dashboard.currentDate.toString()).collection("Foods").add(new Food(food));
        finish();
    }
}


