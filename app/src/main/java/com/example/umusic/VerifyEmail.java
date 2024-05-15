package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity {
    private static final String TAG = VerifyEmail.class.getName();
    private EditText etEmail,etPassword, etCPassword;
    private Button btnSignup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        InitUi();
        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp(progressBar,btnSignup);
            }
        });
    }

    private void InitUi(){
        etEmail = findViewById(R.id.et_inputEmail);
        etPassword = findViewById(R.id.et_inputPassword);
        etCPassword = findViewById(R.id.et_inputConfirmPassword);
        btnSignup = findViewById(R.id.btn_signupemail);
        progressBar = findViewById(R.id.progressBar);

    }

    private void onClickSignUp(ProgressBar progressBar, Button btnSignup) {
        String strEmail = etEmail.getText().toString().trim();
        final String strPassword = etPassword.getText().toString();
        String strCPassword = etCPassword.getText().toString().trim();
        if (TextUtils.isEmpty(strEmail)){
            Toast.makeText(VerifyEmail.this,"Vui lòng nhập Email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strPassword)){
            Toast.makeText(VerifyEmail.this,"Vui lòng nhập Mật khẩu",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strCPassword)){
            Toast.makeText(VerifyEmail.this,"Vui lòng điền xác nhận lại Mật khẩu",Toast.LENGTH_SHORT).show();
        }
        else if (!strPassword.equals(strCPassword)){
            Toast.makeText(VerifyEmail.this,"Mật khẩu xác nhận phải giống với Mật khẩu đăng ký",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(strEmail, strCPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressBar.setVisibility(View.VISIBLE);
                                btnSignup.setVisibility(View.INVISIBLE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(VerifyEmail.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyEmail.this,MainActivityx.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(VerifyEmail.this, "Xác thực không thành công",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}