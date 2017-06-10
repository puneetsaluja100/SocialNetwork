package com.example.deepak.socialnetworkingapp;

/**
 * Created by Deepak Yadav on 6/10/2017.
 */

public class notificationObject {
    String name;
    String profile;
    String notification_type;
    String notification_state;

    public notificationObject(){

    }

    public notificationObject(String name, String profile, String notification_type, String notification_state) {
        this.name = name;
        this.profile = profile;
        this.notification_type = notification_type;
        this.notification_state = notification_state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getNotification_state() {
        return notification_state;
    }

    public void setNotification_state(String notification_state) {
        this.notification_state = notification_state;
    }
}
