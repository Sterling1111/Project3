package com.example.project3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
        final ListView list = findViewById(R.id.FoodList);
        //food that is hardcoded for testing adapter
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("PIZZA");
        arrayList.add("APPLE");
        arrayList.add("CHOCOLATE");
        arrayList.add("BANANA");
        arrayList.add("YOGURT");
        arrayList.add("EGGS");
        arrayList.add("TOAST");
        arrayList.add("BAGEL");
        arrayList.add("SALMON");
        arrayList.add("CHICKEN");
        arrayList.add("STEAK");
        arrayList.add("POTATO");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                   android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Dashboard.this,clickedItem,Toast.LENGTH_LONG).show();
                //logic for the daily summary
            }
        });
    }
}