package com.berber.berberapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.berber.berberapp.Constants;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.Models.Coupon;
import com.berber.berberapp.Network.DataManager;
import com.berber.berberapp.Models.Order;
import com.berber.berberapp.R;
import com.berber.berberapp.Models.User;
import com.berber.berberapp.Models.Vendor;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SummaryActivity extends AppCompatActivity {

    private String phone;
    private String address;
    private String email;
    private String addressZone;
    private String paymentType;
    private HashMap<Beer, Integer> order;
    private Double total;
    private boolean willRetrievePackage;
    private ArrayList<Vendor> vendors;
    private Coupon coupon;
    private Boolean isTempCold;
    private String orderId;

    private TextView mVendorTextView;
    private TextView mSummaryOrderTextview;
    private ImageView mVendorImgView;
    private Button mOrderButton;

    private User userDB;
    private FirebaseUser user;
    private Vendor vendorToUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mVendorTextView = findViewById(R.id.vendor_text_view_summary_activity);
        mSummaryOrderTextview = findViewById(R.id.summary_order_textview_summary_activity);
        mVendorImgView = findViewById(R.id.vendor_imgview_summary_activity);
        mOrderButton = findViewById(R.id.order_button_summary_activity);

        mOrderButton.setEnabled(false);

        coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        phone = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        addressZone = getIntent().getStringExtra("addressType");
        paymentType = getIntent().getStringExtra("paymentType");
        order = (HashMap<Beer, Integer>) getIntent().getSerializableExtra("order");
        total = getIntent().getDoubleExtra("total", 0);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        willRetrievePackage = getIntent().getBooleanExtra("willRetrieve", false);
        isTempCold = getIntent().getBooleanExtra("temperature_cold", true);
        Log.wtf("SumAct", "TEMPERATURE IS: COLD?: " + isTempCold);


        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    userDB = new User();
                    orderId = (user.getUid().substring(0, 4) + "-0");
                    mSummaryOrderTextview.setText("Tu pedido por la cantidad $" + total + ", el número de pedido: " + orderId);
                }
                else {
                    userDB = dataSnapshot.getValue(User.class);
                    orderId = (user.getUid().substring(0, 4) + "-" + userDB.getOrdersMade());
                    mSummaryOrderTextview.setText("Tu pedido por la cantidad $" + total + ", el número de pedido: " + orderId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (!addressZone.equals("National")) {
            DatabaseReference mDatabaseVendors = FirebaseDatabase.getInstance().getReference();
            mDatabaseVendors = mDatabaseVendors.child("vendors");

            mDatabaseVendors.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<ArrayList<Vendor>> t = new GenericTypeIndicator<ArrayList<Vendor>>() {};
                    vendors = dataSnapshot.getValue(t);

                    for (Vendor vendor : vendors) {
                        if (vendor.getZones().contains(addressZone)) {
                            vendorToUse = vendor;
                            mVendorTextView.setText("Tu vendedor es " + vendor.getName() + "\n" + vendor.getPhone());

                            if (!vendor.getImgUrl().equals("")) {
                                Glide.with(SummaryActivity.this)
                                        .load(vendor.getImgUrl())
                                        .into(mVendorImgView);

                                mOrderButton.setEnabled(true);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            mOrderButton.setEnabled(true);
            mVendorTextView.setText("Tu pedido es nacional, no se puede asignar vendedor.");
        }
    }

    public void OnSendOrderAction(View view) {
        Toast.makeText(this, "Se está enviando el pedido, espera por favor.", Toast.LENGTH_SHORT).show();
        view.setEnabled(false);

        String orderStr = "";
        ArrayList<Beer> beers = new ArrayList<>(order.keySet());
        ArrayList<Integer> amount = new ArrayList<>(order.values());
        HashMap<String, Integer> orderCart = new HashMap<>();

        for (int i = 0; i < beers.size(); i++) {
            orderStr = orderStr + beers.get(i).getName() + "      " + amount.get(i) + "    =  $" + (beers.get(i).getPrice() * amount.get(i)) + " MXN";
            orderStr += "\n";
            orderCart.put(beers.get(i).getName(), amount.get(i));
        }
        orderStr += "\n\nTotal: $" + total + " MXN";



        String infoClient = "Cliente: \n--------------------------------\nTeléfono: " + phone + "\nEmail: " + email + "\nDireccion: " + address + "\nZona: " + addressZone + "\n\nTipo de pago: " + paymentType;
        if (willRetrievePackage) {
            infoClient += "\nRecogerá paquete: Sí";
        }
        else {
            infoClient += "\nRecogerá paquete: No";
        }

        if (coupon != null) {
            if (coupon.getType().equals(Constants.COUPON_DISCOUNT)) {
                infoClient += "\n\n\nCupón utilizado:" + "\n     - ID: " + coupon.getId() + "\n     - Tipo: " + coupon.getType() + "\n     - Descuento: %" + coupon.getAmount();
            }
            else if (coupon.getType().equals(Constants.COUPON_LESS_MONEY)) {
                infoClient += "\nCupón utilizado: \n - Tipo: " + coupon.getType() + "\n - Descuento: $" + coupon.getAmount() + " MXN";
            }
        }

        if (isTempCold) {
            Log.wtf("SumAct", "TEMPERATURE IS: COLD?: " + isTempCold);
            infoClient += "\nCervezas frías: Sí";
        }
        else {
            Log.wtf("SumAct", "TEMPERATURE IS WARM?: " + isTempCold);
            infoClient += "\nCervezas tíbias: Sí";
        }

        orderStr = infoClient + "\n\n\n\nOrden           (" + orderId + "):\n--------------------------------\n" + orderStr;



        final Order order = new Order();
        order.setAddress(address);
        order.setDeliveryZone(addressZone);
        order.setTotal(total);
        order.setCart(orderCart);

        if (coupon != null) {
            order.setCoupon(coupon);
        }

        if (userDB.getOrders() != null) {
            order.setOrderId(user.getUid().substring(0, 4) + "-" + userDB.getOrdersMade());
            userDB.getOrders().add(order);
            userDB.setOrdersMade(userDB.getOrdersMade() + 1);
        }
        else {
            order.setOrderId(user.getUid().substring(0, 4) + "-0");
            userDB.setOrders(new ArrayList<>(Arrays.asList(order)));
            userDB.setOrdersMade(1);
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        mDatabase.setValue(userDB);

        DataManager.getSharedInstance().getApiService().sendOrderEmail(email, orderStr, vendorToUse.getEmail()).enqueue(sendEmailCallback);
    }


    final Callback<ResponseBody> sendEmailCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            Toast.makeText(SummaryActivity.this, "La orden se ha enviado exitosamente.", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(SummaryActivity.this, "Hubo un problema enviando el email, intenta más tarde.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
