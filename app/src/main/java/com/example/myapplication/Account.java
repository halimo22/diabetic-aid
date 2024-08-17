package com.example.myapplication;

import static com.example.myapplication.User.user;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;

public class Account extends Fragment {
    StorageReference storageReference;
    FirebaseAuth firebase;
    FirebaseFirestore db;
    TextView tvHi, tvNormal, tvLow,w1,m1,m3;
    PieChart pieChart;
    Button backup;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_account, container, false);
        tvHi = v.findViewById(R.id.High_tv);
        backup=v.findViewById(R.id.backup);
        tvNormal = v.findViewById(R.id.Normal_tv);
        tvLow = v.findViewById(R.id.Low_tv);
        w1=v.findViewById(R.id.onewavg);
        m1=v.findViewById(R.id.onemavg);
        m3=v.findViewById(R.id.threemavg);
        pieChart = v.findViewById(R.id.piechart);
        tvHi.setText(Integer.toString(user.getNumberofHigh()));
        tvNormal.setText(Integer.toString(user.getNumberofNormal()));
        tvLow.setText(Integer.toString(user.getNumberofLow()));
        firebase=FirebaseAuth.getInstance();
        try {
            w1.setText(Integer.toString(user.get1weekAverage()));
            m1.setText(Integer.toString(user.get1monthAverage()));
            m3.setText(Integer.toString(user.get3monthAverage()));
        }
        catch (ArithmeticException e)
        {
            w1.setText("0");
            m1.setText("0");
            m3.setText("0");
        }

        pieChart.addPieSlice(
                new PieModel(
                        "Hi",
                        Integer.parseInt(tvHi.getText().toString()),
                        Color.parseColor("#F8A76C")));
        pieChart.addPieSlice(
                new PieModel(
                        "Normal",
                        Integer.parseInt(tvNormal.getText().toString()),
                        Color.parseColor("#C2D8B8")));
        pieChart.addPieSlice(
                new PieModel(
                        "Low",
                        Integer.parseInt(tvLow.getText().toString()),
                        Color.parseColor("#E94C42")));
        backup.setOnClickListener(v1 ->{
            storageReference=FirebaseStorage.getInstance().getReference();
            File file=new File("/data/user/0/com.example.myapplication/databases/user1.db");
            if(file.exists()){Uri uri=Uri.fromFile(file);
                final ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Backup");
                progressDialog.show();
                storageReference.child(firebase.getCurrentUser().getUid()).child("user1.db").putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri url=uriTask.getResult();
                        Toast.makeText(getContext(),"Backup Sucessful",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=100.0* snapshot.getBytesTransferred()/ snapshot.getTotalByteCount();
                        progressDialog.setMessage(progress+"%");

                    }
                });


            } });


        return v;


    }
}