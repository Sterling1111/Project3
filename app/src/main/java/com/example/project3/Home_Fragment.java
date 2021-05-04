package com.example.project3;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project3.model.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

/**
 * Fragment which allows the user to view information regarding foods consumed for a particular day.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class Home_Fragment extends Fragment {

    private static String TAG = "Home_Fragment";
    /** progress bar for calories */
    ProgressBar calories_pb;
    /** progress bar for protein */
    ProgressBar protein_pb;
    /** progress bar for carbs */
    ProgressBar carbs_pb;
    /** progress bar for fat */
    ProgressBar fat_pb;
    /** percentage for calories */
    TextView calories_percentageTextView;
    /** percentage for protein */
    TextView protein_percentageTextView;
    /** percentage for carbs */
    TextView carbs_percentageTextView;
    /** percentage for fat */
    TextView fat_percentageTextView;
    /** text for calories */
    TextView caloriesTextView;
    /** text for protein */
    TextView proteinTextView;
    /** text for carbs */
    TextView carbsTextView;
    /** text for fat */
    TextView fatTextView;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference foodRef;

    /** A diologue which allows selection of date */
    private DatePickerDialog datePickerDialog;
    /** Button which opens DatePickerDialog */
    private Button dateButton;

    private DocumentReference foodsRef;
    /** to store the foods read from firebase so they can be worked with */
    private Vector<Food> foods;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);

        dateButton = v.findViewById(R.id.datePickerButton);
        dateButton.setText(Dashboard.currentDate.toString());

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

        initDatePicker();

        foods = new Vector<Food>();

        initProgressBars(Dashboard.currentDate);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        return v;
    }

    /**
     * A function to set the progress bars according to the current date
     * @param date the date at which data is to be pulled from the database
     */
    private void initProgressBars(Date date) {
        firestore.collection("Users").document(user.getUid())
                .collection("Dates").document(date.toString())
                .collection("Foods")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Food food = document.toObject(Food.class);
                                foods.add(food);
                            }
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
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * A function to set the onDateSet event handler and to initialize it to the current date.
     */
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                //currentDate = new Date(year, month, dayOfMonth);
                Dashboard.currentDate = new Date(year - 1900, month, dayOfMonth);
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(Dashboard.currentDate.toString());
                foods.clear();
                initProgressBars(Dashboard.currentDate);
            }
        };

        int year = Dashboard.currentDate.getYear();
        int month = Dashboard.currentDate.getMonth();
        int day = Dashboard.currentDate.getDate();

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year + 1900, month, day);
    }

    /**
     * a function to get the date as a string
     * @param day an int that represents a dat
     * @param month an int that represents a month
     * @param year an int that represents a year
     * @return a String that represents a date
     */
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    /**
     * a function to return the month as a string
     * @param month an int that represents the month
     * @return a String that represents the month
     */
    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default
        return "JAN";
    }

}
