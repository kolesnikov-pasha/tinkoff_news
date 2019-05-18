package com.firebase.tinkoffnews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.tinkoffnews.MainActivity;
import com.firebase.tinkoffnews.R;
import com.firebase.tinkoffnews.fragments.NewsFragment;
import com.firebase.tinkoffnews.models.Title;

import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.TreeSet;

public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.HeaderViewHolder> {

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private Integer id;
        private TextView text, date;

        void bind(Title header){
            text.setText(Html.fromHtml(header.getText()));
            date.setText(header.getPublicationDate().toString());
            id = header.getId();
        }

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.publication_date);
            itemView.setOnClickListener(v -> {
                NewsFragment fragment = new NewsFragment();
                fragment.setId(id);
                ((MainActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .addToBackStack("news")
                        .commit();
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
                .inflate(R.layout.title_layout, viewGroup, false);
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
