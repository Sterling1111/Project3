/**
 *Diary_Fragment.java is the main entity that we will be using for our date picker and for the diary.
 */

package com.example.project3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.project3.util.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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

    private Vector<Food> foods;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.diary_fragment, container,false);
        /*recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));*/

        dateButton = v.findViewById(R.id.datePickerButton);

        initDatePicker();

        foodRef = firestore.collection("Users").document(user.getUid()).collection("Dates").document(Dashboard.currentDate.toString()).collection("Foods");

        initRecyclerView(v);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewFoodActivity.class));
            }
        });
        return v;
    }

    private void initRecyclerView(View v) {
        Query query = foodRef;
        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
        adapter = new FoodAdapter(options);
        recyclerView = v.findViewById(R.id.recycler_diary);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }


    public void setFoods(Vector<Food> foods) {
        this.foods = foods;
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
     *
     * @return date in Gregorian form
     */
    private Date getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new Date(year, month, day);
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

                //currentDate = new Date(year, month, dayOfMonth);
                Dashboard.currentDate = new Date(year - 1900, month, dayOfMonth);
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
                //foodRef = firestore.collection("Users").document(user.getUid()).collection("Dates").document(currentDate.toString()).collection("Foods");
                foodRef = firestore.collection("Users").document(user.getUid()).collection("Dates").document(Dashboard.currentDate.toString()).collection("Foods");
                FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>().setQuery(foodRef, Food.class).build();
                adapter.updateOptions(options);
            }
        };


        int year = Dashboard.currentDate.getYear();
        int month = Dashboard.currentDate.getMonth();
        int day = Dashboard.currentDate.getDate();

        dateButton.setText(makeDateString(day, month, year + 1900));

        int style = AlertDialog.THEME_HOLO_DARK;


        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year + 1900, month, day);

    }
}
