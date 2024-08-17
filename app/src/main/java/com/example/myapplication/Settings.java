package com.example.myapplication;

import static com.example.myapplication.User.user;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Settings extends Fragment {

    int input = 0;
    int selection = 0;
    TextView carbtv;
    TextView sugartv;
    private FirebaseAuth firebase;
    private FirebaseDatabase firebasedatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebase = FirebaseAuth.getInstance();
        firebasedatabase = FirebaseDatabase.getInstance();
        View v = inflater.inflate(R.layout.fragment_settings2, container, false);
        CardView carbset = v.findViewById(R.id.setOptimalcarbs);
        carbset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 1;
                showinputdialog();
            }
        });
        carbtv = v.findViewById(R.id.valueofcarbsperunit);
        sugartv = v.findViewById(R.id.valueofsugarperunit);
        carbtv.setText(String.valueOf(user.getCarb_unit()));
        sugartv.setText(String.valueOf(user.getMgm_unit()));
        CardView sugarset = v.findViewById(R.id.setOptimalsugar);
        sugarset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 2;
                showinputdialog();
            }
        });

        return v;

    }

    private void showinputdialog() {
        Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.editinput);
        Button confirm = dialog.findViewById(R.id.confirm_button5);
        Button cancel = dialog.findViewById(R.id.cancelbutton5);
        EditText input_edit = dialog.findViewById(R.id.quantityinput);
        confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    input = Integer.parseInt(input_edit.getText().toString());
                    Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT);
                    if (input <= 0) input = 30;
                    dialog.dismiss();


                    if (selection == 1) {
                        user.setCarb_unit(input);
                        carbtv.setText(String.valueOf(user.getCarb_unit()));
                    } else if (selection == 2) {
                        user.setMgm_unit(input);
                        sugartv.setText(String.valueOf(user.getMgm_unit()));
                    }
                    dialog.dismiss();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("First Name",user.getFirstname());
                    map.put("Last Name",user.getLastname());
                    map.put("Gender",user.getGender().toString());
                    map.put("Birthday",user.getBirthday().toString());
                    map.put("Grams per Unit",user.getCarb_unit());
                    map.put("mgm per Unit",user.getMgm_unit());
                    map.put("Weight",user.getWeight());
                    map.put("Height",user.getHeight());
                    map.put("Email",user.getEmail());
                    firebasedatabase.getReference().child("User").child(firebase.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_SHORT);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
