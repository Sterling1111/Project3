package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Starting point for the application. Checks whether the user is signed in. If they are then
 * they are directed to the dashboard class which is the base activity of the entire application. If they are
 * not signed in then they are directed to the Login activity.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
        } else {
            Log.d(TAG, user.getEmail());
            startActivity(new Intent(MainActivity.this, Dashboard.class));
        }
        finish();
    }
}