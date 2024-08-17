package com.example.myapplication;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public String Message;
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((Intent.ACTION_BOOT_COMPLETED).equals(intent.getAction())){
            // reset all alarms
        }
        else{
            mp=MediaPlayer.create(context, R.raw.alarm);
            mp.start();
            intent=new Intent(context,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(context,"Check Your Reminders",Toast.LENGTH_SHORT).show();
            context.startActivity(intent);

        }




    }

}
