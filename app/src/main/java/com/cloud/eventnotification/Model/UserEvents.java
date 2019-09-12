package com.cloud.eventnotification.Model;

import java.util.Date;

public class UserEvents {

    private String ID;
    private String title;
    private Date stime;
    private String location;

    public UserEvents(String ID, String title, Date stime, String location) {
        this.ID = ID;
        this.title = title;
        this.stime = stime;
        this.location = location;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public Date getStime() {
        return stime;
    }

    public String getLocation() {
        return location;
    }
}
