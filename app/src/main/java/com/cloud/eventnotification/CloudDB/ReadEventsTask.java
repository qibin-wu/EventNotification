package com.cloud.eventnotification.CloudDB;

import android.os.AsyncTask;

import com.cloud.eventnotification.Model.UserEvents;

import java.util.ArrayList;

public class ReadEventsTask extends AsyncTask<Void, Void, ArrayList<UserEvents>> {
    private String A_ID;

    public ReadEventsTask(String A_ID) {
        this.A_ID=A_ID;
    }

    @Override
    protected ArrayList<UserEvents> doInBackground(Void... users) {



        return RDS.selectEvents(A_ID);
    }




}
