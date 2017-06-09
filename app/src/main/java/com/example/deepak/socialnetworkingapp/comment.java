package com.example.deepak.socialnetworkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class comment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private comment_adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        prepareCommentData();
    }

    private void prepareCommentData() {
        List<comment_recycler> commentList = new ArrayList<>();
        comment_recycler comment = new comment_recycler("Hello my name is puneet ","puneetsaluja@gmail.com/profile");
        commentList.add(comment);
        mAdapter = new comment_adapter(commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
