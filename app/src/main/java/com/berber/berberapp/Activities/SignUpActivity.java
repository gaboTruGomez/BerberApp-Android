package com.berber.berberapp.Activities;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.berber.berberapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ImageView berberLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ViewGroup rootView = findViewById(R.id.main_layout_sign_up_activity);
        LayoutTransition layoutTransition = rootView.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        emailEditText = findViewById(R.id.email_sign_up_textview);
        usernameEditText = findViewById(R.id.username_sign_up_textview);
        passwordEditText = findViewById(R.id.password_sign_up_textview);
        berberLogoImageView = findViewById(R.id.berber_logo_imageview_signup_activity);

        mAuth = FirebaseAuth.getInstance();

        /*
        emailEditText.setOnFocusChangeListener(this);
        usernameEditText.setOnFocusChangeListener(this);
        passwordEditText.setOnFocusChangeListener(this);
        */
    }

    /*
    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            berberLogoImageView.setVisibility(View.GONE);
        }else {
            berberLogoImageView.setVisibility(View.VISIBLE);
        }
    }
    */

    public void onSignInAction(View view) {
        this.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void onSignUpAction(View view) {
        if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (isEmailValid(email)) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.wtf("SignUpAct", "task: " + task.getResult().toString() + " - " + task.isSuccessful());

                            Intent intent = new Intent();
                            intent.putExtra("createdAccount", true);
                            setResult(RESULT_OK, intent);

                            SignUpActivity.this.finish();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "No se pudo crear usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
