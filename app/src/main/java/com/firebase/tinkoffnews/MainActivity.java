package com.firebase.tinkoffnews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.tinkoffnews.adapters.TitlesAdapter;
import com.firebase.tinkoffnews.models.Title;
import com.firebase.tinkoffnews.rx.RXWork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;
    TitlesAdapter adapter = new TitlesAdapter();

    void sendInternetErrorMessage(){
        Toast.makeText(this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
    }

    void refresh() {
        Consumer<Title> next = title -> {
            if (title.getId().equals(-1)) {
                adapter.clear();
                return;
            }
            adapter.addItem(title);
        };
        Consumer<Throwable> error = throwable -> {
            refreshLayout.setRefreshing(false);
            sendInternetErrorMessage();
        };
        Action complete = () -> refreshLayout.setRefreshing(false);
        RXWork.getHeadersStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error, complete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this::refresh);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(decoration);
        refreshLayout.setRefreshing(true);
        refresh();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
        else getSupportFragmentManager().popBackStack();
    }

}
