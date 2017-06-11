package com.example.deepak.socialnetworkingapp.MainActivity;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class PostLoader extends AsyncTaskLoader<List<Post>> {
    public String type;

    public PostLoader(Context context,String[] params)
    {
        super(context);
        type = params[0];
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Post> loadInBackground() {

        String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

        List<Post> posts = null;

        try {
            URL url = new URL(con_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


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

//            posts = parseResult(result);


//            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return posts;
    }

    private List<Post> parseResult(String result) {
        List<Post> postList = null;
        Post post = null;
        try {
            JSONObject reader = new JSONObject(result);

            post.setProfileId(Integer.parseInt(reader.getString("id")));
            post.setPostImage(reader.getString("post_image"));
            post.setPostText( reader.getString("post_text") );
            post.setProfileId( Integer.parseInt( reader.getString( "profile_id" ) ) );
            post.setPostTime(reader.getString( "post_time" ));
            postList.add(post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postList;
    }
}
