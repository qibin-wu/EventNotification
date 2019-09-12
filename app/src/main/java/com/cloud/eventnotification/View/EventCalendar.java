package com.cloud.eventnotification.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.eventnotification.R;
import com.cloud.eventnotification.controller.NoEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EventCalendar extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button next;
    private Button back;
    private TextView date;
    private Date firstDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        // set last sunday of current day as default day
        Calendar cal = Calendar.getInstance();
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH, -weekday + 1);
        firstDate = cal.getTime();


        //match widget
        date = findViewById(R.id.currentDate);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(firstDate);
                c.add(Calendar.DAY_OF_MONTH, 7);
                firstDate = c.getTime();
                setDay();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(firstDate);
                c.add(Calendar.DAY_OF_MONTH, -7);
                firstDate = c.getTime();
                setDay();
            }
        });

        //  setting seven days
        setDay();



    }

    private void setDay() {

        //default seven days as blue background and no events
        btn1.setBackgroundColor(Color.parseColor("#0492EB"));
        btn2.setBackgroundColor(Color.parseColor("#0492EB"));
        btn3.setBackgroundColor(Color.parseColor("#0492EB"));
        btn4.setBackgroundColor(Color.parseColor("#0492EB"));
        btn5.setBackgroundColor(Color.parseColor("#0492EB"));
        btn6.setBackgroundColor(Color.parseColor("#0492EB"));
        btn7.setBackgroundColor(Color.parseColor("#0492EB"));
        btn1.setOnClickListener(new NoEventListener(this));
        btn2.setOnClickListener(new NoEventListener(this));
        btn3.setOnClickListener(new NoEventListener(this));
        btn4.setOnClickListener(new NoEventListener(this));
        btn5.setOnClickListener(new NoEventListener(this));
        btn6.setOnClickListener(new NoEventListener(this));
        btn7.setOnClickListener(new NoEventListener(this));


        SimpleDateFormat getMonthYear = new SimpleDateFormat("MM-yyyy");
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
        Calendar las = Calendar.getInstance();
        las.setTime(firstDate);
        las.add(Calendar.DAY_OF_MONTH, 6);
        Date lastDate = las.getTime();

        // if the week cross two years, display two years
        if (getYear.format(firstDate).compareTo(getYear.format(lastDate)) != 0) {
            date.setText(getMonthYear.format(firstDate) + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + getMonthYear.format(lastDate));
        } else
            //display the year and month
            date.setText(getMonthYear.format(firstDate));


        SimpleDateFormat getDay = new SimpleDateFormat("dd");
        final SimpleDateFormat getDate = new SimpleDateFormat("M-dd-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(firstDate);
        // set the day and long press listener
        btn1.setText(getDay.format(firstDate));

        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day2 = c.getTime();
        btn2.setText(getDay.format(day2));

        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day3 = c.getTime();
        btn3.setText(getDay.format(day3));

        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day4 = c.getTime();
        btn4.setText(getDay.format(day4));


        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day5 = c.getTime();
        btn5.setText(getDay.format(day5));

        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day6 = c.getTime();
        btn6.setText(getDay.format(day6));

        c.add(Calendar.DAY_OF_MONTH, 1);
        final Date day7 = c.getTime();
        btn7.setText(getDay.format(day7));



    }
}
