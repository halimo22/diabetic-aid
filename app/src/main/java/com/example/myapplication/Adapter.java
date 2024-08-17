package com.example.myapplication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Reminder> reminders;
    private List<Alarms> alarms;

    private Context context;
    Database_alarms database_alarms;

    private OnItemClickListener listener;
    Database2 database=new Database2(context);

    boolean isOntextChanged=false;
    public interface OnItemClickListener{
        void onItemClicl(int position);
    }
    public void setOnItemClickListner(OnItemClickListener clickListner){
        listener=clickListner;
    }
    public Adapter(Context c, List<Reminder> reminders){
        this.context=c;
        this.reminders=reminders;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView timetxt,labeltxt;
        private Button btn_delete;
        int t1hour,t1minute;
        Adapter adapter;





        public MyViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            labeltxt=itemView.findViewById(R.id.textView);
            timetxt=itemView.findViewById(R.id.textView2);
            Database2 database=new Database2(context);
            database_alarms=new Database_alarms(context);
            alarms=database_alarms.getEverything();
            reminders=database.getEverything();
            btn_delete=itemView.findViewById(R.id.button);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(context, MyReceiver.class);
                   PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarms.get(getAdapterPosition()).get_id(), myIntent,  PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                    if(pendingIntent != null) {
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(context,"Reminder Done",Toast.LENGTH_SHORT).show();
                    }
                    database.deleterow(reminders.get(getAdapterPosition()).getId());
                    database_alarms.deleterow(alarms.get(getAdapterPosition()));
                    reminders.remove(getAdapterPosition());
                    notifyDataSetChanged();


                }
            });
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);

       return new MyViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     Reminder r= reminders.get(position);
    holder.labeltxt.setText(reminders.get(position).getLabel());
    holder.timetxt.setText(reminders.get(position).getTime());

    }



    @Override
    public int getItemCount() {
        return reminders.size();
    }
}
