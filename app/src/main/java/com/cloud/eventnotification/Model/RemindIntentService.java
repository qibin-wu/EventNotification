package com.cloud.eventnotification.Model;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.cloud.eventnotification.CloudDB.RDS;
import com.cloud.eventnotification.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RemindIntentService extends IntentService {
  private  ArrayList<UserEvents> events;
  private int  Threshold;
    private  final  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");



    public RemindIntentService() {
        super("RemindIntentService");
        events = new ArrayList<>();
        Threshold=60;
    }



    @Override
    protected void onHandleIntent(Intent intent) {




        // get event
      events= RDS.selectEvents(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID)+"");

        // get threshold
        Threshold=RDS.getTh(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID)+"");

       DistanceJson dj =new DistanceJson();


       for( int i= 0;i<events.size();i++) {



            // Calculate How long does the event start?
           Date sysD= new Date(System.currentTimeMillis());
           long diff = events.get(i).getsTime().getTime()-System.currentTimeMillis() ;
           if (events.get(i).getsTime().before(sysD))
           {
               continue;
           }
           diff = diff/60000;
           int  diffMin = (int) diff;

            // get the drive time by distance matrix
           int driveTime=dj.getRequireTime(events.get(i).getLocation(),this);

           // if is time to notify, post a notification
           if((driveTime+Threshold)>=diffMin){

               Intent intentDismiss = new Intent(this, NotificationBroadcastReceiver.class);
               Intent intentCancel = new Intent(this, NotificationBroadcastReceiver.class);
               Intent intentReminder = new Intent(this, NotificationBroadcastReceiver.class);


               intentDismiss.putExtra("action", "Direction"+events.get(i).getLocation());
               intentCancel.putExtra("action", "Cancel"+events.get(i).getTitle()+";"+Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID));
               intentReminder.putExtra("action", "Reminder");

               PendingIntent pIntentDismiss = PendingIntent.getBroadcast(this, 44, intentDismiss, PendingIntent.FLAG_ONE_SHOT);
               PendingIntent pIntentCancel = PendingIntent.getBroadcast(this, 99, intentCancel, PendingIntent.FLAG_ONE_SHOT);
               PendingIntent pIntentReminder = PendingIntent.getBroadcast(this, 188, intentReminder, PendingIntent.FLAG_ONE_SHOT);
               NotificationManager notificationManager = (NotificationManager) getSystemService
                       (NOTIFICATION_SERVICE);
               NotificationCompat.Builder drivingNotifBldr;
               drivingNotifBldr = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                       .setSmallIcon(R.drawable.ic_launcher_foreground)
                       .setContentTitle(events.get(i).getTitle())
                       .setContentText("Est drive time:"+driveTime)

                       .addAction(R.drawable.ic_launcher_foreground, "Direction", pIntentDismiss)
                       .addAction(R.drawable.ic_launcher_foreground, "Cancel", pIntentCancel)
                       .addAction(R.drawable.ic_launcher_foreground, "Reminder", pIntentReminder)
               ;

               notificationManager.notify(0, drivingNotifBldr.build());

           }




       }







    }
}
