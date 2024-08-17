package com.example.myapplication;
import static android.content.Context.ALARM_SERVICE;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class Reminders extends Fragment {
    private static final DateFormat TWELVE_TF = new SimpleDateFormat("hh:mm:aa");

    private static final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm");
    Database2 database;
    Database_alarms database_alarms;
    Adapter adapter;
    private ArrayList<Reminder> reminders;
    private RecyclerView recyclerView;
    private List<Reminder> reminderList;
    int hours,mins;

    TextView txt_time;
    Button btn_add;
    String selct;
    String date2;
    FloatingActionButton openDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_reminders, container, false);
        database=new Database2(getContext());
        database_alarms=new Database_alarms(getContext());
        recyclerView=v.findViewById(R.id.recycleview);
        reminderList=database.getEverything();
        adapter=new Adapter(getContext(),reminderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClicl(int position) {
                reminders.remove(position);
                adapter.notifyItemRemoved(position);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                Intent myIntent = new Intent(getContext(), MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getContext(), 1, myIntent, PendingIntent.FLAG_IMMUTABLE);

                alarmManager.cancel(pendingIntent);

            }
        });
        openDialog=v.findViewById(R.id.floatingActionButton);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();


            }
        });
        return v;
    }
    Reminder reminder=new Reminder();


    private void showCustomDialog() {
        final Dialog dialog= new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);
        final EditText label=dialog.findViewById(R.id.editText1);
        final TextView timetxt=dialog.findViewById(R.id.textView3);
        Button btn_add=dialog.findViewById(R.id.button2);
        Button btn_can=dialog.findViewById(R.id.button3);
        btn_can.setOnClickListener(v -> dialog.dismiss());
        timetxt.setOnClickListener(new View.OnClickListener() {
            int t1hour,t1minute;
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                t1hour=hourOfDay;
                                t1minute=minute;
                                String time=t1hour + ":" +t1minute;

                                SimpleDateFormat format=new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat format1=new SimpleDateFormat("hh:mm:aa");
                                    timetxt.setText(format1.format(date));
                                    selct=format1.format(date);





                                }catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1hour,t1minute);
                timePickerDialog.show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setAlarmReqCode(hours,mins)) {
                    String name = label.getText().toString();
                    String timetx = timetxt.getText().toString();
                    reminder.setLabel(name);
                    reminder.setTime(timetx);
                    database.addone(reminder);
                    reminderList = database.getEverything();
                    adapter = new Adapter(getContext(), reminderList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    try {
                        date2 = convertTo24HoursFormat(selct);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    getstime(date2);


                }
                dialog.dismiss();

            }
        });
        dialog.show();



    }


    public static String convertTo24HoursFormat(String twelveHourTime)
            throws ParseException {
        return TWENTY_FOUR_TF.format(
                TWELVE_TF.parse(twelveHourTime));



    }

    public void getstime(String time){
        for(int i=0;i<time.length();i++){

        }
        String a="" +time.charAt(0)+time.charAt(1);
        String b="" +time.charAt(3)+time.charAt(4);
        hours=Integer.parseInt(a);
        mins=Integer.parseInt(b);

    }
    public boolean setAlarmReqCode(int hour,int min){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(alarmManager.canScheduleExactAlarms()) {
                final int _id = (int) System.currentTimeMillis();
                Alarms alarm = new Alarms(_id);
                database_alarms.add(alarm);
                Intent intent = new Intent(getContext(), MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), _id, intent, PendingIntent.FLAG_IMMUTABLE);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hour);  // set hour
                cal.set(Calendar.MINUTE, min);          // set minute
                cal.set(Calendar.SECOND, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                Toast.makeText(getContext(), "Alarm set ", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(getContext(),"Alarm Permsission Required",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            final int _id = (int) System.currentTimeMillis();
            Alarms alarm = new Alarms(_id);
            database_alarms.add(alarm);
            Intent intent = new Intent(getContext(), MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), _id, intent, PendingIntent.FLAG_IMMUTABLE);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hour);  // set hour
            cal.set(Calendar.MINUTE, min);          // set minute
            cal.set(Calendar.SECOND, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            Toast.makeText(getContext(), "Alarm set ", Toast.LENGTH_LONG).show();

        }
        return true;
    }



}

