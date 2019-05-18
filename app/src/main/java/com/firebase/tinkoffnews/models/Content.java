package com.firebase.tinkoffnews.models;

public class Content {
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
