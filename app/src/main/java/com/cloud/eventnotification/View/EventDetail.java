package com.cloud.eventnotification.View;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.eventnotification.CloudDB.DeleteEventTask;
import com.cloud.eventnotification.CloudDB.RDS;
import com.cloud.eventnotification.CloudDB.ReadEventsTask;
import com.cloud.eventnotification.Model.UserEvents;
import com.cloud.eventnotification.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventDetail extends AppCompatActivity {
    private static ArrayList<UserEvents> eventList = new ArrayList<>();


    private static UserEvents thisEvent=null;
    private TextView title;
    private TextView sDate;
    private TextView eDate;
    private TextView Location;
    private Button map;
    private Button cancel;
    private Button reff;
    private Context context=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss aa");

        // get current event
        this.thisEvent = locatEvent();


        //match widget
        this.title = findViewById(R.id.eTitle);
        this.sDate = findViewById(R.id.eSdate);
        this.Location = findViewById(R.id.eLocation);
        this.map=findViewById(R.id.btnShow);
        this.eDate=findViewById(R.id.tEdate);
        this.cancel=findViewById(R.id.btnDelete);


        if(thisEvent!=null) {
            //display  event detail
            this.title.setText(thisEvent.getTitle());
            this.sDate.setText(sdf.format(thisEvent.getsTime()));
            this.Location.setText(thisEvent.getLocation());
            this.eDate.setText(sdf.format(thisEvent.geteTime()));
            this.map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callMap();

                }
            });
            this.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelRemind();
                }
            });

        }
        else {
            this.title.setText("Waiting for Database");
            this.sDate.setText("Waiting for Database");
            this.Location.setText("Waiting for Database");
            this.eDate.setText("Waiting for Database");
        }

        reff=findViewById(R.id.btnReff);
        reff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Intent ref = new Intent();
                ref.setClass(EventDetail.this, EventDetail.class);
                ref.putExtra("eventName", intent.getStringExtra("eventName"));
                EventDetail.this.startActivity(ref);
            }
        });

    }

    private void cancelRemind() {

        new DeleteEventTask(thisEvent).execute();
        Intent back = new Intent();
        back.setClass(this, MainActivity.class);
        this.startActivity(back);

    }

    //setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        MenuItem home = menu.add(0, 17, 0, "");
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


        }
        return true;
    }

    //get current event
    private UserEvents locatEvent() {
        UserEvents event = null;
        Intent intent = getIntent();

        new Thread(new Runnable(){
            public void run(){

                eventList = RDS.selectEvents(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID));

            }
        }).start();

        for (int i = 0; i < eventList.size(); i++) {
            //find the event in event list
            if (eventList.get(i).getTitle().compareTo(intent.getStringExtra("eventName")) == 0)
                event = eventList.get(i);
        }
        return event;
    }
    private void callMap(){

        if (isAvilible(context, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+ thisEvent.getLocation()+"");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "you haven't install google map", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);

    }


}
