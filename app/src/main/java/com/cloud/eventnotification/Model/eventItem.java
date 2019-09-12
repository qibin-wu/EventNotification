package com.cloud.eventnotification.Model;

public class eventItem {
    private String title;
    private String sTime;

    public eventItem(String title, String sTime) {
        this.title = title;
        this.sTime = sTime;
    }

    public String getTitle() {
        return title;
    }

    public String getsTime() {
        return sTime;
    }
}
