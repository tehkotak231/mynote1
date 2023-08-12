package com.ipin.mynote;

// 10120164 M HASBI FADILAH , IF4

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewCatatan extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewCatatan";

    public static AddNewCatatan newInstance(){
        return new AddNewCatatan();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_catatan, container, false);
    }

    private EditText inputJudul,inputCatatan;
    private Button btnSave;
    private DatabaseReference fireRef;
    private Context context;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputJudul = view.findViewById(R.id.inputJudul);
        inputCatatan = view.findViewById(R.id.inputCatatan);
        btnSave = view.findViewById(R.id.btnSave);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judulGet = inputJudul.getText().toString();
                String catatanGet = inputCatatan.getText().toString();
                if(TextUtils.isEmpty(judulGet)){
                    Toast.makeText(getContext(), "Title cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(catatanGet)){
                    Toast.makeText(getContext(), "Catatan cannot empty!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Map<String, Object> catatanMap = new HashMap<>();
                    catatanMap.put("judul", judulGet);
                    catatanMap.put("catatan", catatanGet);
                    catatanMap.put("tanggal", ""+year + "-" + month + "-" + day);

                    fireRef = FirebaseDatabase.getInstance().getReference().child("data");
                    String ID_Key = fireRef.push().getKey();

                    fireRef.child(ID_Key).setValue(catatanMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Catatan successfully saved!", Toast.LENGTH_SHORT).show();
                            inputJudul.setText(null);
                            inputCatatan.setText(null);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Catatan failed to save! try again later. e="+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseError", "Error writing data: " + e.getMessage());
                        }
                    });
                }
//                dismiss();
            }

        });
    }
}
