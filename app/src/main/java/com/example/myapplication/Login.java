package com.example.myapplication;

import static com.example.myapplication.User.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    FirebaseAuth firebase;
    FirebaseDatabase firebaseDatabase;
    Button login;
    EditText email;
    EditText password;
    TextView loginerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login_button);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        firebase=FirebaseAuth.getInstance();
        loginerror=findViewById(R.id.loginerror);
        firebaseDatabase=FirebaseDatabase.getInstance();
        login.setOnClickListener(v
                ->
        {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Logging in");
            progressDialog.show();
            loginerror.setVisibility(View.GONE);
            firebase.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getApplicationContext(),"Login Succesful",Toast.LENGTH_SHORT).show();
                    user=new User();
                    user.setEmail(email.getText().toString());
                    DatabaseReference ref=firebaseDatabase.getReference().child("User");
                    progressDialog.hide();
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            user.setLastname(snapshot.child(firebase.getCurrentUser().getUid()).child("Last Name").getValue(String.class));
                            user.setFirstname(snapshot.child(firebase.getCurrentUser().getUid()).child("First Name").getValue(String.class));
                            user.setBirthday(User.CheckBirthday(snapshot.child(firebase.getCurrentUser().getUid()).child("Birthday").getValue(String.class)));
                            user.setGender(Gender.valueOf(snapshot.child(firebase.getCurrentUser().getUid()).child("Gender").getValue(String.class)));
                            user.setCarb_unit(snapshot.child(firebase.getCurrentUser().getUid()).child("Grams per Unit").getValue(Integer.class));
                            user.setMgm_unit(snapshot.child(firebase.getCurrentUser().getUid()).child("mgm per Unit").getValue(Integer.class));
                            user.setHeight(snapshot.child(firebase.getCurrentUser().getUid()).child("Height").getValue(Integer.class));
                            user.setWeight(snapshot.child(firebase.getCurrentUser().getUid()).child("Weight").getValue(Integer.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.hide();
                        }

                    });

               finish(); }
            });
            firebase.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loginerror.setVisibility(View.VISIBLE);
                    progressDialog.hide();
                }
            });
        });
        TextView register=findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Register_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}