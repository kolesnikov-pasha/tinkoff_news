package com.firebase.tinkoffnews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.tinkoffnews.adapters.HeadersAdapter;
import com.firebase.tinkoffnews.models.*;
import com.firebase.tinkoffnews.network.TinkoffAPI;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;
    HeadersAdapter adapter = new HeadersAdapter();

    Observable<Title> getHeadersStream(){
        return Observable.create(emitter -> {
            long t = System.currentTimeMillis();
            Gson gson = new Gson();
            String text = TinkoffAPI.getTitles();
            AllTitlesModel titles = gson.fromJson(text, AllTitlesModel.class);
            emitter.onNext(new Title(-1, 1, "", "", new Date(0L)));
            for (Title information : titles.getPayload()) {
                emitter.onNext(information);
            }
            emitter.onComplete();
            System.out.println("Time: " + (System.currentTimeMillis() - t));
        });
    }

    void refresh() {
        getHeadersStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((information) -> {
                            if (information.getId().equals(-1)) {
                                adapter.clear();
                                return;
                            }
                            adapter.addItem(information);
                        },
                        (throwable) -> {
                            refreshLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Проверьте подключение к интернету", Toast.LENGTH_LONG).show(); },
                        ()->refreshLayout.setRefreshing(false));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this::refresh);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(decoration);
        refreshLayout.setRefreshing(true);
        refresh();
    }
}
