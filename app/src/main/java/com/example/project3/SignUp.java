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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp.java";

    private FirebaseAuth mAuth;

    TextInputLayout regEmail, regPassword, passwordVerify;
    Button Halogin, Register;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        setContentView(R.layout.activity_sign_up);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        Register = findViewById(R.id.register);
        Halogin = findViewById(R.id.halogin);
        passwordVerify = findViewById(R.id.passwordVerify);
    }

    public void registerUser(View view) {

        //we cant let these short circuit so use | instead of ||
        if(!validatePassword() | !validateEmail() | !verifyPassword()) {
            return;
        }
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignUp.this, "verification email has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d(TAG, "onFailure: email not sent " + e.getMessage());
                                }
                            });

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUp.this, "user created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        if(val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(".{12,}")) {
            regPassword.setError("Password must be >= 12 chars");
            return false;
        } else if(!val.matches("\\S+")) {
            regPassword.setError("No whitespaces permitted");
            return false;
        } else if(!val.matches("(?=.*?[!@#$%^&*?].*?[!@#$%^&*?]).*")) {
            regPassword.setError("Must contain >= 2 special chars");
            return false;
        } else if(!val.matches("(?=.*[A-Z]).*")) {
            regPassword.setError("Must contain >= 1 capital char");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean verifyPassword() {
        if(!passwordVerify.getEditText().getText().toString().equals(regPassword.getEditText().getText().toString())) {
            passwordVerify.setError("Passwords must match");
            return false;
        } else {
            passwordVerify.setError(null);
            passwordVerify.setErrorEnabled(false);
            return true;
        }
    }
}