package com.ipin.mynote;

// 10120164 M HASBI FADILAH, IF4

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipin.mynote.Adapter.CatatanAdapter;
import com.ipin.mynote.Model.CatatanModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatatanFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton btnFloatAdd;
    private DatabaseReference ref;
    private CatatanAdapter adapter;
    private ArrayList<CatatanModel> mList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catatan, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        btnFloatAdd = view.findViewById(R.id.btnFloatAdd);

        ref = FirebaseDatabase.getInstance().getReference("data");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnFloatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCatatan.newInstance().show(getActivity().getSupportFragmentManager(), AddNewCatatan.TAG);
            }
        });

        mList = new ArrayList<>();
        recyclerView.setLayoutManager((new LinearLayoutManager(getContext())));

        adapter = new CatatanAdapter(getContext(), mList);

        recyclerView.setAdapter(adapter);
        showData();

        return view;
    }

    private void showData(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CatatanModel catatanModel = dataSnapshot.getValue(CatatanModel.class);
                    String key = dataSnapshot.getKey();
                    mList.add(catatanModel);
                    catatanModel.setKey(key);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}