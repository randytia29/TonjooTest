package com.example.randytia.tonjootest;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Randytia on 08/08/2017.
 */

public class TonjooLoader extends AsyncTaskLoader<List<Tonjoo>> {

    private String mUrl;

    public TonjooLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Tonjoo> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Lakukan network request, uraikan respons, dan ekstrak daftar gempa.
        List<Tonjoo> tonjoos = QueryUtils.fetchTonjooData(mUrl);
        return tonjoos;
    }
}
