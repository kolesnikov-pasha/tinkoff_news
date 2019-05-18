package com.firebase.tinkoffnews.models;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Locale;

public class Date {

    private Long milliseconds;

    public Date(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Date() { }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @NonNull
    @Override
    public String toString() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        String formatDate = c.get(Calendar.DAY_OF_MONTH) +
                " " +
                c.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, new Locale("ru")) +
                " " +
                c.get(Calendar.YEAR) +
                " в " +
                c.get(Calendar.HOUR_OF_DAY) +
                ":" +
                (c.get(Calendar.MINUTE) < 10 ? "0" : "") +
                c.get(Calendar.MINUTE);
        return formatDate;
    }
}
