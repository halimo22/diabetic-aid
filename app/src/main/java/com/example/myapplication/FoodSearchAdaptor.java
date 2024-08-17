package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

 public class FoodSearchAdaptor extends RecyclerView.Adapter<com.example.myapplication.FoodSearchAdaptor.ViewHolder> {

     ArrayList<Carb> carb = new ArrayList<>();
    public ArrayList<Reading> select = new ArrayList<>();

    public ArrayList<Carb> getCarbs() {
        return carb;
    }

    public void setfood(ArrayList<Carb> carb) {
        this.carb = carb;
        notifyDataSetChanged();
    }

    public void addResults(Carb carb_) {
        carb.add(carb_);
        notifyDataSetChanged();
    }

    FoodSearchAdaptor() {

    }

    @NonNull
    @Override
    public com.example.myapplication.FoodSearchAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_view_item, parent, false);
        com.example.myapplication.FoodSearchAdaptor.ViewHolder viewHolder = new com.example.myapplication.FoodSearchAdaptor.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull com.example.myapplication.FoodSearchAdaptor.ViewHolder holder, int position) {
        holder.carbvalue.setText(Integer.toString(carb.get(position).getCpg()));
        holder.name.setText(carb.get(holder.getAdapterPosition()).getName());
        holder.itemView.setOnClickListener(v -> {
            Calculator.selectedcarb=carb.get(holder.getAdapterPosition());
            Calculator.hidefooddialog();
            Calculator.showdialog1();
        });
    }

    @Override
    public int getItemCount() {
        return carb.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView carbvalue;
        private RelativeLayout background;

        public ViewHolder(View itemview) {
            super(itemview);
            name = itemview.findViewById(R.id.nameLabel);
            carbvalue = itemview.findViewById(R.id.dialog_carbview);
            background = itemview.findViewById(R.id.fooditem);

        }
    }
     public void filter(String charText,ArrayList<Carb> fooddb) {
         charText = charText.toLowerCase(Locale.getDefault());
         carb.clear();
         if (charText.length() == 0) {
             carb.addAll(fooddb);
         } else {
             for (Carb wp : fooddb) {
                 if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                     carb.add(wp);
                 }
             }
         }
         notifyDataSetChanged();
     }

    }