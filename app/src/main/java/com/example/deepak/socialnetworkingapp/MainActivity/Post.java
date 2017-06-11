package com.example.deepak.socialnetworkingapp.MainActivity;

public class Post {
    private int ProfileId;
    private int PostId;
    private String PostText;
    private String PostImage;
    private String PostTime;
    private String ProfileImage;
    private String ProfileName;
    private String LikesNumber;
    private String CommentsNumber;
    private boolean IsLiked;

    public Post(){
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

    public String getLikesNumber() {
        return LikesNumber;
    }

    public void setLikesNumber(String likesNumber) {
        LikesNumber = likesNumber;
    }

    public String getCommentsNumber() {
        return CommentsNumber;
    }

    public void setCommentsNumber(String commentsNumber) {
        CommentsNumber = commentsNumber;
    }

    public boolean isLiked() {
        return IsLiked;
    }

    public void setLiked(boolean liked) {
        IsLiked = liked;
    }
}
