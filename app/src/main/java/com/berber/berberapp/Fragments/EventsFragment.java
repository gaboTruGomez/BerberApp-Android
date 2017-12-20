package com.berber.berberapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.berber.berberapp.Adapters.EventAdapter;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.Models.Event;
import com.berber.berberapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Event> events;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_events_fragment);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("events");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Event>> t = new GenericTypeIndicator<ArrayList<Event>>() {};
                events = dataSnapshot.getValue(t);

                setEventAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error descargnado eventos.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setEventAdapter() {
        EventAdapter adapter = new EventAdapter(getContext(), events);
        mRecyclerView.setAdapter(adapter);
    }
}
