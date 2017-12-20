package com.berber.berberapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.berber.berberapp.Constants;
import com.berber.berberapp.Models.Beer;
import com.berber.berberapp.R;
import com.bumptech.glide.Glide;

public class BeerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Beer beer = (Beer) getIntent().getSerializableExtra("beer");

        TextView mBeerDescriptionTextView = findViewById(R.id.beer_description_textview_detail_activity);
        TextView mBeerPairingTextView = findViewById(R.id.beer_pairing_description_textview_detail_activity);
        ImageView mBeerImgView = findViewById(R.id.beer_imgview_detail_activity);
        TextView mBeerAlcoholicGradeTextView = findViewById(R.id.beer_alcoholic_grade_textview_detail_activity);
        TextView mBeerBitternessGradeTextView = findViewById(R.id.beer_bitterness_grade_textview_detail_activity);
        TextView mBeerMaltasTextView = findViewById(R.id.beer_malta_textview_detail_activity);
        TextView mBeerStyleTextView = findViewById(R.id.beer_style_textview_detail_activity);
        TextView mBeerFermentationTextView = findViewById(R.id.beer_fermentation_textview_detail_activity);

        RelativeLayout mBeerBottlePresentationLayout = findViewById(R.id.beer_bottle_layout_detail_activity);
        RelativeLayout mBeerCanPresentationLayout = findViewById(R.id.beer_can_layout_detail_activity);
        RelativeLayout mBeerBarrelPresentationLayout = findViewById(R.id.beer_barrel_layout_detail_activity);

        mBeerDescriptionTextView.setText(beer.getDescription());
        mBeerPairingTextView.setText(beer.getPairing());
        Glide.with(this)
                .load(beer.getImgUrl())
                .into(mBeerImgView);

        if (beer.getAlcoholic_grade() != null) {
            mBeerAlcoholicGradeTextView.setText("Grado Alcohólico: " + beer.getAlcoholic_grade() + "% volumen");
        }
        if (beer.getBitterness_units() != -1) {
            mBeerBitternessGradeTextView.setText("Unidades de Amargor: " + beer.getBitterness_units() + " IBU");
        }
        if (beer.getMalta() != null) {
            mBeerMaltasTextView.setText("Maltas: " + beer.getMalta());
        }
        if (beer.getStyle() != null) {
            mBeerStyleTextView.setText("Estilo: " + beer.getStyle());
        }
        if (beer.getFermentation() != null) {
            mBeerFermentationTextView.setText("Fermentación: " + beer.getFermentation());
        }

        if (beer.getPresentations() != null && beer.getPresentations().contains(Constants.BEER_BOTTLE)) {
            mBeerBottlePresentationLayout.setVisibility(View.VISIBLE);
        }
        if (beer.getPresentations() != null && beer.getPresentations().contains(Constants.BEER_CAN)) {
            mBeerCanPresentationLayout.setVisibility(View.VISIBLE);
        }
        if (beer.getPresentations() != null && beer.getPresentations().contains(Constants.BEER_BARREL)) {
            mBeerBarrelPresentationLayout.setVisibility(View.VISIBLE);
        }
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
