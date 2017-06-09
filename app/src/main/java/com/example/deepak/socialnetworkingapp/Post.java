package com.example.deepak.socialnetworkingapp;
import java.sql.Timestamp;

public class Post {
    private int ProfileId;
    private int PostId;
    private String PostText;
    private String PostImage;
    private String PostTime;
    private String ProfileImage;
    private String ProfileName;

    public Post(){
    }
    public Post(int profileId, int postId, String postText, String postImage, String postTime, String profileImage, String profileName) {
        ProfileId = profileId;
        PostId = postId;
        PostText = postText;
        PostImage = postImage;
        PostTime = postTime;
        ProfileImage = profileImage;
        ProfileName = profileName;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
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

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }
}
