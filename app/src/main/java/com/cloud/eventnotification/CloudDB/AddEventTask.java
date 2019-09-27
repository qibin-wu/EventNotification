package com.cloud.eventnotification.CloudDB;

import android.app.Activity;
import android.os.AsyncTask;

import com.cloud.eventnotification.Model.UserEvents;

import java.util.ArrayList;

public class AddEventTask extends AsyncTask<Void, Void, Void> {
    private ArrayList<UserEvents> userEvents;

    public AddEventTask( ArrayList<UserEvents> userEvents)
    {
        this.userEvents=userEvents;
    }

    @Override
    protected Void doInBackground(Void... users) {
        RDS.addEvents(userEvents);
        return null;
    }




}
