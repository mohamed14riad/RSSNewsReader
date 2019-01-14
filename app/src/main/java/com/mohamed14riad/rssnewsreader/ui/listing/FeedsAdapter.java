package com.mohamed14riad.rssnewsreader.ui.listing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohamed14riad.rssnewsreader.R;
import com.mohamed14riad.rssnewsreader.data.models.FeedItem;

import java.util.ArrayList;
import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ItemViewHolder> {

    private Context context;
    private ListingView listingView;

    private List<FeedItem> items;

    FeedsAdapter(Context context, ListingView listingView) {
        this.context = context;
        this.listingView = listingView;
        this.items = new ArrayList<>();
    }

    public void setItems(List<FeedItem> items) {
        this.items.clear();
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        FeedItem item = items.get(position);

        itemViewHolder.itemTitle.setText(item.getTitle());
        itemViewHolder.itemPubDate.setText(item.getPubDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemTitle, itemPubDate;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.item_title);
            itemPubDate = itemView.findViewById(R.id.item_pubDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listingView.onItemClicked(items.get(getAdapterPosition()));
        }
    }
}
