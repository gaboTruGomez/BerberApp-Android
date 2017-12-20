package com.berber.berberapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gabotrugomez on 11/2/17.
 */

public class BeerCartAdapter extends RecyclerView.Adapter<BeerCartAdapter.ViewHolder> {
    private HashMap<Beer, Integer> mDataSet;
    private Context mContext;
    ArrayList<Beer> beers;
    ArrayList<Integer> amount;

    public BeerCartAdapter(Context context, HashMap<Beer, Integer> list) {
        this.mContext = context;
        this.mDataSet = list;
        this.beers =  new ArrayList<>(list.keySet());
        this.amount = new ArrayList<>(list.values());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBeerTitleTextView;
        TextView mBeerAmountTextView;
        TextView mBeerUnitPriceTextView;
        TextView mBeerTotalPriceTextView;

        ViewHolder(View v) {
            super(v);
            mBeerTitleTextView = v.findViewById(R.id.beer_type_text_view_cart_item);
            mBeerAmountTextView = v.findViewById(R.id.beer_amount_text_view_cart_item);
            mBeerUnitPriceTextView = v.findViewById(R.id.beer_unit_price_text_view_cart_item);
            mBeerTotalPriceTextView = v.findViewById(R.id.beer_total_price_text_view_cart_item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.beer_cart_view_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBeerTitleTextView.setText(beers.get(position).getName());
        holder.mBeerAmountTextView.setText("" + amount.get(position));
        holder.mBeerUnitPriceTextView.setText("$" + beers.get(position).getPrice() + " MXN");
        holder.mBeerTotalPriceTextView.setText("$" + (beers.get(position).getPrice() * amount.get(position)) + " MXN");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
