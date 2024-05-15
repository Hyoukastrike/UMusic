package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {
    private static final String TAG = VerifyPhoneNumber.class.getName();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        setTitleToolBar();

        final EditText etInputPhone = findViewById(R.id.et_inputphoneregis);
        Button btnVerifyPhone = findViewById(R.id.btn_verifyphone);

        mAuth = FirebaseAuth.getInstance();

        ProgressBar progressBar = findViewById(R.id.progressBar);

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        btnVerifyPhone.setOnClickListener(v -> {
            if (etInputPhone.getText().toString().trim().isEmpty()){
                Toast.makeText(VerifyPhoneNumber.this, "Vui lòng điền số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            btnVerifyPhone.setVisibility(View.INVISIBLE);

            onClickVerifyPhoneNumber(etInputPhone,progressBar,btnVerifyPhone);

        });
    }

    private void onClickVerifyPhoneNumber(EditText etInputPhone, ProgressBar progressBar, Button btnVerifyPhone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + etInputPhone.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnVerifyPhone.setVisibility(View.VISIBLE);
                                signInWithPhoneAuthCredential(phoneAuthCredential, etInputPhone);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressBar.setVisibility(View.GONE);
                                btnVerifyPhone.setVisibility(View.VISIBLE);
                                Toast.makeText(VerifyPhoneNumber.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(VerificationId, forceResendingToken);
                                progressBar.setVisibility(View.GONE);
                                btnVerifyPhone.setVisibility(View.VISIBLE);
                                gotoVerifyOPT(etInputPhone, VerificationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, EditText etInputPhone) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e(TAG, "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        // Update UI
                        gotoMainActivityx(etInputPhone);
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(VerifyPhoneNumber.this,"Mã OTP không hợp lệ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotoVerifyOPT(EditText etInputPhone, String verificationId) {
        Intent intent = new Intent(VerifyPhoneNumber.this, VerifyOTP.class);
        intent.putExtra("phonenumber", etInputPhone.getText().toString());
        intent.putExtra("verificationId", verificationId);
        startActivity(intent);
    }

    private void gotoMainActivityx(EditText etInputPhone) {
        Intent intent = new Intent(VerifyPhoneNumber.this, MainActivityx.class);
        intent.putExtra("phonenumber", etInputPhone.getText().toString());
        startActivity(intent);
    }

    private void setTitleToolBar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Verify Phone Number");
        }
    }

}