package com.example.deepak.socialnetworkingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostAdapter mAdapter;
    private String email;
    private TextView Useremail;
    private TextView Username;
    private ImageView UserImage;


    public String FetchData;
    public String Uname;
    public String Uprofilepicture;

    //new uploading post
    private ImageView mimageUpload;
    private EditText mstatusUpload;
    private Button mpostUpload;
    public String type;


    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";

    private String UPLOAD_URL = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/uploadPost.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private Bitmap bitmap;
    private String imageName;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getExtras().getString("email");


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
        View header = navigationView.getHeaderView(0);
        Useremail = (TextView)header.findViewById(R.id.user_email);
        UserImage = (ImageView)header.findViewById(R.id.user_image);
        Username = (TextView)header.findViewById(R.id.user_name);
        getUserDetails();

        mstatusUpload = (EditText)findViewById(R.id.statusUpload);
        mimageUpload = (ImageView)findViewById(R.id.imageUpload);
        mpostUpload = (Button)findViewById(R.id.postUpload);

        mimageUpload.setOnClickListener(this);
        mpostUpload.setOnClickListener(this);

        //To create the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_post);
        preparePostData();

    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    File f = new File(path);

                    imageName = f.getName();
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    mimageUpload.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public String getStringImage(Bitmap bmp){
        bmp = ((BitmapDrawable) mimageUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog

                        loading.dismiss();
                        preparePostData();

                        //Showing toast message of the response
                        //Toast.makeText(MainActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(upload.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                String text = mstatusUpload.getText().toString();

                //Getting Image Name
                //String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put("email",email);
                params.put("text",text);
                params.put("name", imageName);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == mimageUpload){
            Toast.makeText(MainActivity.this, "You clicked on ImageView", Toast.LENGTH_LONG).show();
            openImageChooser();
        }

        if(v == mpostUpload){
            uploadImage();
        }
    }


    private void getUserDetails() {
        MysqlConProfileShow mysqlConProfileShow = new MysqlConProfileShow();
        mysqlConProfileShow.execute("getprofiledetails",email);
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
                Log.i( "Json", String.valueOf( post.getProfileId() ) );
                post.setPostImage(reader.getString("post_image"));
                post.setPostText( reader.getString("post_text") );
                post.setProfileId( Integer.parseInt( reader.getString( "profile_id" ) ) );
                post.setPostTime(reader.getString( "post_time" ));
                post.setProfileImage(reader.getString("profile"));
                post.setProfileName(reader.getString( "name" ));
                post.setPostId( Integer.parseInt( reader.getString( "post_id" ) ) );
                postList.add(i,post);
                Log.i( "Json post list", postList.get(i).getPostText() );
            }

        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e( "jsonerror","Error in parsing json" );
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

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

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
            type = params[0];
            Log.i("Asynk Task","Asynk task is executing");
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

//            loninterval = System.currentTimeMillis() + 2000;
//            while(System.currentTimeMillis()>interval);g

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
            if(type.equals("post")) {
                Log.e("Result", s);
                FetchData = s;
                String result = "{\"posts\":" + FetchData + "}";
                Log.i("Json", result);
                postList = parseResult(result);
                mAdapter = new PostAdapter(postList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
        }
    }

//Asynk Task to load Profile image and name list from the internet
class MysqlConProfileShow extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        Log.i("Asynk Task","Asynk task is executing");
        String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

//            loninterval = System.currentTimeMillis() + 2000;
//            while(System.currentTimeMillis()>interval);g
        if(type.equals("getprofiledetails")){
            String email = params[1];}
        try {
            URL url = new URL(con_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if(type.equals("getprofiledetails"))
            { OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }
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
            String result = "{\"profile details\":" + FetchData + "}";
            Log.e("Json Result", result);
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray profilearray = reader.getJSONArray("profile details");
                JSONObject profile = profilearray.getJSONObject(0);
                Uname = profile.getString("name");
                Log.e("Username",Uname);
                Uprofilepicture = profile.getString("profile");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Json error ","Cant do th");
            }
            Useremail.setText(email);
            Username.setText(Uname);
            Picasso.with(UserImage.getContext())
                    .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+Uprofilepicture)
                    .into(UserImage);
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
