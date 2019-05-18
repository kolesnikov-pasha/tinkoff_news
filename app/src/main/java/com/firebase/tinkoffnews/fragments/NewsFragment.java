package com.firebase.tinkoffnews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.tinkoffnews.R;
import com.firebase.tinkoffnews.models.Content;
import com.firebase.tinkoffnews.rx.RXWork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment {

    int id;
    View v;
    SwipeRefreshLayout refreshLayout;
    TextView text, title;

    void getContent(){
        Consumer<Content> next = content -> {
            text.setText(Html.fromHtml(content.getPayload().getContent()));
            title.setText(Html.fromHtml(content.getPayload().getTitle().getText()));
        };
        Consumer<Throwable> error = throwable -> {
            refreshLayout.setRefreshing(false);
            Toast.makeText(v.getContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        };
        Action complete = () -> refreshLayout.setRefreshing(false);
        RXWork.getContentStream(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(next, error, complete);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.news_fragment, container, false);
        text = v.findViewById(R.id.content_text);
        title = v.findViewById(R.id.content_title);
        refreshLayout = v.findViewById(R.id.content_refresh);
        refreshLayout.setOnRefreshListener(this::getContent);
        refreshLayout.setRefreshing(true);
        getContent();
        return v;
    }
}
