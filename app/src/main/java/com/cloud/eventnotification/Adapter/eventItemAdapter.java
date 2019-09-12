package com.cloud.eventnotification.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cloud.eventnotification.Model.eventItem;
import com.cloud.eventnotification.R;

public class eventItemAdapter extends ArrayAdapter< eventItem> {
    private Context context;
    private eventItem[] items;


    public eventItemAdapter(Context context, eventItem[] items) {
        super(context,0,items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        eventItem item = items[position];

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        }

        TextView eventTxt = convertView.findViewById(R.id.textEvent);
        TextView SdateTxt = convertView.findViewById(R.id.textSdate);

        eventTxt.setText(item.getTitle());

        SdateTxt.setText(item.getsTime());

        return convertView;
    }
}
