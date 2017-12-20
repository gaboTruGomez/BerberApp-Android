package com.berber.berberapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.berber.berberapp.Activities.CartActivity;
import com.berber.berberapp.Activities.HomeActivity;
import com.berber.berberapp.Activities.SignActivity;
import com.berber.berberapp.Adapters.BeerStoreAdapter;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreFragment extends Fragment implements BeerStoreAdapter.OnDataChangeListener {

    private RecyclerView recyclerView;
    private RelativeLayout amountLayout;
    private BeerStoreAdapter storeAdapter;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance() {
        StoreFragment fragment = new StoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        (view.findViewById(R.id.signin_btn_store_fragment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButtonAction();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Hide warning that user is not logged in.
            LinearLayout textView = view.findViewById(R.id.warning_not_logged_in);
            textView.setVisibility(View.GONE);
        }
        else {
            LinearLayout linearLayout = view.findViewById(R.id.store_layout_wrapper_store_fragment);
            linearLayout.setVisibility(View.GONE);
        }

        List<Beer> beers = ((HomeActivity) getActivity()).beers;

        ArrayList<Beer> beersCopy = new ArrayList<>(beers);
        for (Beer beer : beersCopy) {
            if (!beer.isActive()) {
                beers.remove(beer);
            }
        }

        recyclerView = view.findViewById(R.id.recycler_view_fragment_store);
        amountLayout = view.findViewById(R.id.amount_layout_view_fragment_store);

        view.findViewById(R.id.add_to_cart_btn_fragment_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToCartAction();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        storeAdapter = new BeerStoreAdapter(getContext(), beers);
        storeAdapter.setOnDataChangeListener(this);
        recyclerView.setAdapter(storeAdapter);

        amountLayout.setVisibility(View.GONE);

        return view;
    }

    public void signInButtonAction() {
        Intent intent = new Intent(getContext(), SignActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onDataChanged(int amount) {
        if (amount < 24 && amountLayout.getVisibility() == View.VISIBLE) {
            Animation slide_down = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
            amountLayout.setVisibility(View.GONE);
            amountLayout.startAnimation(slide_down);
            return;
        }

        if (amount >= 24 && amountLayout.getVisibility() == View.GONE) {

            Animation slide_up = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            amountLayout.setVisibility(View.VISIBLE);
            amountLayout.startAnimation(slide_up);
        }
    }

    public void onAddToCartAction() {
        HashMap<Beer, Integer> cart =  new HashMap<>(storeAdapter.getmAmountPerBeerMap());
        ArrayList<Beer> beers = new ArrayList<>(cart.keySet());

        for (Beer beer : beers) {
            if (cart.get(beer) == 0) {
                cart.remove(beer);
            }
        }

        Intent intent = new Intent(getContext(), CartActivity.class);
        intent.putExtra("cart", cart);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((HomeActivity)getActivity()).reloadStoreFragment();
    }
}
