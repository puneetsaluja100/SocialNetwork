package com.example.deepak.socialnetworkingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>  {
    private List<Post> PostList;

    public int postId;   public Button CommentButton,ShareButton;
    public int Uid;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView profileName;
        public ImageView profilePicture;
        public TextView mPostText;
        public ImageView mPostImage;
        public Button LikeButton;



        public MyViewHolder(View view) {
            super(view);
            mPostText = (TextView)view.findViewById(R.id.post_text);
            mPostImage = (ImageView)view.findViewById(R.id.iv_post_image);
            profileName = (TextView) view.findViewById( R.id.tv_profile_name );
            profilePicture = (ImageView) view.findViewById( R.id.ib_profile_picture );
            LikeButton = (Button)view.findViewById( R.id.like_button );
            CommentButton = (Button)view.findViewById( R.id.comment_button );
            ShareButton = (Button) view.findViewById( R.id.share_button );
        }
    }


    public PostAdapter(List<Post> PostList,int uid) {
        this.PostList = PostList;
        this.Uid = uid;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fragment_picture_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
        final Post post = PostList.get(position);
        holder.mPostText.setText(post.getPostText());
        Picasso.with(holder.mPostImage.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getPostImage())
                .into(holder.mPostImage);
        holder.profileName.setText(post.getProfileName());
        Picasso.with(holder.profilePicture.getContext())
                .load("https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+post.getProfileImage())
                .into(holder.profilePicture);
        holder.LikeButton.setTextColor( Color.BLACK );

        holder.LikeButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postId = post.getPostId();
                Log.e("The post id clicked is ", String.valueOf( postId ) );
                Toast.makeText( holder.LikeButton.getContext(),"You liked this post",Toast.LENGTH_SHORT ).show();
                setColorLikeButton(holder.LikeButton);
                MysqlConLike mysqlConLike = new MysqlConLike();
                mysqlConLike.execute( "like",String.valueOf(Uid),String.valueOf(postId) );
            }
        });

        CommentButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                postId = post.getPostId();
//                Log.e("The post id clicked is ", String.valueOf( postId ) );
                Intent intent = new Intent(CommentButton.getContext(),comment.class);
                intent.putExtra("Uid",Uid);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra( "Pid",post.getProfileId() );
                ((Activity)CommentButton.getContext()).startActivity(intent);

            }
        });

//        ShareButton.setOnClickListener( new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                postId = post.getPostId();
//                Log.e("The post id clicked is ", String.valueOf( postId ) );
//            }
//        });

    }

    private void setColorLikeButton(Button LikeButton) {
        ColorStateList mList = LikeButton.getTextColors();
        int color = mList.getDefaultColor();
        Log.e( "Color", String.valueOf( color ) );
        Log.e( "Color", String.valueOf(Color.BLACK) );

        switch(color)
        {
            case Color.RED:
                LikeButton.setTextColor(Color.BLACK);
                LikeButton.setDrawingCacheBackgroundColor(Color.BLACK);
                break;

            case Color.BLACK:
                Log.e( "Color","Inside when color is black" );
                LikeButton.setTextColor( Color.RED);

                break;

        }
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

    //Asynk task to add new Likes to a post
    class MysqlConLike extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            String con_url = "https://socialnetworkapplication.000webhostapp.com/SocialNetwork/"+type+".php";

            String Uid = params[1];
            String postId = params[2];

            try {
                URL url = new URL(con_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream  = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data= URLEncoder.encode("postId","UTF-8")+"="+ URLEncoder.encode(postId,"UTF-8")+"&"
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
            Log.e("Result",s+"Php hai");

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


