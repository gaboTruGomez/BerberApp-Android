package com.berber.berberapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gabotrugomez on 11/8/17.
 */

public class BeerHomeAdapter extends RecyclerView.Adapter<BeerHomeAdapter.ViewHolder> {

    private List<Beer> mDataSet;
    private Context mContext;
    private OnBeerItemClickListener listener;

    public BeerHomeAdapter(Context context, List<Beer> list) {
        this.mContext = context;
        this.mDataSet = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBeerTitleTextView;
        ImageView mBeerImageView;
        LinearLayout mBeerLayout;

        ViewHolder(View v) {
            super(v);
            mBeerTitleTextView = v.findViewById(R.id.beer_name_text_view_home_view_item);
            mBeerImageView = v.findViewById(R.id.beer_imgview_home_view_item);
            mBeerLayout = v.findViewById(R.id.beer_item_layout_beer_home_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.beer_home_view_item, parent, false);
        BeerHomeAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mBeerTitleTextView.setText(mDataSet.get(position).getName());
        holder.mBeerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBeerItemClick(position);
            }
        });

        Glide.with(mContext)
                .load(mDataSet.get(position).getImgUrl())
                .into(holder.mBeerImageView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnBeerItemClickListener(OnBeerItemClickListener onBeerItemClickListener) {
        this.listener = onBeerItemClickListener;
    }

    public interface OnBeerItemClickListener {
        void onBeerItemClick(int position);
    }
}
