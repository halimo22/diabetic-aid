package com.example.myapplication;

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

public class ResultsRecyclerViewAdaptor extends RecyclerView.Adapter<ResultsRecyclerViewAdaptor.ViewHolder> {
    ArrayList<Reading> readings=new ArrayList<>();
    public ArrayList<Reading> select=new ArrayList<>();
    public ArrayList<Reading> getResults() {
        return readings;
    }

    public void setResults(ArrayList<Reading> readings) {
        this.readings = readings;
        notifyDataSetChanged();
    }
    public void addResults(Reading reading) {
        readings.add(reading);
        notifyDataSetChanged();
    }

    ResultsRecyclerViewAdaptor()
    {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.results_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.resultview.setText(Integer.toString(readings.get(position).getLevel()));
       holder.timeview.setText(readings.get(holder.getAdapterPosition()).getTime().toString());
       holder.unitsview.setText(String.valueOf(readings.get(holder.getAdapterPosition()).getInsulin()));
       if(readings.get(holder.getAdapterPosition()).getSelected()==0) {
           holder.itemView.setBackgroundColor(Color.parseColor(readings.get(holder.getAdapterPosition()).getColor()));
       }
       else holder.itemView.setBackgroundColor(Color.parseColor("#89cff0"));
       holder.itemView.setOnLongClickListener(v -> {
            if(readings.get(holder.getAdapterPosition()).getSelected()==0) {
                holder.itemView.setBackgroundColor(Color.parseColor("#89cff0"));
                select.add(readings.get(holder.getAdapterPosition()));
                LogBook.deletevisibility(select);
                readings.get(holder.getAdapterPosition()).setSelected(1);

            }
            else {
                holder.itemView.setBackgroundColor(Color.parseColor(readings.get(holder.getAdapterPosition()).getColor()));
                select.remove(
                        readings.get(holder.getAdapterPosition()));
                LogBook.deletevisibility(select);
                readings.get(holder.getAdapterPosition()).setSelected(0);

            }
         return true;});
    }

    @Override
    public int getItemCount() {
        return readings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView resultview;
        private TextView timeview;
        private TextView unitsview;
        private RelativeLayout background;

        public ViewHolder(View itemview) {
            super(itemview);
            resultview=itemview.findViewById(R.id.result_value);
            timeview=itemview.findViewById(R.id.time_value);
            background=itemview.findViewById(R.id.parent);
            unitsview=itemview.findViewById(R.id.units_value);
        }
    }
}
