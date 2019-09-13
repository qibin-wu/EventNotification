package com.cloud.eventnotification.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cloud.eventnotification.Model.UserEvents;
import com.cloud.eventnotification.R;
import com.cloud.eventnotification.localDB.DBHelper;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class EventDetail extends AppCompatActivity {
    private ArrayList<UserEvents> eventList = new ArrayList<>();


    private UserEvents thisEvent;
    private EditText title;
    private EditText sDate;
    private EditText Location;
    private MenuItem save;
    private boolean isDelete=false;
    private boolean isUpdate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss aa");

        // get current event
        this.thisEvent = locatEvent();


        //match widget
        this.title = findViewById(R.id.eTitle);
        title.setEnabled(false);
        this.sDate = findViewById(R.id.eSdate);
        sDate.setEnabled(false);
        this.Location = findViewById(R.id.eLocation);
        Location.setEnabled(false);

        //display  event detail
        this.title.setText(thisEvent.getTitle());
        this.sDate.setText(sdf.format(thisEvent.getStime()));
        this.Location.setText(thisEvent.getLocation());

    }

    //setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        MenuItem home = menu.add(0, 17, 0, "");
        MenuItem cal = menu.add(0, 18, 0, "");
        cal.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        cal.setIcon(R.drawable.calendar);
        home.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        home.setIcon(R.drawable.home);
        return super.onCreateOptionsMenu(menu);
    }

    //According to id do something
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
            //update event
            case 19:


                this.isUpdate=true;
                Intent i = new Intent();
                i.setClass(this, EventDetail.class);
                i.putExtra("eventName",getIntent().getStringExtra("eventName"));
                this.startActivity(i);
                this.finish();


                break;
        }
        return true;
    }

    //get current event
    private UserEvents locatEvent() {
        UserEvents event = null;
        Intent intent = getIntent();


        DBHelper dbHelper = new DBHelper(this, "eventn.db", null, 1);
        dbHelper.getWritableDatabase();
        this.eventList = dbHelper.selectEvent();

        for (int i = 0; i < eventList.size(); i++) {
            //find the event in event list
            if (eventList.get(i).getTitle().compareTo(intent.getStringExtra("eventName")) == 0)
                event = eventList.get(i);
        }
        return event;
    }


}
