package com.berber.berberapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.berber.berberapp.Fragments.ContactFragment;
import com.berber.berberapp.Fragments.FAQFragment;
import com.berber.berberapp.Fragments.SellingPointsFragment;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.Fragments.EventsFragment;
import com.berber.berberapp.Fragments.HomeFragment;
import com.berber.berberapp.R;
import com.berber.berberapp.Fragments.StoreFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private int selectedFrag = 0;
    public ArrayList<Beer> beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("CERVEZA BERBER");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("beers");


        Log.wtf("MainAct", "heheheh " + mDatabase.toString());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf("MainAct", "datasnapshot: " + dataSnapshot);
                GenericTypeIndicator<ArrayList<Beer>> t = new GenericTypeIndicator<ArrayList<Beer>>() {};
                beers = dataSnapshot.getValue(t);

                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view_home_activity);

                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragmentSelected = null;
                        switch (item.getItemId()) {
                            case R.id.home_action:
                                if (selectedFrag != 0) {
                                    fragmentSelected = HomeFragment.newInstance();
                                    selectedFrag = 0;
                                }
                                break;
                            case R.id.store_action:
                                if (selectedFrag != 1) {
                                    fragmentSelected = StoreFragment.newInstance();
                                    selectedFrag = 1;
                                }
                                break;
                            case R.id.events_action:
                                if (selectedFrag != 2) {
                                    fragmentSelected = EventsFragment.newInstance();
                                    selectedFrag = 2;
                                }
                                break;
                            case R.id.selling_points_action:
                                if (selectedFrag != 3) {
                                    fragmentSelected = SellingPointsFragment.newInstance();
                                    selectedFrag = 3;
                                }
                                break;
                        }
                        if (fragmentSelected != null) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout_home_activity, fragmentSelected);
                            transaction.commit();
                        }
                        return true;
                    }
                });

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home_activity, HomeFragment.newInstance());
                transaction.commit();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("MainAct", "meeee");
                Log.wtf("MainAct", "Error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_top_bar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    public void reloadStoreFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_home_activity, StoreFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (selectedFrag == 1) {
            reloadStoreFragment();
        }
    }
}
