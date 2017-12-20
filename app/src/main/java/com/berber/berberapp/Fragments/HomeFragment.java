package com.berber.berberapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.berber.berberapp.Activities.BeerDetailActivity;
import com.berber.berberapp.Activities.HomeActivity;
import com.berber.berberapp.Adapters.BeerHomeAdapter;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment implements BeerHomeAdapter.OnBeerItemClickListener{

    private RecyclerView mBeerRecyclerView;
    private List<Beer> beers;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        beers = new ArrayList<>(((HomeActivity) getActivity()).beers);

        mBeerRecyclerView = v.findViewById(R.id.beer_recyclerview_home_fragment);

        mBeerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ArrayList<Beer> beersCopy = new ArrayList<>(beers);
        for (Beer beer : beersCopy) {
            if (!beer.isSingleBeer()) {
                beers.remove(beer);
            }
        }

        BeerHomeAdapter adapter = new BeerHomeAdapter(getContext(), beers);
        adapter.setOnBeerItemClickListener(this);
        mBeerRecyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onBeerItemClick(int position) {
        Intent intent = new Intent(getContext(), BeerDetailActivity.class);
        intent.putExtra("beer", beers.get(position));
        startActivity(intent);
    }
}
