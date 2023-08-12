package com.ipin.mynote;

// 10120164 M HASBI FADILAH , IF4

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        final InfoFragment infoFragment = new InfoFragment();
        setFragment(infoFragment);
    }

    private FirebaseAuth auth;
    private FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        final ProfileFragment profileFragment = new ProfileFragment();
        final CatatanFragment catatanFragment = new CatatanFragment();
        final InfoFragment infoFragment = new InfoFragment();
        final LogoutFragment logoutFragment = new LogoutFragment();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    setFragment(profileFragment);
                    return true;
                } else if (id == R.id.catatan) {
                    setFragment(catatanFragment);
                    return true;
                } else if (id == R.id.info) {
                    setFragment(infoFragment);
                    return true;
                } else if (id == R.id.fragLogout){
                    setFragment(logoutFragment);
                    return true;
                }
                return false;
            }
        });



    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
