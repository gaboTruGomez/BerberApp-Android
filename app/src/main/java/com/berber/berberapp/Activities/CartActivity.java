package com.berber.berberapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.berber.berberapp.Adapters.BeerCartAdapter;
import com.berber.berberapp.Constants;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.Models.Coupon;
import com.berber.berberapp.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private HashMap<Beer, Integer> cart;

    private RecyclerView mRecyclerView;
    private TextView mSubTotalTextview;
    private TextView mTotalTextView;
    //private CheckBox mPackageCheckBox;
    private EditText mCouponEditText;

    private Double subTotal;
    private Double total;
    private Coupon coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cart = (HashMap<Beer, Integer>) getIntent().getSerializableExtra("cart");

        mRecyclerView = findViewById(R.id.recycler_view_cart_activity);
        mSubTotalTextview = findViewById(R.id.subtotal_textview_cart_activity);
        mTotalTextView = findViewById(R.id.total_textview_cart_activity);
        //mPackageCheckBox = findViewById(R.id.package_checkbox_cart_activity);
        mCouponEditText = findViewById(R.id.coupon_edittext_cart_activity);

        setOrderTotal();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        BeerCartAdapter adapter = new BeerCartAdapter(this, cart);
        mRecyclerView.setAdapter(adapter);

        mCouponEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 8) {
                    Toast.makeText(CartActivity.this, "Confirmando cupón...", Toast.LENGTH_SHORT).show();
                    checkCouponInDB(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void checkCouponInDB(final String couponStr) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("coupons").child(couponStr);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    coupon = dataSnapshot.getValue(Coupon.class);
                    coupon.setId(couponStr);
                    couponCallback(true);
                }
                else {
                    coupon = null;
                    couponCallback(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void couponCallback(boolean isValid) {
        if (isValid) {
            new MaterialStyledDialog.Builder(CartActivity.this)
                    .setTitle("Cupón aceptado!")
                    .setDescription("El cupón fue aceptado!")
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setHeaderColor(R.color.colorButtonBackground)
                    .setPositiveText("Aceptar")
                    .show();

            updateOrderTotal(isValid);
        }
        else {
            new MaterialStyledDialog.Builder(CartActivity.this)
                    .setTitle("No se encontró cupón!")
                    .setDescription("El cupón no fue válido, lo sentimos!")
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setHeaderColor(R.color.colorGray)
                    .setPositiveText("Aceptar")
                    .show();
            updateOrderTotal(isValid);
        }
    }

    private void updateOrderTotal(boolean isValid) {
        if (isValid) {
            if (coupon.getType().equals(Constants.COUPON_DISCOUNT)) {
                total = subTotal - ((subTotal * coupon.getAmount()) / 100);
                total = (double)Math.round(total * 100) / 100;
                mTotalTextView.setText("$ " + total);
            }
            else if (coupon.getType().equals(Constants.COUPON_LESS_MONEY)) {
                total = subTotal - coupon.getAmount();
                total = (double)Math.round(total * 100) / 100;
                mTotalTextView.setText("$ " + total);
            }
        }
        else {
            total = subTotal;
            mTotalTextView.setText("$ " + total);
        }
    }

    private void setOrderTotal() {
        ArrayList<Beer> beers = new ArrayList<>(cart.keySet());
        ArrayList<Integer> beersAmount = new ArrayList<>(cart.values());

         subTotal = 0.0;
         total = 0.0;

        for (int i = 0; i < beers.size(); i++) {
            subTotal += (beers.get(i).getPrice() * beersAmount.get(i));
            total += (beers.get(i).getPrice() * beersAmount.get(i));
        }

        mSubTotalTextview.setText("$ " + subTotal);
        mTotalTextView.setText("$ " + total);
    }

    public void onConfirmAction(View v) {
        Intent intent = new Intent(this, SendActivity.class);

        intent.putExtra("order", cart);
        intent.putExtra("total", total);
        intent.putExtra("coupon", coupon);
        //intent.putExtra("package", mPackageCheckBox.isChecked());

        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
