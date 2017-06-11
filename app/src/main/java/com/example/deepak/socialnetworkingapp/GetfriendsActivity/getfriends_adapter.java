package com.example.deepak.socialnetworkingapp.GetfriendsActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepak.socialnetworkingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by OMPRAKASH on 6/10/2017.
 */

public class getfriends_adapter extends RecyclerView.Adapter<getfriends_adapter.MyViewHolder> {

    private List<getfriends_recycler> getfriendsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView getfriend_profile_name;
        public de.hdodenhof.circleimageview.CircleImageView friends_profilepic;

        public MyViewHolder(View view) {
            super(view);
            getfriend_profile_name = (TextView) view.findViewById( R.id.friends_profilename);
            friends_profilepic = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.friends_pic);
        }
    }

    public getfriends_adapter(List<getfriends_recycler> getfriendsList){this.getfriendsList = getfriendsList;}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.getfriends_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        getfriends_recycler getfriends  = getfriendsList.get(position);
        Log.e("getfriends ",getfriends.getProfilename());


        holder.getfriend_profile_name.setText(getfriends.getProfilename());
        Picasso.with(holder.friends_profilepic.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+getfriends.getProfileimage())
                .into(holder.friends_profilepic);
    }

    @Override
    public int getItemCount() {
        return getfriendsList.size();
    }
}

