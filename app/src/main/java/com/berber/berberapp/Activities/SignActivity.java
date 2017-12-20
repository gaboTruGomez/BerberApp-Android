package com.berber.berberapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.berber.berberapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailEditTxt;
    private EditText mPasswordEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mEmailEditTxt = (EditText) findViewById(R.id.email_sign_in_textview);
        mPasswordEditTxt = (EditText) findViewById(R.id.password_sign_in_textview);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUpBtnAction(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void signInBtnAction(View view) {
        if (!mEmailEditTxt.getText().toString().isEmpty() && !mPasswordEditTxt.getText().toString().isEmpty()) {

            String email = mEmailEditTxt.getText().toString();
            String password = mPasswordEditTxt.getText().toString();

            if (isEmailValid(email)) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // signin was successfull
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("SignInAct", "USER: " + user.getEmail() + " - " + user.getUid());
                            SignActivity.this.finish();
                        }
                        else {
                            Toast.makeText(SignActivity.this, "User was not found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getBooleanExtra("createdAccount", false)) {
                Toast.makeText(this, "Usuario creado!", Toast.LENGTH_SHORT).show();
                SignActivity.this.finish();
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
