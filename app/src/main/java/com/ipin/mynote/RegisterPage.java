package com.ipin.mynote;

// 10120164 M HASBI FADILAH, IF4

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class RegisterPage extends AppCompatActivity {

    private Button btnToLogin, btnRegis;
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);

        btnToLogin = findViewById(R.id.btnToLogin);
        btnRegis = findViewById(R.id.btnRegis);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                email = String.valueOf(inputEmail.getText());
                password = String.valueOf(inputPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this, "Email cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterPage.this, "Password cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully registered! you can login now UwU",
                                            Toast.LENGTH_SHORT).show();

                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                                            startActivity(intent);
                                        }
                                    }, 1000L);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration failed. Please try again later",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}

