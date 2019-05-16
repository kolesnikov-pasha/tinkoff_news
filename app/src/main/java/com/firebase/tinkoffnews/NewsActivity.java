package com.firebase.tinkoffnews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsActivity extends AppCompatActivity {

    class Payload {
        Object creationDate, lastModificationDate, title, bankInfoTypeId, typeId;
        String content;

        public Payload(Object creationDate, Object lastModificationDate, Object title, Object bankInfoTypeId, Object typeId, String content) {
            this.creationDate = creationDate;
            this.lastModificationDate = lastModificationDate;
            this.title = title;
            this.bankInfoTypeId = bankInfoTypeId;
            this.typeId = typeId;
            this.content = content;
        }

        public Payload() {
        }

        public Object getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(Object creationDate) {
            this.creationDate = creationDate;
        }

        public Object getLastModificationDate() {
            return lastModificationDate;
        }

        public void setLastModificationDate(Object lastModificationDate) {
            this.lastModificationDate = lastModificationDate;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getBankInfoTypeId() {
            return bankInfoTypeId;
        }

        public void setBankInfoTypeId(Object bankInfoTypeId) {
            this.bankInfoTypeId = bankInfoTypeId;
        }

        public Object getTypeId() {
            return typeId;
        }

        public void setTypeId(Object typeId) {
            this.typeId = typeId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    class Content {
        Payload payload;
        Object resultCode, trackingId;

        public Content(Payload payload, Object resultCode, Object trackingId) {
            this.payload = payload;
            this.resultCode = resultCode;
            this.trackingId = trackingId;
        }

        public Content() {
        }

        public Payload getPayload() {
            return payload;
        }

        public void setPayload(Payload payload) {
            this.payload = payload;
        }

        public Object getResultCode() {
            return resultCode;
        }

        public void setResultCode(Object resultCode) {
            this.resultCode = resultCode;
        }

        public Object getTrackingId() {
            return trackingId;
        }

        public void setTrackingId(Object trackingId) {
            this.trackingId = trackingId;
        }
    }

    int id;
    SwipeRefreshLayout refreshLayout;
    TextView text;

    void getContent(){
        Observable.create((ObservableOnSubscribe<Content>) emitter -> {
            long t = System.currentTimeMillis();
            Gson gson = new Gson();
            StringBuilder text = new StringBuilder();
            try {
                InputStream in = new URL("https://api.tinkoff.ru/v1/news_content?id=" + id).openStream();
                Scanner scanner = new Scanner(in);
                while (scanner.hasNext()) text.append(scanner.nextLine());
                Content content = gson.fromJson(text.toString(), Content.class);
                emitter.onNext(content);
                emitter.onComplete();
                System.out.println("Time: " + (System.currentTimeMillis() - t));
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnNext(content -> {
            text.setText(Html.fromHtml(content.payload.content));
        }).doOnError((throwable) -> {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), "Проверьте подключение к интернету", Toast.LENGTH_LONG).show();
        })
        .doOnComplete(() -> refreshLayout.setRefreshing(false))
        .subscribe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        id  = getIntent().getExtras().getInt("ID");
        text = findViewById(R.id.content_text);
        refreshLayout = findViewById(R.id.content_refresh);
        refreshLayout.setOnRefreshListener(this::getContent);
        refreshLayout.setRefreshing(true);
        getContent();
    }
}
