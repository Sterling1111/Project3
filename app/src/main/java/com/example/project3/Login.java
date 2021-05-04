package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Activity which allows the user to Login if they already have an account or directs them to
 * an activity which allows them to register with an account.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 5/4/21
 * */
public class Login extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mSignInClient;
    private FirebaseAuth mAuth;

    /** launches the SignUp activity */
    Button callSignUp;
    /** launches the Dashboard activity if success else displays exception*/
    Button loginBtn;
    /** launches the ResetPassword activity */
    Button forgotPassword;
    /** Signs in user with Google and launches Dashboard activity */
    SignInButton gSignUpBtn;
    /** launches the SignUp activity */
    TextView googleSignUptxt;
    /** To enter the users email */
    TextInputLayout email;
    /** To enter the users password */
    TextInputLayout password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        callSignUp = findViewById(R.id.signUpButton);
        loginBtn = findViewById(R.id.loginButton);
        gSignUpBtn = findViewById(R.id.sign_in_button);
        googleSignUptxt = (TextView) gSignUpBtn.getChildAt(0);
        googleSignUptxt.setText(R.string.sign_up_with_google);
        googleSignUptxt.setTextSize(15);
        googleSignUptxt.setTextColor(getResources().getColor(R.color.black));
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgot_password_btn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(this, gso);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        gSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()) {
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if(!mAuth.getCurrentUser().isEmailVerified()) {
                                            Toast.makeText(getApplicationContext(), "email not verified", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        Intent intent = new Intent(Login.this, Dashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    /**
     * Authorises the user with Google sign in. If succesful then Dashboard activity is launched else
     * an error message is displayed.
     * @param idToken is an idToken that you can send to the server
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    /**
     * Verifies whether the password field is not empty.
     * @return a boolean to denote if the entered password is empty.
     */
    public Boolean validatePassword() {
        if(TextUtils.isEmpty(password.getEditText().getText().toString())) {
            password.setError("Please enter password");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Verifies whether the email field is not empty.
     * @return a boolean to denote if the entered email is empty.
     */
    public Boolean validateEmail() {
        if(TextUtils.isEmpty(email.getEditText().getText().toString())) {
            email.setError("Please enter email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * a function which signs in the user
     */
    public void signIn() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}