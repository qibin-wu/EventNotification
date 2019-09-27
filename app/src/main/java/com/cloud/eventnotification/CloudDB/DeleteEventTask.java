package com.cloud.eventnotification.CloudDB;

import android.os.AsyncTask;

import com.cloud.eventnotification.Model.UserEvents;

public class DeleteEventTask extends AsyncTask<Void, Void, Void> {
        private  UserEvents userEvents;

    public DeleteEventTask(UserEvents userEvents)
    {
        this.userEvents=userEvents;
    }

    @Override
    protected Void doInBackground(Void... users) {
        RDS.deleteEvent(userEvents);
        return null;
    }
}
