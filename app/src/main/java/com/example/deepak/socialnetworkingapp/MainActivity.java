package com.example.deepak.socialnetworkingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_post);
        preparePostData();
        mAdapter = new PostAdapter(postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void preparePostData() {
        Post post = new Post(1,"hello ","deepak.jpg","9999-12-31 23:59:59");
        postList.add(post);
        post = new Post(2,"HI there ","deepak.jpg","9999-12-31 23:59:59");
        postList.add(post);

//        String result = "[{\"id\":\"1\",\"profile_id\":\"5\",\"post_text\":\"Hi op !\",\"post_image\":\"0\",\"post_time\":\"2017-06-05 16:59:33\"},{\"id\":\"2\",\"profile_id\":\"6\",\"post_text\":\"HI Deepak !\",\"post_image\":\"0\",\"post_time\":\"2017-06-05 17:08:07\"},{\"id\":\"3\",\"profile_id\":\"2\",\"post_text\":\"HI PUNEET!\",\"post_image\":\"0\",\"post_time\":\"2017-06-05 17:08:44\"}]";
//
//        postList = parseResult(result);}
//
//        public List<Post> parseResult(String result) {
//            List<Post> postList = null;
//            Post post = null;
//            try {
//                JSONObject reader = new JSONObject(result);
//
//                post.setProfileId(Integer.parseInt(reader.getString("id")));
//                post.setPostImage(reader.getString("post_image"));
//                post.setPostText( reader.getString("post_text") );
//                post.setProfileId( Integer.parseInt( reader.getString( "profile_id" ) ) );
//                post.setPostTime(reader.getString( "post_time" ));
//                postList.add(post);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return postList;
        }

//        LoaderManager.LoaderCallbacks<List<Post>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Post>>() {
//            @Override
//            public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
//
//                PostLoader loader = new PostLoader(getApplicationContext(),new String[] {"post"});
//                return loader;
//            }
//
//            @Override
//            public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
//                mAdapter.swapdata(data);
//            }
//
//            @Override
//            public void onLoaderReset(Loader<List<Post>> loader) {
//                mAdapter.swapdata(null);
//            }
//        }

}
