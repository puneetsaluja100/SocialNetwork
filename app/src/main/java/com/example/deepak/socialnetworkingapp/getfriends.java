package com.example.deepak.socialnetworkingapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class getfriends extends AppCompatActivity {
    private RecyclerView recyclerView;
    private getfriends_adapter mAdapter;
    private Button set_friends;
    private EditText et_getfriends_text;
    public int Uid;
    public int Pid;
    public int postId;
    private String FetchData;
    private ArrayList<getfriends_recycler> getfriendsList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView progressText;
    //To show progress when the data is being fetched
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressText.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressText.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getfriends_recycler_outer);

        progressBar = (ProgressBar) findViewById( R.id.getfriends_progress );
        progressText = (TextView) findViewById( R.id.getfriends_progress_text );


        Uid = getIntent().getExtras().getInt("Uid");

        recyclerView = (RecyclerView) findViewById(R.id.getfriends_recyclerview);
        preparegetfriendsData();
        set_friends = (Button) findViewById(R.id.set_friends);

//        set_friends.setOnClickListener( new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                Toast.makeText(getApplicationContext(),"getfriendsing Please wait",LENGTH_SHORT).show();
//                et_getfriends_text.clearFocus();
//                MysqlCon mysqlCon = new MysqlCon();
//                mysqlCon.execute("getfriends",et_getfriends_text.getText().toString(),String.valueOf(postId),String.valueOf(Uid),String.valueOf(Pid));
//            }});
    }

    private void preparegetfriendsData() {
//        showProgress( true );
            Log.e("Asynk Task","Asynk task is loaded to load getfriendss");
            MysqlCongetfriendsShow mysqlCongetfriendsShow = new MysqlCongetfriendsShow();
            mysqlCongetfriendsShow.execute( "getfriends", String.valueOf( Uid ) );
    }

    //Asynk task to fetch the friends to a post
    class MysqlCongetfriendsShow extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String Uid = params[1];

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream  = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data= URLEncoder.encode("Uid","UTF-8")+"="+ URLEncoder.encode(Uid,"UTF-8");


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
            FetchData = s;
            String result = "{\"friends\":" + FetchData + "}";
            Log.i("Json", result);
            getfriendsList = parsegetfriendsResult(result);
            mAdapter = new getfriends_adapter(getfriendsList);
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



    private ArrayList<getfriends_recycler> parsegetfriendsResult(String result) {
        ArrayList<getfriends_recycler> getfriendsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("friends");
            for (int i = 0; i < jsonArray.length(); i++) {
                getfriends_recycler getfriends = new getfriends_recycler();
                JSONObject reader = jsonArray.getJSONObject(i);
                Log.i("Json",reader.toString());
                getfriends.setProfilename(reader.getString("name"));
                getfriends.setProfileimage(reader.getString("profile"));

                getfriendsList.add(i,getfriends);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e( "jsonerror","Error in parsing json" );
        }

        return getfriendsList;
    }
}

