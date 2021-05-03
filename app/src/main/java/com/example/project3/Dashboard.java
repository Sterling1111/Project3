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

import com.example.project3.adapter.ExpansionState;
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
import java.util.Vector;

public class Dashboard extends AppCompatActivity {

    private final Vector<Food> foods = new Vector<>();
    private final Vector<ExpansionState> expansionStates = new Vector<>();

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        for(int i = 0; i < 7; i++) {expansionStates.add(new ExpansionState());}


        Food food1 = new Food("Appl1", 50, 1, 0, 15, 1);
        Food food2 = new Food("Apple2", 50, 1, 0, 15, 1);
        Food food3 = new Food("Apple3", 50, 1, 0, 15, 1);

        foods.add(food1);
        foods.add(food2);
        foods.add(food3);

        reference.child(user.getUid()).child(java.time.LocalDate.now().toString()).child("foods").setValue(food1);


        Home_Fragment home_fragment = new Home_Fragment();
        home_fragment.setFoods(foods);
        home_fragment.setExpansionStates(expansionStates);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_fragment).commit();
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
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home_Fragment();
                            toolbar.setTitle("Home");
                            ((Home_Fragment) selectedFragment).setFoods(foods);
                            ((Home_Fragment) selectedFragment).setExpansionStates(expansionStates);
                            break;
                        case R.id.nav_diary:
                            selectedFragment = new Diary_Fragment();
                            toolbar.setTitle("Diary");
                            ((Diary_Fragment) selectedFragment).setFoods(foods);
                            break;
                        case R.id.nav_me:
                            selectedFragment = new Me_Fragment();
                            toolbar.setTitle("Me");
                            break;
                        case R.id.nav_search:
                            selectedFragment = new Search_Fragment();
                            toolbar.setTitle("Search");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    public void launchFoodItemFragment() {

    }
}