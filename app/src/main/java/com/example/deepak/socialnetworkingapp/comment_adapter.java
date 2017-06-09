package com.example.deepak.socialnetworkingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 6/9/2017.
 */

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.MyViewHolder>  {

    private List<comment_recycler> commentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comment_text;
        public de.hdodenhof.circleimageview.CircleImageView comment_image;

        public MyViewHolder(View view) {
            super(view);
            comment_text = (TextView) view.findViewById(R.id.commenttext);
            comment_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.comment_pic);
        }
    }


    public comment_adapter(List<comment_recycler> commentList) {
        this.commentList = commentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        comment_recycler comment = commentList.get(position);
        holder.comment_text.setText(comment.getComment());
        Picasso.with(holder.comment_image.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+comment.getImage())
                .into(holder.comment_image);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}
