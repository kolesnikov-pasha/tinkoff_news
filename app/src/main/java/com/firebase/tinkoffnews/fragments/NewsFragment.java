package com.firebase.tinkoffnews.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.tinkoffnews.R;
import com.firebase.tinkoffnews.models.Content;
import com.firebase.tinkoffnews.network.TinkoffAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment {

    int id;
    View v;
    SwipeRefreshLayout refreshLayout;
    TextView text, title;

    Observable<Content> getContentStream() {
        return Observable.create(emitter -> {
            long t = System.currentTimeMillis();
            Gson gson = new Gson();
            try {
                String text = TinkoffAPI.getNews(id);
                Content content = gson.fromJson(text, Content.class);
                emitter.onNext(content);
                System.out.println("Time: " + (System.currentTimeMillis() - t));
                emitter.onComplete();
            }
            catch (IOException e) {
                emitter.onError(e);
            }
        });
    }

    void getContent(){
        getContentStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(content -> {
                            text.setText(Html.fromHtml(content.getPayload().getContent()));
                            title.setText(Html.fromHtml(content.getPayload().getTitle().getText()));
                        },
                        (throwable) -> {
                            refreshLayout.setRefreshing(false);
                            Log.e("get news error", throwable.getMessage());
                            Toast.makeText(v.getContext(), "Проверьте подключение к интернету", Toast.LENGTH_LONG).show();
                        },
                        () -> refreshLayout.setRefreshing(false));
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
