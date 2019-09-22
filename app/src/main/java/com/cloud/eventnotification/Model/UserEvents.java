package com.cloud.eventnotification.Model;

import java.util.Date;

public class UserEvents {

    private String ID;
    private String title;
    private Date sTime;
    private Date eTime;
    private String location;

    public UserEvents(String ID, String title, Date stime,Date eTime, String location) {
        this.ID = ID;
        this.title = title;
        this.sTime = stime;
        this.location = location;
        this.eTime=eTime;
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

    public Date geteTime() {
        return eTime;
    }
}
