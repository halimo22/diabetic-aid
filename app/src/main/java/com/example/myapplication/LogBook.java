package com.example.myapplication;

import static com.example.myapplication.User.getaverage;
import static com.example.myapplication.User.user;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;


public class LogBook extends Fragment {
    static FloatingActionButton edit;
    static FloatingActionButton delete;
    static ResultsRecyclerViewAdaptor ReadingAdapter;
    static TextView avg;
    FloatingActionButton time;
    static FloatingActionButton add;
    static ArrayList<Reading> readings=new ArrayList<>();
    static Database database;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_log_book, container, false);
        RecyclerView resultsview = v.findViewById(R.id.results_view);
        delete = v.findViewById(R.id.delete_button2);
        time=v.findViewById(R.id.time_filter);
        avg=v.findViewById(R.id.valueof1day);
        getReadingsOfDate(LocalDate.now().toString());
        time.setOnClickListener(v1 -> {
            showDialoguetime();
            ReadingAdapter.notifyDataSetChanged();
        });
        ReadingAdapter = new ResultsRecyclerViewAdaptor();
        ReadingAdapter.setResults(readings);
        resultsview.setAdapter(ReadingAdapter);
        resultsview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        add = v.findViewById(R.id.floatingActionButton2);
        add.setOnClickListener(v1 -> showDialogue1());
        edit=v.findViewById(R.id.edit_button);
        delete.setOnClickListener(v1 -> {
            Iterator<Reading> i = ReadingAdapter.select.iterator();
            while (i.hasNext()) {
                Reading r=i.next();
                readings.remove(r);
                database.deleterow(r.id);
                user.readings.remove(r);
                i.remove();
                deletevisibility(ReadingAdapter.select);
            }
            ReadingAdapter.notifyDataSetChanged();
            try {
                avg.setText(String.valueOf(getaverage(readings)));
            }
            catch (ArithmeticException e)
            {
                avg.setText("0");
            }
        });
        edit.setOnClickListener(v1-> showedit_dialogue());
        return v;
    }
    public void showDialogue1() {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialogue_1);
        dialog.show();
        final EditText blood = dialog.findViewById(R.id.blood_edittext);
        final EditText food = dialog.findViewById(R.id.food_edit);
        final Button confirm = dialog.findViewById(R.id.confirm_button);
        final Button cancel = dialog.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(v -> dialog.dismiss());

        confirm.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    user.readings.add(new Reading(LocalDateTime.now(), Integer.parseInt(blood.getText().toString()), Integer.parseInt(food.getText().toString())));
                    dialog.dismiss();
                    showDialogue2();
                }
                catch(Exception e)
                {
                    showToast("Invalid Input");
                }



            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getReadingsOfDate(String Date)
    {
        LocalDate time=LocalDate.now();
        try{
            time=User.CheckBirthday(Date);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT);
        }
        readings=new ArrayList<>();
        for (Reading r: user.readings
             ) {
            if ((r.time.getYear()== time.getYear())&& (r.time.getMonthValue()==r.time.getMonthValue())&&(r.time.getDayOfMonth()==time.getDayOfMonth())){
                readings.add(r);
            }
        }
        try {
            avg.setText(String.valueOf(getaverage(readings)));
        }
        catch (ArithmeticException e)
        {
            avg.setText("0");
        }
    }
    public void showToast(String string) {
        Toast.makeText(this.getContext(), string, Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDialogue2() {  // needs tracing for exception
        final Dialog dialog2 = new Dialog(this.getContext());
        Database database= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            database = new Database(this.getContext());
        }
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.dialogue_2);
        dialog2.show();
        final EditText insulin = dialog2.findViewById(R.id.insulin_edittext);
        final Button confirm = dialog2.findViewById(R.id.confirm_button2);
        final Button cancel = dialog2.findViewById(R.id.cancel_button2);
        final TextView insulin_text=dialog2.findViewById(R.id.insulin_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            insulin_text.setText(String.format("Recommended Amount : %d",user.readings.get(user.readings.size()-1).getRecommendedInsulin()));
        }
        Database finalDatabase = database;
        cancel.setOnClickListener(v ->{
            dialog2.dismiss();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                finalDatabase.addone(user.readings.get(user.readings.size()-1));
            }
            getReadingsOfDate(LocalDate.now().toString());
            ReadingAdapter.setResults(readings);
            showToast("Done");
            ReadingAdapter.notifyDataSetChanged();
            avg.setText(String.valueOf(getaverage(readings)));
        });

        Database finalDatabase1 = database;
        confirm.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    user.readings.get(user.readings.size() - 1).setInsulin(Integer.parseInt(insulin.getText().toString()));
                    finalDatabase1.addone(user.readings.get(user.readings.size()-1));
                    getReadingsOfDate(LocalDate.now().toString());
                    ReadingAdapter.setResults(readings);
                    showToast("Done");
                    ReadingAdapter.notifyDataSetChanged();
                    dialog2.dismiss();
                    avg.setText(String.valueOf(getaverage(readings)));
                }
                catch (Exception e) {
                    showToast("Invalid Input");
                }

            }
        });

    }
    public void showedit_dialogue()
    {
        final Dialog dialog=new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.edit_dialogue);
        final EditText blood_edit=dialog.findViewById(R.id.edit_blood_edittext);
        final EditText insulin_edit=dialog.findViewById(R.id.insulin_edit);
        final Button confirm_edit=dialog.findViewById(R.id.confirm_button3);
        final Button cancel_edit=dialog.findViewById(R.id.cancel_button3);
        cancel_edit.setOnClickListener(v-> dialog.dismiss());
        confirm_edit.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ReadingAdapter.select.get(0).setInsulin(Integer.parseInt(insulin_edit.getText().toString()));
                ReadingAdapter.select.get(0).setLevel(Integer.parseInt(blood_edit.getText().toString()));
                ReadingAdapter.select.get(0).setSelected(0);
                database.updaterow(ReadingAdapter.select.get(0),ReadingAdapter.select.get(0).id);
                ReadingAdapter.select.remove(0);
                ReadingAdapter.notifyDataSetChanged();
                dialog.dismiss();
                deletevisibility(ReadingAdapter.select);
                avg.setText(String.valueOf(getaverage(readings)));
            }
        });
        dialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDialoguetime()
    {
        final Dialog dialog=new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialogue_time);
        EditText time_text=dialog.findViewById(R.id.time_edittext);
        Button confirm_time=dialog.findViewById(R.id.confirmtime_button);
        Button cancel=dialog.findViewById(R.id.canceltime_button);
        cancel.setOnClickListener(v -> dialog.dismiss());
        confirm_time.setOnClickListener(v -> {
            try {
                LocalDate T=User.CheckBirthday(time_text.getText().toString());
                getReadingsOfDate(time_text.getText().toString());
                ReadingAdapter.setResults(readings);
                showToast("Done");
                ReadingAdapter.notifyDataSetChanged();
                try {
                    avg.setText(String.valueOf(getaverage(readings)));
                }
                catch (ArithmeticException e)
                {
                    avg.setText("0");
                }
                dialog.dismiss();
            } catch (Exception e){
                showToast("Invalid Input");

            }


        });
        dialog.show();

    }
    public static void deletevisibility(ArrayList<Reading> select) {

        if (select.isEmpty()){
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

        }
        else {
            delete.setVisibility(View.VISIBLE);
            if (select.size() == 1)
                edit.setVisibility(View.VISIBLE);
            else edit.setVisibility(View.GONE);
        }

    }

}