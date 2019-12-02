package com.jasjotsingh.saferide_yourright;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzcz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText email;
    EditText password;
    Button createAccount;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        createAccount = findViewById(R.id.createAccount);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Email = email.getText().toString();
                final String Password = password.getText().toString();

                if (TextUtils.isEmpty(Email) && (!TextUtils.isEmpty(Password))) {
                    Toast loginToast = Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_LONG);
                    loginToast.show();
                }
                if (TextUtils.isEmpty(Password) && (!TextUtils.isEmpty(Email))) {
                    Toast loginToast2 = Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG);
                    loginToast2.show();
                }
                if (TextUtils.isEmpty(Password) && TextUtils.isEmpty(Email)) {
                    Toast loginToast3 = Toast.makeText(LoginActivity.this, "Enter Email and Password", Toast.LENGTH_LONG);
                    loginToast3.show();
                } else {
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                        Toast loginToast4 = Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG);
                                        loginToast4.show();
                                    } else {

                                        Toast loginToast = Toast.makeText(LoginActivity.this, "Technical Error,Try again later", Toast.LENGTH_LONG);
                                        loginToast.show();
                                    }

                                }
                            });
                }

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            DatabaseReference myRef = database.getReference("email");

            myRef.setValue(user.getEmail());
            Intent signupIntent = new Intent(LoginActivity.this,MainActivity.class);
            signupIntent.putExtra("email",user.getEmail());
            startActivity(signupIntent);

        } else {

        }
    }}

