package com.example.deepak.socialnetworkingapp;

/**
 * Created by OMPRAKASH on 6/10/2017.
 */


public class getfriends_recycler {

    String profileimage,profilename;

    public getfriends_recycler(){}

    public getfriends_recycler(String profileimage, String profilename) {
        this.profileimage = profileimage;
        this.profilename = profilename;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }
}
