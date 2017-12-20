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
import com.berber.berberapp.Models.SellingPoint;
import com.berber.berberapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gabotrugomez on 12/5/17.
 */

public class SellingPointsDetailAdapter extends RecyclerView.Adapter<SellingPointsDetailAdapter.ViewHolder> {
    private List<SellingPoint> mDataSet;
    private Context mContext;

    public SellingPointsDetailAdapter(Context context, List<SellingPoint> list) {
        this.mContext = context;
        this.mDataSet = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSellingPointTitleTextView;
        TextView mSellingpointDescriptionTextView;
        ImageView mSellingPointImageView;
        ImageView mSellingPointWebImageView;
        ImageView mSellingPointMapImageView;

        ViewHolder(View v) {
            super(v);
            mSellingPointTitleTextView = v.findViewById(R.id.selling_point_detail_title_item);
            mSellingpointDescriptionTextView = v.findViewById(R.id.selling_point_detail_description_item);
            mSellingPointImageView = v.findViewById(R.id.selling_point_detail_image_view_item);
            mSellingPointWebImageView = v.findViewById(R.id.web_img_view_selling_point_item);
            mSellingPointMapImageView = v.findViewById(R.id.map_img_view_selling_point_item);
        }
    }

    @Override
    public SellingPointsDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.selling_points_detail_item, parent, false);
        SellingPointsDetailAdapter.ViewHolder vh = new SellingPointsDetailAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SellingPointsDetailAdapter.ViewHolder holder, int position) {

        final SellingPoint sellingPoint = mDataSet.get(position);

        holder.mSellingPointTitleTextView.setText(sellingPoint.getTitle());
        holder.mSellingpointDescriptionTextView.setText(sellingPoint.getDescription());

        if (sellingPoint.getImgUrl() != null && !sellingPoint.getImgUrl().equals("")) {
            Glide.with(mContext)
                    .load(sellingPoint.getImgUrl())
                    .into(holder.mSellingPointImageView);
        }


        holder.mSellingPointWebImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sellingPoint.getPlaceUrl() != null && !sellingPoint.getPlaceUrl().equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sellingPoint.getPlaceUrl()));
                    mContext.startActivity(browserIntent);
                }
            }
        });

        holder.mSellingPointMapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sellingPoint.getMapUrl() != null && !sellingPoint.getMapUrl().equals("")) {
                    /*
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sellingPoint.getMapUrl()));
                    mContext.startActivity(browserIntent);
                    */
                    Uri gmmIntentUri = Uri.parse(sellingPoint.getMapUrl());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(mapIntent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
