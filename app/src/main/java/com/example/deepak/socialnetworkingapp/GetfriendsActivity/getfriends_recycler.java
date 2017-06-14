package com.example.deepak.socialnetworkingapp.GetfriendsActivity;

/**
 * Created by OMPRAKASH on 6/10/2017.
 */


public class getfriends_recycler {

    String profileimage;
    String profilename;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    String group_id;
    int Uid;

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
