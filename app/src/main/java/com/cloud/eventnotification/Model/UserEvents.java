package com.cloud.eventnotification.Model;

import java.util.Date;

public class UserEvents {

    private String ID;
    private String title;
    private Date sTime;
    private Date eTime;
    private String location;
    private String Android_ID;

    public UserEvents(String ID, String title, Date stime,Date eTime, String location,String Android_ID) {
        this.ID = ID;
        this.title = title;
        this.sTime = stime;
        this.location = location;
        this.eTime=eTime;
        this.Android_ID=Android_ID;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public Date getsTime() {
        return sTime;
    }

    public String getLocation() {
        return location;
    }

    public String getAndroid_ID() {
        return Android_ID;
    }

    public Date geteTime() {
        return eTime;
    }
}
