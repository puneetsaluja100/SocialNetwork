package com.example.deepak.socialnetworkingapp.messages;

/**
 * Created by admin on 6/13/2017.
 */

public class message_recycler {
    //String message;
    String image;
    String profilename;
    int Uid;
    int messageFrom;

    public int getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(int messageFrom) {
        this.messageFrom = messageFrom;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }



    public message_recycler(){}

    public message_recycler(String message, String image, String profilename) {
        //this.message = message;
        this.image = image;
        this.profilename = profilename;
    }

//    public String getMessage() {
//        return message;
//    }

//   // public void setMessage(String message) {
//        this.message = message;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }
}
