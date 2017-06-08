package com.example.deepak.socialnetworkingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostAdapter mAdapter;
    private String email;
    private TextView Useremail;
    public String FetchData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //trying to display email
//        email = getIntent().getExtras().getString("email");
//        Log.e("email",email);

        //navigation Drawer Activity to make the activity
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        View header = navigationView.getHeaderView(0);
//        Useremail = (TextView)header.findViewById(R.id.user_email);
//        Useremail.setText(email);

        //To create the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_post);
        preparePostData();

//        preparePostData();

    }


    private void preparePostData() {
        //Function to populate the post list
        MysqlCon mysqlCon = new MysqlCon();
        mysqlCon.execute(new String[] {"post"});
    }

    public List<Post> parseResult(String result) {
        // Function to take json as string and parse it into the post list
        ArrayList<Post> postList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            for (int i = 0; i < jsonArray.length(); i++) {
                Post post = new Post();
                JSONObject reader = jsonArray.getJSONObject(i);
                Log.i("Json",reader.toString());
                String read = reader.toString();
                post.setProfileId(Integer.parseInt(reader.getString("id")));
                Log.i( "Json", String.valueOf( post.getProfileId() ) );
                post.setPostImage(reader.getString("post_image"));
                post.setPostText( reader.getString("post_text") );
                post.setProfileId( Integer.parseInt( reader.getString( "profile_id" ) ) );
                post.setPostTime(reader.getString( "post_time" ));
//                post.setProfileImage(reader.getString("profile_image"));
//                post.setProfileName(reader.getString( "profile_name" ));
                postList.add(i,post);
                Log.i( "Json post list", postList.get(i).getPostText() );
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e( "jsonerror","Error in parsing json" );
        }
        for(int i =0;i<postList.size();i++){
            Log.i("Json post list",postList.get(i).getPostText());
        }
        return postList;
    }

    //Functions for navigation drawer activity
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
//            Intent intent = new Intent(MainActivity.this,upload.class);
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            drawer.closeDrawer(GravityCompat.START);
//            startActivity(intent);
            // Handle the camera action

            startActivity(new Intent(MainActivity.this,upload.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }

    //Code for loader
//            LoaderManager.LoaderCallbacks<List<Post>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Post>>() {
//            @Override
//            public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
//
//                PostLoader loader = new PostLoader(getApplicationContext(),new String[] {"post"});
//                return loader;
//            }
//
//            @Override
//            public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
//                mAdapter
//            }
//
//            @Override
//            public void onLoaderReset(Loader<List<Post>> loader) {
//                mAdapter.swapdata(null);
//            }
//        }


    //Asynk Task to load post list from the internet
    class MysqlCon extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            Log.i("Asynk Task","Asynk task is executing");
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

//            loninterval = System.currentTimeMillis() + 2000;
//            while(System.currentTimeMillis()>interval);g

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
            FetchData = s;
            String result = "{\"posts\":"+FetchData+"}";
            Log.i( "Json",result);
            postList = parseResult(result);
            mAdapter = new PostAdapter(postList);
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
}
