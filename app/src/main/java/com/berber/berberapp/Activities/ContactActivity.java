package com.berber.berberapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.berber.berberapp.Fragments.ContactFragment;
import com.berber.berberapp.Network.DataManager;
import com.berber.berberapp.R;
import com.berber.berberapp.Utils;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEditText = findViewById(R.id.name_edittext_contact_activity);
        emailEditText = findViewById(R.id.email_edittext_contact_activity);
        messageEditText = findViewById(R.id.message_edittext_contact_activity);
        sendButton = findViewById(R.id.send_button_contact_activity);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();

                if (!name.isEmpty() || !email.isEmpty() || !message.isEmpty()) {
                    if (Utils.isValidEmail(email)) {
                        // Call API and send message
                        Toast.makeText(ContactActivity.this, "Mandando mensaje...", Toast.LENGTH_SHORT).show();
                        sendButton.setEnabled(false);
                        DataManager.getSharedInstance().getApiService().sendContactEmail(email, name, message).enqueue(contactEmailCallback);
                    }
                    else {
                        Toast.makeText(ContactActivity.this, "El email que escribiste no es válido.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ContactActivity.this, "Ops! Creo que te faltó añadir algo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    final Callback<ResponseBody> contactEmailCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                Toast.makeText(ContactActivity.this, "Mensaje enviado!", Toast.LENGTH_SHORT).show();
            }
            else {
                new MaterialStyledDialog.Builder(ContactActivity.this)
                        .setTitle("No se mandó mensaje")
                        .setDescription("Lo sentimos, no se pude enviar el mensaje, intenta más tarde.")
                        .setStyle(Style.HEADER_WITH_ICON)
                        .setHeaderColor(R.color.colorButtonBackground)
                        .setPositiveText("Aceptar")
                        .show();
            }
            sendButton.setEnabled(true);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            new MaterialStyledDialog.Builder(ContactActivity.this)
                    .setTitle("No se mandó mensaje")
                    .setDescription("Lo sentimos, no se pude enviar el mensaje, intenta más tarde.")
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setHeaderColor(R.color.colorButtonBackground)
                    .setPositiveText("Aceptar")
                    .show();
            sendButton.setEnabled(true);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }}
