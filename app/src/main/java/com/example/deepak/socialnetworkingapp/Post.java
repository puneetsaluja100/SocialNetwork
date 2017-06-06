package com.example.deepak.socialnetworkingapp;


import java.sql.Timestamp;

public class Post {
    private int ProfileId;
    private String PostText;
    private String PostImage;
    private String PostTime;

    public Post(int profileId, String postText, String postImage, String postTime) {
        ProfileId = profileId;
        PostText = postText;
        PostImage = postImage;
        PostTime = postTime;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public String getPostText() {
        return PostText;
    }

    public void setPostText(String postText) {
        PostText = postText;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }
}
