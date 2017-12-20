package com.berber.berberapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berber.berberapp.Models.Event;
import com.berber.berberapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gabotrugomez on 11/29/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> mDataSet;
    private Context mContext;

    public EventAdapter(Context context, List<Event> list) {
        this.mContext = context;
        this.mDataSet = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mEventTitleTextView;
        TextView mEventDescriptionTextView;
        ImageView mEventImageView;
        LinearLayout mEventLayoutWrapper;

        ViewHolder(View v) {
            super(v);
            mEventTitleTextView = v.findViewById(R.id.event_title_item);
            mEventDescriptionTextView = v.findViewById(R.id.event_description_item);
            mEventImageView = v.findViewById(R.id.event_image_view_item);
            mEventLayoutWrapper = v.findViewById(R.id.event_layout_wrapper_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.event_view_item, parent, false);
        EventAdapter.ViewHolder vh = new EventAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Event event = mDataSet.get(position);

        holder.mEventTitleTextView.setText(event.getTitle());
        holder.mEventDescriptionTextView.setText(event.getDescription());

        Glide.with(mContext)
                .load(event.getImgUrl())
                .into(holder.mEventImageView);

        holder.mEventLayoutWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventUrl()));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
