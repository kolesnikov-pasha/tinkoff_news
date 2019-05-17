package com.firebase.tinkoffnews.models;

public class AllTitlesModel {

    private String resultCode = "OK";
    private Title[] payload;

    public AllTitlesModel() {
    }

    public AllTitlesModel(String resultCode, Title[] payload) {
        this.resultCode = resultCode;
        this.payload = payload;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Title[] getPayload() {
        return payload;
    }

    public void setPayload(Title[] payload) {
        this.payload = payload;
    }
}
