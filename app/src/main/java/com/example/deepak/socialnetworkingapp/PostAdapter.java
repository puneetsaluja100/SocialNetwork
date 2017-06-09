package com.example.deepak.socialnetworkingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>  {
    private List<Post> PostList;
    public Button LikeButton;
    public Button CommentButton,ShareButton;
    public int postId;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName;
        public ImageView profilePicture;
        public TextView mPostText;
        public ImageView mPostImage;


        public MyViewHolder(View view) {
            super(view);
            mPostText = (TextView)view.findViewById(R.id.post_text);
            mPostImage = (ImageView)view.findViewById(R.id.iv_post_image);
            profileName = (TextView) view.findViewById( R.id.tv_profile_name );
            profilePicture = (ImageView) view.findViewById( R.id.ib_profile_picture );
            LikeButton = (Button)view.findViewById( R.id.like_button );
            CommentButton = (Button)view.findViewById( R.id.comment_button );
            ShareButton = (Button) view.findViewById( R.id.share_button );
        }
    }


    public PostAdapter(List<Post> PostList) {
        this.PostList = PostList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fragment_picture_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Post post = PostList.get(position);
        holder.mPostText.setText(post.getPostText());
        Picasso.with(holder.mPostImage.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getPostImage())
                .into(holder.mPostImage);
        holder.profileName.setText(post.getProfileName());
        Picasso.with(holder.profilePicture.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getProfileImage())
                .into(holder.profilePicture);

        LikeButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postId = post.getPostId();
                Log.e("The post id clicked is ", String.valueOf( postId ) );
            }
        });

        CommentButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                postId = post.getPostId();
//                Log.e("The post id clicked is ", String.valueOf( postId ) );
                Intent intent = new Intent(CommentButton.getContext(),comment.class);
                ((Activity)CommentButton.getContext()).startActivity(intent);

            }
        });
//
//        ShareButton.setOnClickListener( new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                postId = post.getPostId();
//                Log.e("The post id clicked is ", String.valueOf( postId ) );
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }
}

