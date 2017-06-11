package com.example.deepak.socialnetworkingapp.NotificationActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.deepak.socialnetworkingapp.NotificationActivity.Notification_fragment.OnListFragmentInteractionListener;
import com.example.deepak.socialnetworkingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class notification_adapter extends RecyclerView.Adapter<notification_adapter.MyViewHolder> {


    private List<notificationObject> notificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_text,notification_profile_name;
        public de.hdodenhof.circleimageview.CircleImageView notification_image;

        public MyViewHolder(View view) {
            super(view);
            notification_text = (TextView) view.findViewById( R.id.notification_text);
            notification_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.notification_pic);
            notification_profile_name = (TextView) view.findViewById(R.id.notification_profilename);
        }
    }

    public notification_adapter(List<notificationObject> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public notification_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);

        return new notification_adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(notification_adapter.MyViewHolder holder, int position) {

        notificationObject notification = notificationList.get(position);
        //Log.e("comment ",comment.getComment());
        int type = Integer.valueOf(  notification.getNotification_type());
        if(type==0)
        {
            holder.notification_text.setText("Commented on your Post");
        }
        else if(type==1)
        {
            holder.notification_text.setText("Liked on your Post");
        }
        holder.notification_profile_name.setText(notification.getName());
        Picasso.with(holder.notification_image.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+notification.getProfile())
                .into(holder.notification_image);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }




}
