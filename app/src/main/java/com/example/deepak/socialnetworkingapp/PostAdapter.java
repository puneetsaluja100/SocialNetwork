package com.example.deepak.socialnetworkingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>  {
    private List<Post> PostList;

    public int postId;   public Button CommentButton,ShareButton;
    public int Uid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName;
        public ImageView profilePicture;
        public TextView mPostText;
        public ImageView mPostImage;
        public Button LikeButton;



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


    public PostAdapter(List<Post> PostList,int uid) {
        this.PostList = PostList;
        this.Uid = uid;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fragment_picture_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
        final Post post = PostList.get(position);
        holder.mPostText.setText(post.getPostText());
        Picasso.with(holder.mPostImage.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getPostImage())
                .into(holder.mPostImage);
        holder.profileName.setText(post.getProfileName());
        Picasso.with(holder.profilePicture.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getProfileImage())
                .into(holder.profilePicture);
        holder.LikeButton.setTextColor( Color.BLACK );

        holder.LikeButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postId = post.getPostId();
                Log.e("The post id clicked is ", String.valueOf( postId ) );
                Toast.makeText( holder.LikeButton.getContext(),"You liked this post",Toast.LENGTH_SHORT ).show();
                setColorLikeButton(holder.LikeButton);
            }
        });

        CommentButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                postId = post.getPostId();
//                Log.e("The post id clicked is ", String.valueOf( postId ) );
                Intent intent = new Intent(CommentButton.getContext(),comment.class);
                intent.putExtra("Uid",Uid);
                intent.putExtra("postId",post.getPostId());
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

    private void setColorLikeButton(Button LikeButton) {
        ColorStateList mList = LikeButton.getTextColors();
        int color = mList.getDefaultColor();
        Log.e( "Color", String.valueOf( color ) );
        Log.e( "Color", String.valueOf(Color.BLACK) );

        switch(color)
        {
            case Color.RED:
                LikeButton.setTextColor(Color.BLACK);
                LikeButton.setDrawingCacheBackgroundColor(Color.BLACK);
                break;

            case Color.BLACK:
                Log.e( "Color","Inside when color is black" );
                LikeButton.setTextColor( Color.RED);

                break;

        }
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }
}


