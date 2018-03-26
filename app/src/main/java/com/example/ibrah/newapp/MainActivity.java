package com.example.ibrah.newapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>> {
    private String url = "http://content.guardianapis.com/search?q=tech&api-key=test";
    private AdapterNews adapterNews;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<New> news = new ArrayList();
        adapterNews = new AdapterNews(this, news);
        ListView listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBarrr);
        listView.setAdapter(adapterNews);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        LoaderManager loaderManager = getSupportLoaderManager();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            loaderManager.initLoader(0, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(news.get(position).getmLink())));
            }
        });
    }


    @Override
    public Loader<List<New>> onCreateLoader(int id, Bundle args) {
        loaderneww loaderneww = new loaderneww(this, url);
        return loaderneww;
    }

    @Override
    public void onLoadFinished(Loader<List<New>> loader, List<New> data) {
        progressBar.setVisibility(View.GONE);
        adapterNews.clear();
        if (data != null && !data.isEmpty()) {
            adapterNews.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<New>> loader) {
        adapterNews.clear();
    }
}
