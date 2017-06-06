package com.example.deepak.socialnetworkingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        LoaderManager.LoaderCallbacks<List<Post>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Post>>() {
            @Override
            public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
                PostLoader loader = new PostLoader(getApplicationContext());
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
                mAdapter.swapdata(data);
            }

            @Override
            public void onLoaderReset(Loader<List<Post>> loader) {
                mAdapter.swapdata(null);
            }
        }
    }
}
