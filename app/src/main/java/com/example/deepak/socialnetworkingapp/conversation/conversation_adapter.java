package com.example.deepak.socialnetworkingapp.conversation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deepak.socialnetworkingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class conversation_adapter extends RecyclerView.Adapter<conversation_adapter.MyViewHolder>{


    private List<conversation_recycler> conversationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView conversation_text,conversation_profile_name;
        public de.hdodenhof.circleimageview.CircleImageView conversation_image;
        public int position_clicked;
        public MyViewHolder(View view) {
            super(view);
            conversation_text = (TextView) view.findViewById( R.id.conversation_text1);
            conversation_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.conversation_pic);
            conversation_profile_name = (TextView) view.findViewById(R.id.conversation_profilename);
            position_clicked = 0;
        }
    }


    public conversation_adapter(List<conversation_recycler> conversationList) {
        this.conversationList = conversationList;
    }

    @Override
    public conversation_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_coversation, parent, false);

        return new conversation_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(conversation_adapter.MyViewHolder holder, int position) {
        holder.position_clicked = holder.getAdapterPosition();
        conversation_recycler conversation = conversationList.get(position);
        Log.e("conversation ",conversation.getConversation_message());

        holder.conversation_text.setText(conversation.getConversation_message());
        holder.conversation_profile_name.setText(conversation.getProfilename());
        Picasso.with(holder.conversation_image.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+conversation.getImage())
                .into(holder.conversation_image);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}
