package com.example.deepak.socialnetworkingapp;

/**
 * Created by Deepak on 6/4/2017.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
class MysqlCon extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... strings) {
        String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/connection.php";

        try {
            URL url = new URL(con_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
            String result = "";
            String line;



            while((line = bufferedReader.readLine())!=null){
                result+=line;
            }
            bufferedReader.close();
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
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
