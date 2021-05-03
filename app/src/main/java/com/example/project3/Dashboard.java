package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import com.example.project3.adapter.ExpansionState;
import com.example.project3.adapter.FoodAdapter;

import com.example.project3.model.Food;
import com.example.project3.util.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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


        Food food1 = new Food("Appl1", 50f, 1f, 0f, 15f, 1f);
        Food food2 = new Food("Apple2", 50f, 1f, 0f, 15f, 1f);
        Food food3 = new Food("Apple3", 50f, 1f, 0f, 15f, 1f);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch(item.getItemId()){
            case R.id.quick_add_menu_button:
                toolbar.setTitle("Quick Add");
                Toast.makeText(this, "You clicked add", Toast.LENGTH_SHORT).show();
                switchContent(R.id.fragment_container, new Food_Fragment());
                break;

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

    public void switchContent(int id, Food_Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, frag, frag.toString());
        ft.commit();
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