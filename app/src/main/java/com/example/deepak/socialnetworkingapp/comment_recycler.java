package com.example.deepak.socialnetworkingapp;

/**
 * Created by admin on 6/9/2017.
 */

public class comment_recycler {

    String comment,image,profilename;

    public comment_recycler(){}

    public comment_recycler(String comment, String image,String profilename) {
        this.comment = comment;
        this.image = image;
        this.profilename = profilename;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
