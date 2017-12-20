package com.berber.berberapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabotrugomez on 11/2/17.
 */

public class BeerStoreAdapter extends RecyclerView.Adapter<BeerStoreAdapter.ViewHolder> {

    private List<Beer> mDataSet;
    private List<Integer> mAmountDataSet;
    private HashMap<Beer, Integer> mAmountPerBeerMap;
    private int totalAmount;
    private Context mContext;
    private OnDataChangeListener mOnDataChangeListener;

    public BeerStoreAdapter(Context context, List<Beer> list) {
        this.mContext = context;
        this.mDataSet = list;
        this.totalAmount = 0;
        this.mAmountDataSet = new ArrayList<>();
        this.mAmountPerBeerMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            mAmountDataSet.add(i, 0);
            mAmountPerBeerMap.put(list.get(i), 0);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBeerTitleTextView;
        TextView mBeerPriceTextView;
        TextView mBeerAmountTextView;
        ImageView mBeerImgView;
        ImageButton mAddButton;
        ImageButton mRemoveButton;

        ViewHolder(View v) {
            super(v);
            mBeerTitleTextView = v.findViewById(R.id.beer_title_store_view_item);
            mBeerPriceTextView = v.findViewById(R.id.beer_price_store_view_item);
            mBeerAmountTextView = v.findViewById(R.id.beer_amount_text_view_store_view_item);
            mBeerImgView = v.findViewById(R.id.beer_imgview_store_view_item);
            mAddButton = v.findViewById(R.id.button_add_store_view_item);
            mRemoveButton = v.findViewById(R.id.button_remove_store_view_item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.store_view_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBeerTitleTextView.setText(mDataSet.get(position).getName());
        holder.mBeerPriceTextView.setText("$" + mDataSet.get(position).getPrice() + " MXN");
        holder.mBeerAmountTextView.setText("" + mAmountDataSet.get(position));

        holder.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalAmount = totalAmount + mDataSet.get(position).getSingleBeerAmount();
                mAmountDataSet.set(position, mAmountDataSet.get(position) + 1);
                mAmountPerBeerMap.put(mDataSet.get(position), mAmountPerBeerMap.get(mDataSet.get(position)) + 1);
                holder.mBeerAmountTextView.setText("" + mAmountDataSet.get(position));

                mOnDataChangeListener.onDataChanged(totalAmount);
            }
        });

        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAmountDataSet.get(position) != 0) {
                    mAmountDataSet.set(position, mAmountDataSet.get(position) - 1);
                    mAmountPerBeerMap.put(mDataSet.get(position), mAmountPerBeerMap.get(mDataSet.get(position)) - 1);
                    totalAmount -= mDataSet.get(position).getSingleBeerAmount();
                    mOnDataChangeListener.onDataChanged(totalAmount);
                }
                holder.mBeerAmountTextView.setText("" + mAmountDataSet.get(position));
            }
        });

        Glide.with(mContext)
                .load(mDataSet.get(position).getImgUrl())
                .into(holder.mBeerImgView);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public HashMap<Beer, Integer> getmAmountPerBeerMap() {
        return this.mAmountPerBeerMap;
    }

    public void setOnDataChangeListener(OnDataChangeListener mOnDataChangeListener) {
        this.mOnDataChangeListener = mOnDataChangeListener;
    }

    public interface OnDataChangeListener {
        void onDataChanged(int amount);
    }
}
