package com.firebase.tinkoffnews.models;

public class Payload {
    private Object creationDate, lastModificationDate, bankInfoTypeId, typeId;
    private String content;
    private Title title;

    public Payload(Object creationDate, Object lastModificationDate, Title title, Object bankInfoTypeId, Object typeId, String content) {
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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
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
