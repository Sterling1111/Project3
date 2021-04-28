package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.project3.adapter.FoodAdapter;
import com.example.project3.model.Food;
import com.example.project3.util.FirebaseUtil;
import com.example.project3.util.FoodUtil;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Collections;
import java.util.List;

//public class Dashboard extends AppCompatActivity implements
//        View.OnClickListener,
//        FoodAdapter.OnFoodSelectedListener {
//
//    private static final int LIMIT = 50;
//    private static final String TAG = "Dashboard";
////
//    private FirebaseFirestore mFirestore;
//    private Query mQuery;
//    private FoodAdapter mAdapter;
//    private RecyclerView mFoodsRecycler;
//    private ViewGroup mEmptyView;
//    private Toolbar mToolbar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard_activity);

//        //mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//
//        //mFoodsRecycler = findViewById(R.id.recycler_foods);
//        //mEmptyView = findViewById(R.id.view_empty);
//
//        mFirestore = FirebaseUtil.getFirestore();
//        mQuery = mFirestore.collection("foods")
//                .orderBy("foodName", Query.Direction.DESCENDING)
//                .limit(LIMIT);
//        initRecyclerView();
    //}

//    private void initRecyclerView() {
//        if (mQuery == null) {
//            Log.w(TAG, "No query, not initializing RecyclerView");
//        }
//
//        mAdapter = new FoodAdapter(mQuery, this) {
////
//            @Override
//            protected void onDataChanged() {
//                // Show/hide content if the query returns empty.
//                if (getItemCount() == 0) {
//                    mFoodsRecycler.setVisibility(View.GONE);
//                    mEmptyView.setVisibility(View.VISIBLE);
//                } else {
//                    mFoodsRecycler.setVisibility(View.VISIBLE);
//                    mEmptyView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            protected void onError(FirebaseFirestoreException e) {
//                // Show a snackbar on errors
//                Snackbar.make(findViewById(android.R.id.content),
//                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
//            }
//        };
//
//        mFoodsRecycler.setLayoutManager(new LinearLayoutManager(this));
//        mFoodsRecycler.setAdapter(mAdapter);
//    }

//    private void onAddItemsClicked() {
//        // Get a reference to the restaurants collection
//        CollectionReference foods = mFirestore.collection("foods");
//
//
//        for (int i = 0; i < 10; i++) {
//            // Get a random Restaurant POJO
//            Food food = FoodUtil.getRandom(this);
//            // Add a new document to the restaurants collection
//            foods.add(food);
//        }
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.filter_bar:
//                onFilterClicked();
//                break;
//            case R.id.button_clear_filter:
//                onClearFilterClicked();
//        }
//    }
//
//    public void onFilterClicked() {
//        // Show the dialog containing filter options
//
//    }
//
//    public void onClearFilterClicked() {
//
//    }
//
//
//    @Override
//    public void onFoodSelected(DocumentSnapshot restaurant) {

//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // Start listening for Firestore updates
//        if (mAdapter != null) {
//            mAdapter.startListening();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.buttom_navigation, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.add_random_foods:
//                onAddItemsClicked();
//                break;
//            case R.id.sign_out:
//                FirebaseUtil.getAuth().signOut();
//                startSignIn();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void startSignIn() {
//        Intent intent = new Intent(Dashboard.this, Login.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
//}


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.sign_out:
                FirebaseUtil.getAuth().signOut();
                startSignIn();
                break;
            case R.id.add_random_foods:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.me:
                Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    private void startSignIn() {
        Intent intent = new Intent(Dashboard.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home_Fragment();
                            break;
                        case R.id.nav_diary:
                            selectedFragment = new Diary_Fragment();
                            break;
                        case R.id.nav_me:
                            selectedFragment = new Me_Fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

}