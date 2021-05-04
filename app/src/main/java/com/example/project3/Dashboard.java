package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * An activity which is a base for the entire application
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class Dashboard extends AppCompatActivity {

    /** a date which represents that current date */
    static Date currentDate;
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

        //initialize currentDate to today's date
        Date tempDate = new Date();
        currentDate = new Date(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate());

        Home_Fragment home_fragment = new Home_Fragment();
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
     * If the user signs out then they will be taken to the sign in page which is a new Activity
     * that will be called by this method. The flags passed mean that once this function is called you
     * cannot return to the activity except by loging in again.
     */
    private void startSignIn() {
        Intent intent = new Intent(Dashboard.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * A listener which listens for the user to select an icon in the Bottom Navigation view.
     * When they do they will be directed to the Fragment which corresponds the the icon which
     * they selected.
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