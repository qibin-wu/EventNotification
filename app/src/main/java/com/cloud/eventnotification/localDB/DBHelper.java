package com.cloud.eventnotification.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cloud.eventnotification.Model.UserEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create all tables
        String CREATE_TABLE_Event = "CREATE TABLE Event( ID varchar(100) PRIMARY KEY, title varchar(20),stime varchar(50),etime varchar(50),Location varchar(100))";
        db.execSQL(CREATE_TABLE_Event);
        String CREATE_TABLE_UserSetting = "CREATE TABLE UserSetting( name varchar(20) PRIMARY KEY,Period int,Threshold int,Remind int, mode varchar(20))";
        db.execSQL(CREATE_TABLE_UserSetting);
        String CREATE_TABLE_RemindAgain = "CREATE TABLE Remind( eventID varchar(10) PRIMARY KEY,RemindTime varchar(50))";
        db.execSQL(CREATE_TABLE_RemindAgain);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<UserEvents> selectEvent() {
        ArrayList<UserEvents> events = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Event", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String Title = cursor.getString(1);
                String stime = cursor.getString(2);
                String etime = cursor.getString(3);
                String location = cursor.getString(4);
                try {
                    Date sTime = sdf.parse(stime);
                    Date eTime = sdf.parse(etime);
                    UserEvents newEvent = new UserEvents(id, Title, sTime,eTime,location);
                    events.add(newEvent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();


        return events;

    }

    public void addEvent(UserEvents tempEvent) {

        if(checkEventExist(tempEvent))
        {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", tempEvent.getID());
        values.put("title", tempEvent.getTitle());
        values.put("stime", sdf.format(tempEvent.getsTime()));
        values.put("etime", sdf.format(tempEvent.geteTime()));
         values.put("Location", tempEvent.getLocation());
        db.insert("Event", null, values);


    }

    private boolean checkEventExist(UserEvents tempEvent) {
        boolean check =false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("Event", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                if(id.compareTo(tempEvent.getID())==0)
                {
                        check=true;
                        break;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();


        return check;
    }
}
