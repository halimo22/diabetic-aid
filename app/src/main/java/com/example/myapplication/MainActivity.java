package com.example.myapplication;

import static com.example.myapplication.User.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseUser fuser;
    NavigationView nav_draw;
    FirebaseAuth firebase;
    FirebaseDatabase firebaseDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebase=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        fuser= firebase.getCurrentUser();
        if(fuser==null)
        {
            user=new User();
            Intent intent=new Intent(this,Register_Activity.class);
            startActivity(intent);
            LogBook.database=new Database(this);
            user.readings=new ArrayList<>();
            LogBook.readings=user.readings;
        } else {
            user=new User();
            DatabaseReference ref=firebaseDatabase.getReference().child("User");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    user.setLastname(snapshot.child(firebase.getCurrentUser().getUid()).child("Last Name").getValue(String.class));
                    user.setFirstname(snapshot.child(firebase.getCurrentUser().getUid()).child("First Name").getValue(String.class));
                    user.setBirthday(User.CheckBirthday2(snapshot.child(firebase.getCurrentUser().getUid()).child("Birthday").getValue(String.class)));
                    user.setGender(Gender.valueOf(snapshot.child(firebase.getCurrentUser().getUid()).child("Gender").getValue(String.class)));
                    user.setCarb_unit(snapshot.child(firebase.getCurrentUser().getUid()).child("Grams per Unit").getValue(Integer.class));
                    user.setMgm_unit(snapshot.child(firebase.getCurrentUser().getUid()).child("mgm per Unit").getValue(Integer.class));
                    user.setHeight(snapshot.child(firebase.getCurrentUser().getUid()).child("Height").getValue(Integer.class));
                    user.setWeight(snapshot.child(firebase.getCurrentUser().getUid()).child("Weight").getValue(Integer.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            LogBook.database=new Database(this);
            user.readings= LogBook.database.getEverything();
            Reading.itemcount=user.readings.size();
            LogBook.readings=new ArrayList<>();
            for (Reading r:user.readings
            ) {
                LogBook.readings.add(r);
            }

        }
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_draw=findViewById(R.id.nav_draw);
        nav_draw.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new LogBook()).commit();
        }


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_logbook) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new LogBook()).commit();

        }
        else if(itemId==R.id.nav_calc)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new Calculator()).commit();
        }
        else if(itemId==R.id.nav_reminders)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new Reminders()).commit();
        }
        else if(itemId==R.id.nav_account)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new Account()).commit();

        } else if (itemId==R.id.nav_logout) {
            firebase.signOut();
            LogBook.database.deleteall();
            Intent intent=new Intent(this,Register_Activity.class);
            startActivity(intent);
            LogBook.readings.clear();
            LogBook.avg.setText("0");
            LogBook.ReadingAdapter.notifyDataSetChanged();
        }
        else if (itemId==R.id.nav_settings)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new Settings()).commit();

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /*private void copyDataBase(Context context) throws IOException {
        try {
            InputStream inputStream = context.getAssets().open(Database_search.dbname2);
            String outputfilename = Database_search.dbpath;
            byte[] buff = new byte[1024];
            int length = 0;
            OutputStream outputStream = new FileOutputStream(outputfilename);
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Toast.makeText(getApplicationContext(),"Copy Success",Toast.LENGTH_SHORT);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }*/




}