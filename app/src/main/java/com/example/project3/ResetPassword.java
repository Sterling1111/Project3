package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity which allows the user to reset their password.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class ResetPassword extends AppCompatActivity {

    private FirebaseAuth auth;
    /** To enter the users email */
    TextInputLayout email;
    /** To submit request for email reset at password location */
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();

        submit = findViewById(R.id.emailSend);
        email = findViewById(R.id.email);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.sendPasswordResetEmail(email.getEditText().getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResetPassword.this, Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }


}