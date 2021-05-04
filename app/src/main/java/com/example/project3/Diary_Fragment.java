
package com.example.project3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.adapter.FoodAdapter;
import com.example.project3.model.Food;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

/**
 * An activity which displays the food items that a user has consumed for a given date.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class Diary_Fragment extends Fragment {


    private static final String TAG = "Diary_Fragment";


    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference foodRef;

    /** An object which creates the correct layout for the individual recycler view items */
    private FoodAdapter adapter;
    private RecyclerView recyclerView;
    private DatePickerDialog datePickerDialog;

    /** a button which opens a date picker diologue and also displays the current date */
    private Button dateButton;
    private View v;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.diary_fragment, container,false);
        dateButton = v.findViewById(R.id.datePickerButton);
        foodRef = firestore.collection("Users").document(user.getUid())
                .collection("Dates").document(Dashboard.currentDate.toString())
                .collection("Foods");

        initDatePicker();
        initRecyclerView(v);

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        return v;
    }

    /**
     * A method which initializes the recycler with the results a document reference
     * @param v is a view which is needed in order to find the recycler_diary recyclerview
     */
    private void initRecyclerView(View v) {
        Query query = foodRef;
        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>().setQuery(query,
                Food.class).build();
        adapter = new FoodAdapter(options);
        recyclerView = v.findViewById(R.id.recycler_diary);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }

    /**
     *This method takes a day, month, and year and returns it in dd/MM/YYYY form
     *
     * @param day is an int representing the date of the month the user has selected
     * @param month is an int that represents the month of the year the user has selected
     * @param year an int that represents what year the user has selected
     * @return Dates in Gregorian form
     */
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    /**
     *Takes an int which represents a month and returns a string which represents a month
     * @param month integer representation of the month the user has selected
     * @return returns String that corresponding to the integer selected
     */
    private String getMonthFormat(int month) {
        if (month == 0)
            return "JAN";
        if (month == 1)
            return "FEB";
        if (month == 2)
            return "MAR";
        if (month == 3)
            return "APR";
        if (month == 4)
            return "MAY";
        if (month == 5)
            return "JUN";
        if (month == 6)
            return "JUL";
        if (month == 7)
            return "AUG";
        if (month == 8)
            return "SEP";
        if (month == 9)
            return "OCT";
        if (month == 10)
            return "NOV";
        if (month == 11)
            return "DEC";

        //default
        return "ERROR";
    }

    /**
     *initializes date picker by setting a listener that listens for a change in the date. If the date
     * is changed then a new reference to documents is made based on the date that was just selected. Then a new query
     * is created and the adapter for the recycler view is updated.
     */
    private void initDatePicker() {
        //The listener used to indicate the user has finished selecting a date
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {

            Dashboard.currentDate = new Date(year - 1900, month, dayOfMonth);
            String date = makeDateString(dayOfMonth, month, year);
            dateButton.setText(date);

            foodRef = firestore.collection("Users").document(user.getUid())
                    .collection("Dates").document(Dashboard.currentDate.toString())
                    .collection("Foods");
            FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                    .setQuery(foodRef, Food.class).build();
            adapter.updateOptions(options);
        };


        int year = Dashboard.currentDate.getYear();
        int month = Dashboard.currentDate.getMonth();
        int day = Dashboard.currentDate.getDate();

        dateButton.setText(makeDateString(day, month, year + 1900));

        datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener, year + 1900, month, day);

    }
}
