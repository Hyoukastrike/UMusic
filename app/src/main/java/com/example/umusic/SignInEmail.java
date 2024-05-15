package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInEmail extends AppCompatActivity {
    private TextView btnSignUp;
    private Button btnSignIn;
    private FirebaseAuth mAuth;
    private ImageView btnBack;
    private EditText etInputEmail, etInputPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_email);

        InitUi();
        mAuth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInEmail.this, VerifyEmail.class);
                startActivity(intent);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });

    }

    private void InitUi(){
        btnSignUp = findViewById(R.id.btn_signup);
        btnSignIn = findViewById(R.id.btn_loginemail);
        btnBack = findViewById(R.id.btn_back);
        etInputEmail = findViewById(R.id.et_inputEmail);
        etInputPassword = findViewById(R.id.et_inputPassword);
        progressBar = findViewById(R.id.progressBar);
    }

    private void onClickSignIn() {
        String strEmail = etInputEmail.getText().toString().trim();
        final String strPassword = etInputPassword.getText().toString();

        if (TextUtils.isEmpty(strEmail)){
            Toast.makeText(SignInEmail.this,"Vui lòng nhập Email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strPassword)){
            Toast.makeText(SignInEmail.this,"Vui lòng nhập Mật khẩu",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressBar.setVisibility(View.VISIBLE);
                                btnSignIn.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(SignInEmail.this,MainActivityx.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInEmail.this, "Xác thực tài khoản không thành công",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }
}