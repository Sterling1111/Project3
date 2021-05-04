/**
 *Diary_Fragment.java is the main entity that we will be using for our date picker and for the diary.
 */

package com.example.project3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.adapter.FoodAdapter;
import com.example.project3.model.Food;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Vector;

public class Diary_Fragment extends Fragment implements
        View.OnClickListener,
        FoodAdapter.OnFoodSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FoodAdapter adapter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseUser user;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;


    private Vector<Food> foods;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary_fragment, container,false);
        recyclerView = v.findViewById(R.id.recycler_diary);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mQuery = mFirestore.collection(user.getUid() + "/" + java.time.LocalDate.now().toString() + "/foods");
        recyclerView.setAdapter(new FoodAdapter(foods));
        dateButton = v.findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
        initDatePicker();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        return v;
    }

    private void initRecyclerView() {
        adapter = new FoodAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    //mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // who cares
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }


    public void setFoods(Vector<Food> foods) {
        this.foods = foods;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFoodSelected(DocumentSnapshot food) {

    }

    /**
     *
     * @return date in Gregorian form
     */
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
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


    private void initDatePicker() {
        //The listener used to indicate the user has finished selecting a date
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                //january is month 0 so i did month+1 to make january 1
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };

        //Calendar.getInstance() gets the current day that gets saved as cal
        Calendar cal = Calendar.getInstance();
        //cal.get(Calendar.YEAR) gets present year
        int year = cal.get(Calendar.YEAR);
        //cal.get(Calendar.MONTH) gets present month
        int month = cal.get(Calendar.MONTH);
        //cal.get(Calendar.DAY_OF_MONTH) gets present day of month
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);

        //this line of of code is so that the user can't select a date in the future. It is commented out for now.
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
}
