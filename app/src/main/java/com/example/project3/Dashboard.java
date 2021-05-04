package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.example.project3.model.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Vector;

/**
 *
 */
public class Dashboard extends AppCompatActivity {

    /**
     *
     */
    static Date currentDate;

    /**
     *
     */
    FirebaseDatabase rootNode;

    /**
     *
     */
    DatabaseReference reference;

    /**
     *
     */
    FirebaseUser user;

    /**
     *
     * @param savedInstanceState
     */
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

        //initialize currentDate to today's date
        Date tempDate = new Date();
        currentDate = new Date(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate());

        Home_Fragment home_fragment = new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_fragment).commit();
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dots, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                startSignIn();
                break;
            case R.id.quick_add_menu_button:
                startActivity(new Intent(this, NewFoodActivity.class));
                break;
        }
        return true;
    }

    /**
     *
     */
    private void startSignIn() {
        Intent intent = new Intent(Dashboard.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     *
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        switch(item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new Home_Fragment();
                toolbar.setTitle("Home");
                break;
            case R.id.nav_diary:
                selectedFragment = new Diary_Fragment();
                toolbar.setTitle("Diary");
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
    };
}