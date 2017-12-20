package com.berber.berberapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.berber.berberapp.Adapters.EventAdapter;
import com.berber.berberapp.Adapters.SellingPointsDetailAdapter;
import com.berber.berberapp.Models.Event;
import com.berber.berberapp.Models.SellingPoint;
import com.berber.berberapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellingPointsDetailActivity extends AppCompatActivity {

    private RecyclerView mSellingPointsRecyclerView;
    private ArrayList<SellingPoint> sellingPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_points_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSellingPointsRecyclerView = findViewById(R.id.selling_points_recyclerview_detail_activity);
        mSellingPointsRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        String sellingPointType = getIntent().getStringExtra("type");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("selling_points").child(sellingPointType);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf("MEME", "datasnapshot: " + dataSnapshot.toString());
                GenericTypeIndicator<ArrayList<SellingPoint>> t = new GenericTypeIndicator<ArrayList<SellingPoint>>() {};
                sellingPoints = dataSnapshot.getValue(t);

                setSellingPointsList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SellingPointsDetailActivity.this, "Error descargnado eventos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSellingPointsList() {
        SellingPointsDetailAdapter adapter = new SellingPointsDetailAdapter(this, sellingPoints);
        mSellingPointsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
