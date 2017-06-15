package com.example.deepak.socialnetworkingapp.conversation;

/**
 * Created by admin on 6/13/2017.
 */

public class conversation_recycler {
    String conversation_message,image,profilename;

    public conversation_recycler(){

    }

    public conversation_recycler(String conversation_message, String image, String profilename) {
        this.conversation_message = conversation_message;
        this.image = image;
        this.profilename = profilename;
    }

    public String getConversation_message() {
        return conversation_message;
    }

    public void setConversation_message(String conversation_message) {
        this.conversation_message = conversation_message;
    }

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
