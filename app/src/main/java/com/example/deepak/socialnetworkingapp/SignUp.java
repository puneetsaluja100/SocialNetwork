package com.example.deepak.socialnetworkingapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;

public class SignUp extends AppCompatActivity {


    private MysqlCon mAuthTask = null;

    private EditText mName,mDob,mEmail,mPassword;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = (EditText)findViewById(R.id.name);
        mDob = (EditText)findViewById(R.id.dob);
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        mSignUp = (Button) findViewById(R.id.email_sign_up_button);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

    }

    private void attemptRegister(){

        if (mAuthTask != null) {
            return;
        }

        mName.setError(null);
        mDob.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);


        String name = mName.getText().toString();
        String dob = mDob.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            mName.setError("Please enter your name");
            focusView = mName;
            cancel = true;
        }

        if(TextUtils.isEmpty(dob)){
            mDob.setError("Please Enter your DOB");
            focusView = mDob;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Please enter your Email");
            focusView = mEmail;
            cancel = true;
        }else if(!isEmailValid(email)){
            mEmail.setError("This email address is invalid");
            focusView = mEmail;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            mPassword.setError("Please enter your Password");
            focusView = mPassword;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else {
            MysqlCon mysqlCon = new MysqlCon();
            mysqlCon.execute("register", name, dob, email, password);
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }


    class MysqlCon extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";
            String name = params[1];
            String dob = params[2];
            String email = params[3];
            String password = params[4];

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream  = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(name,"UTF-8")+"&"
                        + URLEncoder.encode("dob","UTF-8")+"="+ URLEncoder.encode(dob,"UTF-8")+"&"
                        + URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")+"&"
                        + URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");


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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s);
            if(s.contains("Insertion Successful")){
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Registration Unsuccessful";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}

