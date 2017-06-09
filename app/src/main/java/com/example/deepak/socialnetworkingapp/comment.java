package com.example.deepak.socialnetworkingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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

public class comment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private comment_adapter mAdapter;
    private ImageView send_comment;
    private EditText et_comment_text;
    public int Uid;
    public int postId;
    private String FetchData;
    private ArrayList<comment_recycler> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Uid = getIntent().getExtras().getInt("Uid");
        postId = getIntent().getExtras().getInt("postId");

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        prepareCommentData();
        send_comment = (ImageView) findViewById(R.id.send_comment);
        et_comment_text = (EditText) findViewById(R.id.et_commenttext);

        send_comment.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Toast.makeText(getApplicationContext(),"Commenting Please wait",LENGTH_SHORT).show();
                et_comment_text.clearFocus();
                MysqlCon mysqlCon = new MysqlCon();
                mysqlCon.execute("comment",et_comment_text.getText().toString(),String.valueOf(postId),String.valueOf(Uid));
            }});
    }

    private void prepareCommentData() {
//        comment_recycler acomment = new comment_recycler("Hello my name is puneet ","/upload/puneetsaluja@gmail.com/profile.png");
//        Log.e("comment ",acomment.getComment());
//        commentList.add(acomment);
        MysqlConCommentShow mysqlConCommentShow = new MysqlConCommentShow();
        mysqlConCommentShow.execute("getPostComments", String.valueOf(postId));
    }

    class MysqlCon extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String commenttext  = params[1];
            String postId = params[2];
            String Uid = params[3];

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream  = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data= URLEncoder.encode("comment","UTF-8")+"="+ URLEncoder.encode(commenttext,"UTF-8")+"&"
                        +URLEncoder.encode("postId","UTF-8")+"="+ URLEncoder.encode(postId,"UTF-8")+"&"
                        + URLEncoder.encode("Uid","UTF-8")+"="+ URLEncoder.encode(Uid,"UTF-8");


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
            Log.e("Result",s);
            prepareCommentData();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {

        }
    }

    class MysqlConCommentShow extends AsyncTask<String,String,String>
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

                    String post_data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");


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

                Log.e("Result", s);
                FetchData = s;
                String result = "{\"comments\":" + FetchData + "}";
                Log.i("Json", result);
                commentList = parseCommentResult(result);
                mAdapter = new comment_adapter(commentList);
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

    private ArrayList<comment_recycler> parseCommentResult(String result) {
        ArrayList<comment_recycler> commentList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("comments");
            for (int i = 0; i < jsonArray.length(); i++) {
                comment_recycler comment = new comment_recycler();
                JSONObject reader = jsonArray.getJSONObject(i);
                Log.i("Json",reader.toString());
                comment.setComment(reader.getString("comment_text"));
                comment.setImage(reader.getString("profile"));
                comment.setProfilename(reader.getString("name"));
                commentList.add(i,comment);
                Log.i( "Json post list", commentList.get(i).getComment() );
            }

        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e( "jsonerror","Error in parsing json" );
        }

        return commentList;
    }
}

