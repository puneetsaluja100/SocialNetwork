package com.example.deepak.socialnetworkingapp.messages;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deepak.socialnetworkingapp.R;

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

public class message extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView progressText;
    private message_adapter mAdapter;
    private ArrayList<message_recycler> messageList = new ArrayList<>();
    private int Uid;
    String FetchData;

//    //To show progress when the data is being fetched
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    public void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressText.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressText.setVisibility(show ? View.VISIBLE : View.GONE);
//
//        }
//    }
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_outer);
        Uid = getIntent().getExtras().getInt("Uid");


        progressBar = (ProgressBar) findViewById( R.id.message_progress );
        progressText = (TextView) findViewById( R.id.message_progress_text );

        recyclerView = (RecyclerView) findViewById(R.id.message_recycler_view);
        prepareMessageData();
    }


    private void prepareMessageData() {
        //showProgress( true );
        if(messageList.size()==0) {
            Log.e("Asynk Task","Asynk task is loaded to load comments");
            message.MysqlConMessageShow mysqlConMessageShow = new message.MysqlConMessageShow();
            mysqlConMessageShow.execute( "getMessages", String.valueOf( Uid ) );
        }
        else{
            mAdapter = new message_adapter(messageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }


    class MysqlConMessageShow extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String type = params[0]; //getPostComments
            Log.i("Asynk Task","Asynk task is executing");
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String postId = params[1];
            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("Uid", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");


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
            String result = "{\"messages\":" + FetchData + "}";
            Log.i("Json", result);
            messageList = parseMessageResult(result);
            mAdapter = new message_adapter(messageList);
            Context context = getBaseContext();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
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

    private ArrayList<message_recycler> parseMessageResult(String result) {
        ArrayList<message_recycler> messageList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("messages");
            for (int i = 0; i < jsonArray.length(); i++) {
                message_recycler message = new message_recycler();
                JSONObject reader = jsonArray.getJSONObject(i);
                Log.i("Json",reader.toString());
                message.setImage(reader.getString("profile"));
                message.setProfilename(reader.getString("name"));
                message.setUid(Uid);
                message.setMessageFrom(reader.getInt("message_from"));
                messageList.add(i,message);
                //Log.i( "Json post list", messageList.get(i).getMessage() );
            }

        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e( "jsonerror","Error in parsing json" );
        }

        return messageList;
    }
}
