package com.example.ibrah.newapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

/**
 * Created by ibrah on 18/07/2017.
 */

public class loaderneww extends AsyncTaskLoader {
    String NEW_URL;
    public loaderneww(Context context, String url){
        super(context);
        this.NEW_URL = url;
    }
    @Override
    public List<New> loadInBackground() {
        if (NEW_URL == null) {
            return null;
        }

        List<New> books = QuerryNews.fetchNewsData(NEW_URL);
        return books;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}

