package com.example.deepak.socialnetworkingapp.MainActivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Post implements Parcelable{
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

    protected Post(Parcel in) {
        ProfileId = in.readInt();
        PostId = in.readInt();
        PostText = in.readString();
        PostImage = in.readString();
        PostTime = in.readString();
        ProfileImage = in.readString();
        ProfileName = in.readString();
        LikesNumber = in.readString();
        CommentsNumber = in.readString();
        IsLiked = in.readByte() != 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post( in );
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt( ProfileId );
        dest.writeInt( PostId );
        dest.writeString( PostText );
        dest.writeString( PostImage );
        dest.writeString( PostTime );
        dest.writeString( ProfileImage );
        dest.writeString( ProfileName );
        dest.writeString( LikesNumber );
        dest.writeString( CommentsNumber );
        dest.writeByte( (byte) (IsLiked ? 1 : 0) );
    }
}
