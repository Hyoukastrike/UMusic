package com.example.umusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.umusic.fragment.PersonalFragmentx;

public class LoginScreen extends AppCompatActivity {

    ImageView btnBack;
    Button btnRegisterPhone, btnRegisterEmail, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        btnRegisterPhone = findViewById(R.id.btn_register_phone);
        btnRegisterPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, VerifyPhoneNumber.class);
                startActivity(intent);
            }
        });

        btnRegisterEmail = findViewById(R.id.btn_register_email);
        btnRegisterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, SignInEmail.class);
                startActivity(intent);
            }
        });
    }
}