package com.cloud.eventnotification.controller;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.cloud.eventnotification.Model.eventItem;
import com.cloud.eventnotification.View.EventDetail;
import com.cloud.eventnotification.View.MainActivity;

public class ItemClickListener implements AdapterView.OnItemClickListener {

    private MainActivity ma;
    private  eventItem[] items;

    public ItemClickListener(MainActivity ma, eventItem[] items) {
        this.ma = ma;
        this.items = items;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // get the title of the clicked event
        String text = items[position].getTitle();


        // go to the event detail
        Intent intent = new Intent();
        intent.setClass(ma, EventDetail.class);
        intent.putExtra("eventName", text);
        ma.startActivity(intent);


    }


}
