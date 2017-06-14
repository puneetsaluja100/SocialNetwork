package com.example.deepak.socialnetworkingapp.messages;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepak.socialnetworkingapp.CommentActivity.comment;
import com.example.deepak.socialnetworkingapp.MainActivity.MainActivity;
import com.example.deepak.socialnetworkingapp.R;
import com.example.deepak.socialnetworkingapp.conversation.coversation;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class message_adapter extends RecyclerView.Adapter<message_adapter.MyViewHolder>   {

    private List<message_recycler> messageList;
    public int position_clicked;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message_text,message_profile_name;
        public de.hdodenhof.circleimageview.CircleImageView message_image;


        public MyViewHolder(View view) {
            super(view);
            //message_text = (TextView) view.findViewById( R.id.message_text);
            message_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.message_pic);
            message_profile_name = (TextView) view.findViewById(R.id.message_profilename);
            position_clicked = 0;
        }

    }

    public message_adapter(List<message_recycler> messageList) {
        this.messageList = messageList;
    }

    @Override
    public message_adapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_message, parent, false);
        return new message_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final message_adapter.MyViewHolder holder, final int position) {
        message_recycler message = messageList.get(position);
        //Log.e("message ",message.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = position ;
                    int Uid = messageList.get(i).getUid();
                    int Sid = messageList.get(i).getMessageFrom();
                    String profilename = messageList.get(i).getProfilename();
                    Log.i("hello",String.valueOf(Uid) + " "+ String.valueOf( Sid));
                Intent intent = new Intent(holder.itemView.getContext(),coversation.class);
                intent.putExtra("Uid",Uid);
                intent.putExtra("profilename",profilename);
                intent.putExtra("Sid",Sid);
                ((Activity)holder.itemView.getContext()).startActivity(intent);
                Toast.makeText(holder.itemView.getContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //holder.message_text.setText(message.getMessage());
        holder.message_profile_name.setText(message.getProfilename());
        Picasso.with(holder.message_image.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+message.getImage())
                .into(holder.message_image);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
