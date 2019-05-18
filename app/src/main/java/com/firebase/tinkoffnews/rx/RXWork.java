package com.firebase.tinkoffnews.rx;

import com.firebase.tinkoffnews.models.AllTitlesModel;
import com.firebase.tinkoffnews.models.Content;
import com.firebase.tinkoffnews.models.Date;
import com.firebase.tinkoffnews.models.Title;
import com.firebase.tinkoffnews.network.TinkoffAPI;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;

public class RXWork {

    public static Observable<Content> getContentStream(int id) {
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

    public static Observable<Title> getHeadersStream(){
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

}
