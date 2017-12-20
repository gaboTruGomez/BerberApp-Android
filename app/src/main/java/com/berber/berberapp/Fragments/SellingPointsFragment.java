package com.berber.berberapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.berber.berberapp.Activities.SellingPointsDetailActivity;
import com.berber.berberapp.R;

public class SellingPointsFragment extends Fragment {

    private RelativeLayout mDirectSellingPointsLayout;
    private RelativeLayout mConsumePointsLayout;
    private RelativeLayout mDistributorPointsLayout;

    public SellingPointsFragment() {
        // Required empty public constructor
    }

    public static SellingPointsFragment newInstance() {
        SellingPointsFragment fragment = new SellingPointsFragment();
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
        View view = inflater.inflate(R.layout.fragment_selling_points, container, false);

        mDirectSellingPointsLayout = view.findViewById(R.id.selling_points_direct_layout_fragment);
        mConsumePointsLayout = view.findViewById(R.id.consume_points_layout_fragment);
        mDistributorPointsLayout = view.findViewById(R.id.distributor_points_layout_fragment);

        mDirectSellingPointsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SellingPointsDetailActivity.class);
                intent.putExtra("type", "direct");
                startActivity(intent);
            }
        });

        mConsumePointsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SellingPointsDetailActivity.class);
                intent.putExtra("type", "consume");
                startActivity(intent);
            }
        });

        mDistributorPointsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SellingPointsDetailActivity.class);
                intent.putExtra("type", "distributor");
                startActivity(intent);
            }
        });

        return view;
    }
}
