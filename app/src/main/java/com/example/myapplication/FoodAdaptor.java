package com.example.myapplication;

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
import java.util.Locale;

public class FoodAdaptor extends RecyclerView.Adapter<FoodAdaptor.ViewHolder> {

    public int getTotal() {
        int total = 0;
        for (Food f : foods
        ) {
            total += f.getCarbstaken();
        }
        return total;
    }

    ArrayList<Food> foods = new ArrayList<>();
    public ArrayList<Reading> select = new ArrayList<>();

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setfood(ArrayList<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public void addResults(Food food) {
        foods.add(food);
        notifyDataSetChanged();
    }

    FoodAdaptor() {

    }

    @NonNull
    @Override
    public com.example.myapplication.FoodAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_calc_item, parent, false);
        FoodAdaptor.ViewHolder viewHolder = new FoodAdaptor.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull com.example.myapplication.FoodAdaptor.ViewHolder holder, int position) {
        holder.carbvalue.setText(Integer.toString((int)Math.round(foods.get(position).getCarbstaken())));
        holder.name.setText(foods.get(holder.getAdapterPosition()).getName());
        holder.itemView.setOnClickListener(v -> {
            Calculator.selectedcarb = foods.get(holder.getAdapterPosition());
            Calculator.hidefooddialog();
            Calculator.showdialog1();
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
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
}
