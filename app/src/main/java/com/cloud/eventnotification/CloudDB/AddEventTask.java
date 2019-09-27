package com.cloud.eventnotification.CloudDB;

import android.app.Activity;
import android.os.AsyncTask;

import com.cloud.eventnotification.Model.UserEvents;

public class AddEventTask extends AsyncTask<Void, Void, Void> {
    private UserEvents UserEvents;

    public AddEventTask(UserEvents UserEvents)
    {
        this.UserEvents=UserEvents;
    }

    @Override
    protected Void doInBackground(Void... users) {
        RDS.addEvent(UserEvents);
        return null;
    }




}
