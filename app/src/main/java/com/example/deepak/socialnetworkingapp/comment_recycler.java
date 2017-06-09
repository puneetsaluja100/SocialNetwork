package com.example.deepak.socialnetworkingapp;

/**
 * Created by admin on 6/9/2017.
 */

public class comment_recycler {

    String comment,image;

    public comment_recycler(String comment, String image) {
        this.comment = comment;
        this.image = image;
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
