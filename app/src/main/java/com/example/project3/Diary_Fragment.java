package com.example.project3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.Vector;

public class Diary_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;


    private Vector<Food> foods;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diary_fragment, container,false);
        recyclerView = v.findViewById(R.id.recycler_diary);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(new FoodAdapter(foods));
        return v;
    }

    public void setFoods(Vector<Food> foods) {
        this.foods = foods;
    }


    //here is where DatePicker logic should go
}
