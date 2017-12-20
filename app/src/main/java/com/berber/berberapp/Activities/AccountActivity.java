package com.berber.berberapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.berber.berberapp.Constants;
import com.berber.berberapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button signOutButton = findViewById(R.id.button_log_out_account_activity);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signOutButton.setText("Iniciar Sesión");
        }
    }

    public void onLogOutAction(View view) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            FirebaseAuth.getInstance().signOut();
            finish();
        }
    }

    public void onContactAction(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void onFAQAction(View view) {
        Intent intent = new Intent(this, FAQActivity.class);
        startActivity(intent);
    }

    public void onFacebookAction(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FACEBOOK_URL));
        startActivity(browserIntent);
    }

    public void onWebsiteAction(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEBSITE_URL));
        startActivity(browserIntent);
    }
}
