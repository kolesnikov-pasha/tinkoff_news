package com.firebase.tinkoffnews.models;

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
}
