package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private FirebaseAuth auth;
    TextInputLayout email;
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