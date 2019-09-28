package com.cloud.eventnotification.Model;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.cloud.eventnotification.CloudDB.RDS;
import com.cloud.eventnotification.View.Direction;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationBroadcastReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {



        String action=intent.getStringExtra("action");
        if(action.contains("Direction")){
            action=action.replace("Direction","");
            performAction1(context,action);
        }
        else if(action.contains("Cancel")){

            action=action.replace("Cancel","");
            performAction2(context,action);

        }
        else if(action.contains("Reminder")){
            action=action.replace("Reminder","");
            performAction3(context,action);

        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public void performAction1(Context context,String location){


        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, Direction.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        trIntent.putExtra("location", location);
        context.startActivity(trIntent);
    }

    public void performAction2(final Context context, final String titleaid){

        String substring[] = titleaid.split(";");
        String title=substring[0];
        String AID=substring[1];
        RDS.deleteEvent(title,AID);


    }
    public void performAction3(Context context,String evenID){


        NotificationManager notificationManager = ( NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);


    }

}
