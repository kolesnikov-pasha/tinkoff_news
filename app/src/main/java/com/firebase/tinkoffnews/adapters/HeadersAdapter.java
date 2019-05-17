package com.firebase.tinkoffnews.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.tinkoffnews.NewsActivity;
import com.firebase.tinkoffnews.R;
import com.firebase.tinkoffnews.models.Title;

import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.TreeSet;


public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.HeaderViewHolder> {

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private Integer id;
        private TextView text, date;

        void bind(Title header){
            text.setText(Html.fromHtml(header.getText()));
            String formatDate;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(header.getPublicationDate().getMilliseconds());

            formatDate = c.get(Calendar.DAY_OF_MONTH) + " " +
                    c.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, new Locale("ru")) + " " +
                    c.get(Calendar.YEAR) + " в " +
                    c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE) < 10 ? "0" : "") +
                    c.get(Calendar.MINUTE);
            date.setText(formatDate);
            id = header.getId();
        }

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.publication_date);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsActivity.class);
                intent.putExtra("ID", id);
                itemView.getContext().startActivity(intent);
            });
        }

    }

    private TreeSet<Title> headers = new TreeSet<>();

    public void addAll(Collection<Title> headers) {
        TreeSet<Title> all = new TreeSet<>();
        all.addAll(this.headers);
        all.addAll(headers);
        this.headers.clear();
        this.headers.addAll(all);
        notifyDataSetChanged();
    }

    public void addItem(Title header) {
        headers.add(header);
        notifyDataSetChanged();
    }

    public void clear(){
        headers.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_header_layout, viewGroup, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder headerInformation, int i) {
        for (Title inf: headers) {
            if (i == 0) {
                headerInformation.bind(inf);
                break;
            }
            i--;
        }
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }
}
