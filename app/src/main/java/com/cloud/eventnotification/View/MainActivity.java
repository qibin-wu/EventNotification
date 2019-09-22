package com.cloud.eventnotification.View;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cloud.eventnotification.Adapter.eventItemAdapter;
import com.cloud.eventnotification.Model.UserEvents;
import com.cloud.eventnotification.Model.Utility;
import com.cloud.eventnotification.Model.eventItem;
import com.cloud.eventnotification.R;
import com.cloud.eventnotification.controller.ItemClickListener;
import com.cloud.eventnotification.localDB.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private ArrayList<UserEvents> events= new ArrayList<>();
    private static final int LOCATION_CODE = 1;
    private LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestCalendar();
        getPermission();

        try {
            UpdateEvent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getEventFromDB();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss aa");
        eventItem[] items = new  eventItem[events.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = new eventItem(events.get(i).getTitle(), sdf.format(events.get(i).getsTime()));
        }
        eventItemAdapter myAdapter = new eventItemAdapter(this, items);
        ListView eventListView = findViewById(R.id.eventListView);
        eventListView.setAdapter(myAdapter);
        eventListView.setOnItemClickListener(new ItemClickListener(this, items));

        Button btnRef = findViewById(R.id.btnRef);
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent();
                back.setClass(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(back);
            }
        });

    }

    //setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        menu.add(0, 1, 0, "Add Event");
        menu.add(0, 2, 0, "Setting");
        menu.add(0, 3, 0, "Soonest events");
        MenuItem home = menu.add(0, 17, 0, "");
        MenuItem cal = menu.add(0, 18, 0, "");
        cal.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        cal.setIcon(R.drawable.calendar);
        home.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        home.setIcon(R.drawable.home);
        return super.onCreateOptionsMenu(menu);

    }

    //do something by id
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                /*
                Intent intent = new Intent();
                intent.setClass(this, AddEvent.class);
                intent.putExtra("eventNum", this.event.size() + "");
                this.startActivity(intent);*/
                break;
            case 2:
                /*
                Intent intent2 = new Intent();
                intent2.setClass(this, MovieList.class);
                this.startActivity(intent2);*/
                break;
            case 3:/*
                Intent intent3 = new Intent();
                intent3.setClass(this, ConfigTime.class);
                this.startActivity(intent3);*/
                break;
            case 17:
                Intent back = new Intent();
                back.setClass(this, MainActivity.class);
                this.startActivity(back);
                break;
            case 18:
                Intent cal = new Intent();
                cal.setClass(this, EventCalendar.class);
                this.startActivity(cal);
                break;

        }
        return true;
    }

    private void UpdateEvent() throws ParseException {
        Utility.readCalendarEvent(this);
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
        DBHelper dbHelper = new DBHelper(this, "eventn.db", null, 1);
        dbHelper.getWritableDatabase();

        for(int i=0;i<Utility.locations.size();i++)
        {    UUID uuid = UUID.randomUUID();
            UserEvents tempEvent= new UserEvents(Utility.nameOfEvent.get(i)+sdf.parse(Utility.startDates.get(i))+Utility.locations.get(i),Utility.nameOfEvent.get(i),sdf.parse(Utility.startDates.get(i)),sdf.parse(Utility.endDates.get(i)),Utility.locations.get(i));
            dbHelper.addEvent(tempEvent);
        }


    }

    private void getEventFromDB() {

        DBHelper dbHelper = new DBHelper(this, "eventn.db", null, 1);
        dbHelper.getWritableDatabase();
        this.events = dbHelper.selectEvent();
    }

    //ask CALENDAR PERMISSION
    public void requestCalendar() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR)
                != PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALENDAR)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALENDAR,}, 2);
            }
        }
    }
    // get the location permission
    public void getPermission() {
        lm = (LocationManager) MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {// if turn on GPS

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //  permission check
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
                Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();

            } else {

                // user allow permission
            }
        } else {

            // GPS not open
            Toast.makeText(MainActivity.this, "GPS hasn't turn on", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // accept permission

                } else {
                    // deny permission
                    Toast.makeText(MainActivity.this, "Location permissions are disabled, some functions may unavailable!", Toast.LENGTH_LONG).show();
                }

            }
            case 2: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                if (requestCode == 1) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PERMISSION_GRANTED) {

                        } else {
                            Toast.makeText(this, "" + "permission" + permissions[i] + "apply failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        }
    }


}
