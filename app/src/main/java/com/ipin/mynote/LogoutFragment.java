package com.ipin.mynote;

// 10120164 M HASBI FADILAH , IF4

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutFragment extends Fragment {

    public LogoutFragment(){

    }

    private TextView textEmail;
    private Button btnLogout;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        textEmail = view.findViewById(R.id.textEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
            }
        });

        if(user == null){
            Intent intent = new Intent(getActivity(), LoginPage.class);
            startActivity(intent);
        }else{
            textEmail.setText(user.getEmail());
        }

        return view;
    }
}