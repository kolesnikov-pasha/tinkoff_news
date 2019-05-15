package com.firebase.tinkoffnews;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TreeSet;

class Data {
    private Long milliseconds;

    public Data(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Data() {
    }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}

class HeaderInformation implements Comparable<HeaderInformation>{

    private Integer id, bankInfoTypeId;
    private String name, text;
    private Data publicationDate;

    public HeaderInformation() { }

    public HeaderInformation(Integer id, Integer bankInfoTypeId, String name, String text, Data publicationDate) {
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

    public Data getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Data publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public int compareTo(HeaderInformation o) {
        if (o.getName().compareTo(name) != 0) return o.getName().compareTo(name);
        else return id.compareTo(o.getId());
    }
}

public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.HeaderViewHolder> {

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView text, date;

        public void bind(HeaderInformation header){
            text.setText(header.getText());
            String formatDate;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(header.getPublicationDate().getMilliseconds());
            formatDate = c.get(Calendar.DAY_OF_MONTH) + " " +
                    c.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US) + " " +
                    c.get(Calendar.YEAR);
            date.setText(formatDate);
        }

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.publication_date);
        }
    }

    private LinkedList<HeaderInformation> headers = new LinkedList<>();

    public void addAll(Collection<HeaderInformation> headers) {
        TreeSet<HeaderInformation> all = new TreeSet<>();
        all.addAll(this.headers);
        all.addAll(headers);
        this.headers.clear();
        this.headers.addAll(all);
        notifyDataSetChanged();
    }

    public void addItem(HeaderInformation header) {
        int i = 0;
        while (headers.size() > i && header.compareTo(headers.get(i)) < 0) i++;
        headers.add(i, header);
        notifyDataSetChanged();
    }

    public void clear(){
        headers.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_header_layout, viewGroup, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder headerInformation, int i) {
        headerInformation.bind(headers.get(i));
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }
}
