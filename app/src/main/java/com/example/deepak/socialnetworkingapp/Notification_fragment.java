package com.example.deepak.socialnetworkingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;


public class Notification_fragment extends AppCompatActivity {

    private int Uid;
    private RecyclerView recyclerView;
    private notification_adapter mAdapter;
    private ArrayList<notificationObject> notificationList = new ArrayList<>();
    private String FetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.notification_outer);
        Uid = 2027;
        Log.e( "User Id",String.valueOf( Uid )+"Yarr ye hai" );
        recyclerView = (RecyclerView) findViewById( R.id.notification_recycler_view );
        prepareNotificationData();
        }

    private void prepareNotificationData() {
        MysqlConNotificationShow mysqlConNotificationShow = new MysqlConNotificationShow();
        Log.e( "User Id",String.valueOf( Uid )+"Yarr ye hai" );
        mysqlConNotificationShow.execute("getNotifications",String.valueOf( Uid ) );
    }

    class MysqlConNotificationShow extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String type = params[0]; //getPostComments
            Log.i("Asynk Task","Asynk task is executing");
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String Uid = params[1];
            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Uid, "UTF-8");


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
            //showProgress( false );
            Log.e("Result", s);
            FetchData = s;
            String result = "{\"notifications\":" + FetchData + "}";
            Log.i("Json", result);
            notificationList = parseNotificationResult(result);
            mAdapter = new notification_adapter(notificationList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
        }
    }

    private ArrayList<notificationObject> parseNotificationResult(String result) {
        ArrayList<notificationObject> notificationList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("notifications");
            for (int i = 0; i < jsonArray.length(); i++) {
                notificationObject notification = new notificationObject();
                JSONObject reader = jsonArray.getJSONObject(i);
                Log.i("Json",reader.toString());
                notification.setName(reader.getString("name"));
                notification.setProfile(reader.getString("profile"));
                notification.setNotification_type(reader.getString("notification_type"));
                notification.setNotification_state(reader.getString("notification_state"));
                notificationList.add(i,notification);
                Log.i( "Json post list", notificationList.get(i).getName() );
            }

        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e( "jsonerror","Error in parsing json" );
        }

        return notificationList;
    }
}
