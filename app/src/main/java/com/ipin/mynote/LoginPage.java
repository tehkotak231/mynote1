package com.ipin.mynote;

// 10120164 M HASBI FADILAH , IF4 //


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginPage extends AppCompatActivity {

    private Button btnToRegis;
    private Button btnLogin, btnLoginGoogle;
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
        setContentView(R.layout.loginpage);

        btnToRegis = findViewById(R.id.btnToRegis);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnToRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                String email, password;
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginPage.this, "Email cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginPage.this, "Password cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(LoginPage.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}

