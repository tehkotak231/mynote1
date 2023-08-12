package com.ipin.mynote.Adapter;

// 10120164, M HASBI FADILAH, IF4

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ipin.mynote.Model.CatatanModel;
import com.ipin.mynote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.MyViewHolder> {

    Context context;
    ArrayList<CatatanModel> list;

    public CatatanAdapter(Context context, ArrayList<CatatanModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_catatan, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CatatanModel catatanModel = list.get(position);
        holder.tanggal.setText("Created at "+catatanModel.getTanggal());
        holder.judul.setText(catatanModel.getJudul());

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.edit_catatan))
                        .setExpanded(true, 1500)
                        .create();

                view = dialogPlus.getHolderView();

                EditText inputJudulEdit, inputCatatanEdit;
                TextView textTanggal;
                Button btnUpdate;
                DatabaseReference ref;

                textTanggal = view.findViewById(R.id.textTanggal);
                inputJudulEdit = view.findViewById(R.id.inputJudulEdit);
                inputCatatanEdit = view.findViewById(R.id.inputCatatanEdit);
                btnUpdate = view.findViewById(R.id.btnUpdate);

                ref = FirebaseDatabase.getInstance().getReference("data");

                textTanggal.setText(catatanModel.getTanggal());
                inputJudulEdit.setText(catatanModel.getJudul());
                inputCatatanEdit.setText(catatanModel.getCatatan());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("judul", inputJudulEdit.getText().toString());
                        map.put("catatan", inputCatatanEdit.getText().toString());

                        ref.child(catatanModel.getKey()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to updated! e="+e.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You sure?");
                builder.setMessage("Deleted data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    DatabaseReference ref;
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ref = FirebaseDatabase.getInstance().getReference("data");
                        ref.child(catatanModel.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Catatan has been deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Can't delete this time, try again later! e="+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "ngoghey", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, judul;
        Button btnView, btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.textTanggal);
            judul = itemView.findViewById(R.id.textJudul);
            btnView = itemView.findViewById(R.id.btnView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

