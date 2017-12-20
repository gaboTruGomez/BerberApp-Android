package com.berber.berberapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.berber.berberapp.Constants;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.Models.Coupon;
import com.berber.berberapp.R;

import java.util.HashMap;

public class SendActivity extends AppCompatActivity {

    private HashMap<Beer, Integer> cart;
    private Double total;
    private Coupon coupon;

    private EditText mAddressEditText;
    private EditText mPhoneEditText;
    private Spinner mAddressSpinner;
    private Spinner mPaymentMethodSpinner;
    private LinearLayout mZoneLayout;
    private RadioGroup mDeliveryRadioGroup;
    private RelativeLayout deliverPackageLayout;
    private CheckBox deliverPackageCheckBox;
    private LinearLayout temperatureTypeLayout;
    private Spinner temperatureTypeSpinner;
    //private PlaceAutocompleteFragment placeAutocompleteFragment;
    //private LinearLayout mPlaceAutocompleteLayout;

    private boolean isLocalDelivery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        cart = (HashMap<Beer, Integer>) getIntent().getSerializableExtra("order");
        total = getIntent().getDoubleExtra("total", 0);
        coupon = (Coupon) getIntent().getSerializableExtra("coupon");

        isLocalDelivery = true;

        mAddressEditText = findViewById(R.id.address_edittext_send_activity);
        mPhoneEditText = findViewById(R.id.phone_edittext_send_activity);
        mAddressSpinner = findViewById(R.id.address_spinner_send_activity);
        mPaymentMethodSpinner = findViewById(R.id.payment_type_spinner_send_activity);
        mZoneLayout = findViewById(R.id.delivery_zone_layout_send_activity);
        mDeliveryRadioGroup = findViewById(R.id.delivery_radiogroup_send_activity);
        deliverPackageLayout = findViewById(R.id.deliver_package_layout_send_activity);
        deliverPackageCheckBox = findViewById(R.id.deliver_package_checkbox_send_activity);
        temperatureTypeLayout = findViewById(R.id.temperature_type_layout_send_activity);
        temperatureTypeSpinner = findViewById(R.id.temperature_type_spinner_send_activity);
        //placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.idDireccio.place_autocomplete_fragment);
        //mPlaceAutocompleteLayout = findViewById(R.id.place_autocomplete_fragment_layout);

        mDeliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.local_delivery_radiobutton_send_activity:
                        mZoneLayout.setVisibility(View.VISIBLE);
                        if (mAddressSpinner.getSelectedItem().toString().equals("Guadalajara")) {
                            deliverPackageLayout.setVisibility(View.VISIBLE);
                        }
                        temperatureTypeLayout.setVisibility(View.VISIBLE);
                        //mPlaceAutocompleteLayout.setVisibility(View.VISIBLE);
                        isLocalDelivery = true;
                        break;
                    case R.id.national_delivery_radiobutton_send_activity:
                        mZoneLayout.setVisibility(View.GONE);
                        deliverPackageLayout.setVisibility(View.GONE);
                        temperatureTypeLayout.setVisibility(View.GONE);
                        //mPlaceAutocompleteLayout.setVisibility(View.GONE);
                        isLocalDelivery = false;
                        break;
                }
            }
        });

        /*
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.wtf("SendAct", "Place: " + place.getName() + " - " + place.getLatLng().latitude);
            }

            @Override
            public void onError(Status status) {

            }
        });
        */

        mAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    deliverPackageLayout.setVisibility(View.VISIBLE);
                }
                else {
                    deliverPackageLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void onBuyAction(View v) {
        String phone = mPhoneEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        String addressZone = (String) mAddressSpinner.getSelectedItem();
        String paymentType = (String) mPaymentMethodSpinner.getSelectedItem();

        
        if (phone.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu teléfono", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu dirección", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        Intent intentAct = new Intent(this, SummaryActivity.class);

        if (coupon != null) {
            intentAct.putExtra("coupon", coupon);
        }
        intentAct.putExtra("phone", phone);
        intentAct.putExtra("address", address);
        intentAct.putExtra("order", cart);
        intentAct.putExtra("total", total);

        if ((temperatureTypeSpinner.getSelectedItem()).equals(Constants.TEMP_COLD)) {
            intentAct.putExtra("temperature_cold", true);
        }
        else {
            intentAct.putExtra("temperature_cold", false);
        }

        if (isLocalDelivery) {
            intentAct.putExtra("addressType", addressZone);

            if ((temperatureTypeSpinner.getSelectedItem()).equals(Constants.TEMP_COLD)) {
                intentAct.putExtra("temperature_cold", true);
            }
            else {
                intentAct.putExtra("temperature_cold", false);
            }
        }
        else {
            intentAct.putExtra("addressType", "National");
            intentAct.putExtra("temperature_cold", true);
        }

        if (addressZone.equals("Guadalajara")) {
            intentAct.putExtra("willRetrieve", deliverPackageCheckBox.isChecked());
        }
        else {
            intentAct.putExtra("willRetrieve", false);
        }

        intentAct.putExtra("paymentType", paymentType);

        startActivity(intentAct);
        this.finish();

    }
}

