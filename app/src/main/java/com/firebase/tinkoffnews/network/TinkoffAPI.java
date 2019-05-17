package com.firebase.tinkoffnews.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class TinkoffAPI {

    static public String getTitles() throws IOException {
        StringBuilder text = new StringBuilder();
        InputStream in = new URL("https://api.tinkoff.ru/v1/news").openStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) text.append(scanner.nextLine());
        return text.toString();
    }

    static public String getNews(long id) throws IOException {
        StringBuilder text = new StringBuilder();
        InputStream in = new URL("https://api.tinkoff.ru/v1/news_content?id=" + id).openStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) text.append(scanner.nextLine());
        return text.toString();
    }

}
