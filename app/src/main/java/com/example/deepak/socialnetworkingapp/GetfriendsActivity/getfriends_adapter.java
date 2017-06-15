package com.example.deepak.socialnetworkingapp.GetfriendsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.deepak.socialnetworkingapp.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by OMPRAKASH on 6/10/2017.
 */

public class getfriends_adapter extends RecyclerView.Adapter<getfriends_adapter.MyViewHolder> {

    private  List<getfriends_recycler> getfriendsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button set_friends;
        public TextView getfriend_profile_name;
        public de.hdodenhof.circleimageview.CircleImageView friends_profilepic;

        public MyViewHolder(View view) {
            super(view);
            getfriend_profile_name = (TextView) view.findViewById( R.id.friends_profilename);
            friends_profilepic = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.friends_pic);
            set_friends = (Button)view.findViewById(R.id.set_friends);
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

        final getfriends_recycler getfriends  = getfriendsList.get(position);
        Log.e("getfriends ",getfriends.getProfilename());


        holder.getfriend_profile_name.setText(getfriends.getProfilename());
        Picasso.with(holder.friends_profilepic.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+getfriends.getProfileimage())
                .into(holder.friends_profilepic);

        holder.set_friends.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                //Toast.makeText(getApplicationContext(),"user adding Please wait",LENGTH_SHORT).show();
                //et_getfriends_text.clearFocus();
                MysqlConsetgroup mysqlConshow = new MysqlConsetgroup();
                mysqlConshow.execute("adduser",String.valueOf(getfriends.getUid()),String.valueOf(getfriends.getGroup_id()));
     //           MysqlCongetfriendsShow.execute("getfriends",String.valueOf(getfriends.Uid),String.valueOf(getfriends.group_id));
                //new getfriends().preparegetfriendsData()


            }});
    }

    @Override
    public int getItemCount() {
        return getfriendsList.size();
    }

    class MysqlConsetgroup extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String Uid = params[1];
            String group_id = params[2];

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream  = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data= URLEncoder.encode("Uid","UTF-8")+"="+ URLEncoder.encode(Uid,"UTF-8")+"&"+
                        URLEncoder.encode("group_id","UTF-8")+"="+ URLEncoder.encode(group_id,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream  = httpURLConnection.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
                String result="";
                String line;

                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s+"PHp hai");


//            showProgress( false );
           /**FetchData = s;
            String result = "{\"friends\":" + FetchData + "}";
            Log.i("Json", result);
           /* getfriendsList = parsegetfriendsResult(result);
            mAdapter = new getfriends_adapter(getfriendsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);*/

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {

        }
    }





}

