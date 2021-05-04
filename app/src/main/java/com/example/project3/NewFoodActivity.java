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
 * Adds Food objects to FireStore that can be called from Search_Fragment, which will pass in a Food
 * object and populate the TextViews or from the quick_add toolbar button with empty TextViews
 */
public class NewFoodActivity extends AppCompatActivity {

    /**
     * gets the current logged in user
     */
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * holds the name of the Food
     */
    private TextView nameField;

    /**
     * holds the number of Calories
     */
    private TextView calField;

    /**
     * holds the number of grams of carbohydrates per serving
     */
    private TextView carbField;

    /**
     * holds the number of grams of protein per serving
     */
    private TextView proteinField;

    /**
     * holds the number of grams of fat per serving
     */
    private TextView fatField;

    /**
     * holds the number of servings
     */
    private TextView servingsField;

    /**
     * can hold a Food object passed in from Search_Fragment and created in createFood
     */
    Food food = null;

    /**
     * used to write messages to Log
     */
    private final String TAG = NewFoodActivity.class.getSimpleName();

    /**
     * creates the NewFoodActivity View when the activity is started and initializes the layout
     * @param savedInstanceState is used by Search_Fragment to pass in a Food object
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

        Button addButton = findViewById(R.id.quick_add_food_button);

        //check to see if a Food was placed in the Intent's Extras, if so initalize its values to
        // the TextViews
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
     * creates a new Food object from the TextViews and then adds to Firestore for the current
     * selected date
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


