package com.example.deepak.socialnetworkingapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.lang.reflect.Array;
import java.util.List;

public class PostLoader extends AsyncTaskLoader<List<Post>> {


    public PostLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Post> loadInBackground() {
        List<Post> posts = null;
        return posts;
    }
}
