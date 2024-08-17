package com.example.myapplication;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Register_Activity extends AppCompatActivity {

    User user;
    Button login;
    private FirebaseAuth firebase;
    private FirebaseDatabase firebasedatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_dialogue);
        firebase = FirebaseAuth.getInstance();
        firebasedatabase = FirebaseDatabase.getInstance();
        Button Guest = findViewById(R.id.register_guest);
        EditText firstname = findViewById(R.id.Register_firstname);
        EditText lastname = findViewById(R.id.Register_lastname);
        EditText email = findViewById(R.id.Register_email);
        EditText birthday = findViewById(R.id.Register_Birthday);
        EditText password = findViewById(R.id.Register_password);
        EditText confirmpassword = findViewById(R.id.Register_confirmpassword);
        Spinner gender = findViewById(R.id.Register_gender);
        Spinner country = findViewById(R.id.Register_Country);
        EditText weight = findViewById(R.id.Register_Weight);
        EditText height = findViewById(R.id.Register_Height);
        EditText gmunit = findViewById(R.id.Register_carbperunit);
        EditText sugarmgmunit = findViewById(R.id.Register_sugarlevelperunit);
        TextView error1 = findViewById(R.id.register_error1);
        TextView error2 = findViewById(R.id.register_error2);
        TextView error3 = findViewById(R.id.register_error3);
        TextView error4 = findViewById(R.id.register_error4);
        TextView error5 = findViewById(R.id.register_error5);
        TextView error6 = findViewById(R.id.register_error6);
        TextView error7 = findViewById(R.id.register_error7);
        TextView error8 = findViewById(R.id.register_error8);
        TextView error9 = findViewById(R.id.register_error9);
        TextView error10 = findViewById(R.id.register_error10);
        login = findViewById(R.id.register_login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });


        Guest.setOnClickListener(v1 -> {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Creating Guest Account");
            progressDialog.show();
            user = new User();
            int max = 1000;
            int min = 50;
            Task authResult = firebase.createUserWithEmailAndPassword(Double.toString(Math.floor(Math.random() * (max - min + 1) + min)), "Admin0000");
            authResult.addOnSuccessListener(v2 ->
            {
                Toast.makeText(this, "Guess Account Created Succesfully", Toast.LENGTH_SHORT).show();
                finish();

            });
            authResult.addOnFailureListener(v2 -> {
                Toast.makeText(this, "Try Again Later ", Toast.LENGTH_SHORT).show();

            });
        });

        Button register = findViewById(R.id.Register_Register);
        register.setOnClickListener(v2 -> {
            user = new User();
            Integer[] i = new Integer[10];
            Arrays.fill(i, 0);
            if (firstname.getText().toString().isBlank()) {
                error1.setVisibility(View.VISIBLE);
                error1.setText("Field is Empty");
                i[0] = 1;
            } else if (!firstname.getText().toString().matches("[a-zA-Z]+")) {
                error1.setVisibility(View.VISIBLE);
                error1.setText("Invalid First Name");
                i[0] = 1;
            } else {
                user.setFirstname(firstname.getText().toString());
                error1.setVisibility(View.GONE);
                i[0] = 0;
            }

            if (lastname.getText().toString().isBlank()) {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Field is Empty");
                i[1] = 1;
            } else if (!lastname.getText().toString().matches("[a-zA-Z]+")) {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Invalid Last Name");
                i[1] = 1;
            } else {
                user.setLastname(lastname.getText().toString());
                error2.setVisibility(View.GONE);
                i[1] = 0;
            }
            if (!User.CheckEmail(email.getText().toString())) {
                error10.setVisibility(View.VISIBLE);
                i[2] = 1;
            } else {
                user.setEmail(email.getText().toString());
                error10.setVisibility(View.GONE);
                i[2] = 0;
            }
            if (confirmpassword.getText().toString().equals(password.getText().toString()) && !confirmpassword.getText().toString().isBlank()) {
                if (User.CheckPassword(password.getText().toString())) {
                    user.setPassword(password.getText().toString());
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    i[3] = 0;
                } else {
                    error3.setVisibility(View.VISIBLE);
                    i[3] = 1;
                }

            } else {
                error4.setVisibility(View.VISIBLE);
                error3.setVisibility(View.VISIBLE);
                i[3] = 1;
            }
            try {
                user.setBirthday(User.CheckBirthday(birthday.getText().toString()));
                error5.setVisibility(View.GONE);
                i[4] = 0;
            } catch (Exception e) {
                error5.setVisibility(View.VISIBLE);
                i[4] = 1;
            }
            try {
                user.setWeight(Integer.parseInt(weight.getText().toString()));
                error6.setVisibility(View.GONE);
                i[5] = 0;
            } catch (Exception e) {
                error6.setVisibility(View.VISIBLE);
                i[5] = 1;
            }
            try {
                user.setHeight(Integer.parseInt(height.getText().toString()));
                error7.setVisibility(View.GONE);
                i[6] = 0;
            } catch (Exception e) {
                error7.setVisibility(View.VISIBLE);
                i[6] = 1;
            }
            try {
                user.setCarb_unit(Integer.parseInt(gmunit.getText().toString()));
                error8.setVisibility(View.GONE);
                i[7] = 0;
            } catch (Exception e) {
                error8.setVisibility(View.VISIBLE);
                i[7] = 1;
            }
            try {
                user.setMgm_unit(Integer.parseInt(sugarmgmunit.getText().toString()));
                error9.setVisibility(View.GONE);
                i[8] = 0;
            } catch (Exception e) {
                error9.setVisibility(View.VISIBLE);
                i[8] = 1;
            }
            Gender gender1 = Gender.valueOf(gender.getSelectedItem().toString());
            user.setGender(gender1);
            user.setCountry(country.getSelectedItem().toString());
            boolean allgood = true;
            for (Integer j : i) {
                if (j.equals(1)) allgood = false;

            }
            if (allgood) {
                firebase.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("First Name", user.getFirstname());
                                map.put("Last Name", user.getLastname());
                                map.put("Gender", user.getGender().toString());
                                map.put("Birthday", user.getBirthday().toString());
                                map.put("Grams per Unit", user.getCarb_unit());
                                map.put("mgm per Unit", user.getMgm_unit());
                                map.put("Weight", user.getWeight());
                                map.put("Height", user.getHeight());
                                map.put("Email", user.getEmail());

                                firebasedatabase.getReference().child("User")
                                        .child(firebase.getCurrentUser().getUid()).setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                ;
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } else
                Toast.makeText(this, "Please Verify the Incorrect Fields", Toast.LENGTH_SHORT).show();

        });

    }

}

