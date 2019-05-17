package com.firebase.tinkoffnews.models;

public class Title implements Comparable<Title>{

    private Integer id, bankInfoTypeId;
    private String name, text;
    private Date publicationDate;

    public Title() { }

    public Title(Integer id, Integer bankInfoTypeId, String name, String text, Date publicationDate) {
        this.id = id;
        this.bankInfoTypeId = bankInfoTypeId;
        this.name = name;
        this.text = text;
        this.publicationDate = publicationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(Integer bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public int compareTo(Title o) {
        int c = publicationDate.getMilliseconds().compareTo(o.getPublicationDate().getMilliseconds());
        if (c != 0 && id.compareTo(o.getId()) != 0) {
            return -c;
        }
        else return id.compareTo(o.getId());
    }
}
