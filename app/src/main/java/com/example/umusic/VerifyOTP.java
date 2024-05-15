package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    private static final String TAG = VerifyOTP.class.getName();

    private EditText etInputOTP1, etInputOTP2, etInputOTP3, etInputOTP4, etInputOTP5, etInputOTP6;
    private String verificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        setTitleToolBar();

        TextView tv_phonenumber = findViewById(R.id.tv_notephone);
        TextView tv_sendotpagain = findViewById(R.id.tv_send_otp_again);
        mAuth = FirebaseAuth.getInstance();
        tv_phonenumber.setText(String.format(
                "+84-%s", getIntent().getStringExtra("phonenumber")
        ));

        etInputOTP1 = findViewById(R.id.et_inputotp1);
        etInputOTP2 = findViewById(R.id.et_inputotp2);
        etInputOTP3 = findViewById(R.id.et_inputotp3);
        etInputOTP4 = findViewById(R.id.et_inputotp4);
        etInputOTP5 = findViewById(R.id.et_inputotp5);
        etInputOTP6 = findViewById(R.id.et_inputotp6);

        setUpOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Button btnVerifyOTP = findViewById(R.id.btn_verifyotp);


        verificationId = getIntent().getStringExtra("verificationId");
        btnVerifyOTP.setOnClickListener(v -> {

            if (etInputOTP1.getText().toString().trim().isEmpty()
            || etInputOTP2.getText().toString().trim().isEmpty()
            || etInputOTP3.getText().toString().trim().isEmpty()
            || etInputOTP4.getText().toString().trim().isEmpty()
            || etInputOTP5.getText().toString().trim().isEmpty()
            || etInputOTP6.getText().toString().trim().isEmpty()){
                Toast.makeText(VerifyOTP.this,"Nhập mã vào ô trống", Toast.LENGTH_SHORT).show();
                return;
            }
            String code =
                    etInputOTP1.getText().toString() +
                            etInputOTP2.getText().toString() +
                            etInputOTP3.getText().toString() +
                            etInputOTP4.getText().toString() +
                            etInputOTP5.getText().toString() +
                            etInputOTP6.getText().toString();

            if (verificationId != null){
                progressBar.setVisibility(View.VISIBLE);
                btnVerifyOTP.setVisibility(View.INVISIBLE);
                onClickSendOTPCode(code);
            }
        });

        tv_sendotpagain.setOnClickListener(v -> {
            onClickSendOTPAgain();
            tv_sendotpagain.setText("Đã gửi lại mã");
        });
    }

    private void setUpOTPInputs(){
        etInputOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    etInputOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etInputOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    etInputOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etInputOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    etInputOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etInputOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    etInputOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etInputOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    etInputOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onClickSendOTPCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void onClickSendOTPAgain(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + getIntent().getStringExtra("phonenumber"))       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToken)// (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTP.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(VerificationId, forceResendingToken);
                                verificationId = VerificationId;
                                mForceResendingToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        // Update UI
                        assert user != null;
                        gotoMainActivityx(user.getPhoneNumber());
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(VerifyOTP.this,"Mã OTP không hợp lệ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotoMainActivityx(String phoneNumber) {
        Intent intent = new Intent(this, MainActivityx.class);
        intent.putExtra("phonenumber", phoneNumber);
        startActivity(intent);
    }

    private void setTitleToolBar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Verify OTP");
        }
    }


}