package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calculator extends Fragment {


    ArrayList<Carb> food;
    FoodAdaptor foodAdaptor;
    ArrayList<Food> foodeaten=new ArrayList<>();
    RecyclerView foodview;
    TextView totalcarbs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_calculator2, container, false);
        totalcarbs=v.findViewById(R.id.textView3);
        totalcarbs.setText("0");

        food=new ArrayList<>();
        foodview=v.findViewById(R.id.food_view);
        foodview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        MyDBHandler dbHandler =new MyDBHandler(getContext(),null,null,2);
        food=dbHandler.loadHandler();
        foodeaten=new ArrayList<>();
        foodAdaptor=new FoodAdaptor();
        foodview.setAdapter(foodAdaptor);
        foodAdaptor.setfood(foodeaten);
        Button addfood=v.findViewById(R.id.add_food);
        Button done=v.findViewById(R.id.done_calc);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodeaten.clear();
                totalcarbs.setText("0");
                foodAdaptor.notifyDataSetChanged();

            }
        });
        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddfoodDialogue();
            }
        });
        return v;
    }
    static Dialog dialog;
    void showaddfoodDialogue()
    {
        dialog= new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialogue_addfood);
        dialog.show();
        RecyclerView recyclerView=dialog.findViewById(R.id.chooseview);
        FoodSearchAdaptor adaptor=new FoodSearchAdaptor();
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        ArrayList<Carb> adaptorcarb=new ArrayList<>();
        adaptorcarb.addAll(food);
        adaptor.setfood(adaptorcarb);
        SearchView search=dialog.findViewById(R.id.search);
        Addfooddialog();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptor.filter(newText,food);
                return true;
            }
        });


    }
    static Carb selectedcarb;
    static public void hidefooddialog()
    {
        dialog.dismiss();
    }
    static Dialog dialog1;
     private void Addfooddialog()
    {
        dialog1=new Dialog(this.getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.addfooddialog);
        EditText amount=dialog1.findViewById(R.id.quantityinput);
        Button confirm=dialog1.findViewById(R.id.confirm_button5);
        Button cancel=dialog1.findViewById(R.id.cancelbutton5);
        cancel.setOnClickListener(v->dialog1.dismiss());
        confirm.setOnClickListener(v->
        {
            try{
                int amount_=Integer.parseInt(amount.getText().toString());
                foodAdaptor.addResults(new Food(selectedcarb,amount_));
                totalcarbs.setText(Integer.toString(foodAdaptor.getTotal()));
                dialog1.dismiss();

            }
            catch (Exception e)
            {
                Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
            }

        });

    }
    public static void showdialog1()
    {
        dialog1.show();
    }

}
