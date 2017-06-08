package com.example.deepak.socialnetworkingapp;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mPostText;
        public ImageView mPostImage;

        public MyViewHolder(View view) {
            super(view);
            mPostText = (TextView)view.findViewById(R.id.post_text);
            mPostImage = (ImageView)view.findViewById(R.id.iv_post_image);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post post = PostList.get(position);
        holder.mPostText.setText(post.getPostText());
        //TODO(1): ADD SOMETHING FOR THE IMAGE
        Picasso.with(holder.mPostImage.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getPostImage())
                .into(holder.mPostImage);

    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }
}

