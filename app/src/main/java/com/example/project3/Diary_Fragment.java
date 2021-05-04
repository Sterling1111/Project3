
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

public class Diary_Fragment extends Fragment {

    private static final String TAG = "Diary_Fragment";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference foodRef;
    private FoodAdapter adapter;

    RecyclerView recyclerView;

    private DatePickerDialog datePickerDialog;
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
     *
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


    private void initDatePicker() {
        //The listener used to indicate the user has finished selecting a date
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

                Dashboard.currentDate = new Date(year - 1900, month, dayOfMonth);
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);

                foodRef = firestore.collection("Users").document(user.getUid())
                        .collection("Dates").document(Dashboard.currentDate.toString())
                        .collection("Foods");
                FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                        .setQuery(foodRef, Food.class).build();
                adapter.updateOptions(options);
            }
        };


        int year = Dashboard.currentDate.getYear();
        int month = Dashboard.currentDate.getMonth();
        int day = Dashboard.currentDate.getDate();

        dateButton.setText(makeDateString(day, month, year + 1900));

        datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener, year + 1900, month, day);


    }
}
