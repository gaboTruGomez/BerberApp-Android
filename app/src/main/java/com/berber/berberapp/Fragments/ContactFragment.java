package com.berber.berberapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.berber.berberapp.R;
import com.berber.berberapp.Utils;

public class ContactFragment extends Fragment {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText messageEditText;
    private Button sendButton;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        nameEditText = view.findViewById(R.id.name_edittext_contact_fragment);
        emailEditText = view.findViewById(R.id.email_edittext_contact_fragment);
        messageEditText = view.findViewById(R.id.message_edittext_contact_fragment);
        sendButton = view.findViewById(R.id.send_button_contact_fragment);
        
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();
                
                if (!name.isEmpty() || !email.isEmpty() || !message.isEmpty()) {
                    if (Utils.isValidEmail(email)) {
                        // Call API and send message
                        Toast.makeText(getContext(), "Mensaje enviado!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "El email que escribiste no es válido.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Ops! Creo que te faltó añadir algo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
