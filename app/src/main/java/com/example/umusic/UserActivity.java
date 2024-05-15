package com.example.umusic;

import static android.app.PendingIntent.getActivity;

import static com.example.umusic.MainActivityx.MY_REQUEST_CODE;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umusic.fragment.PersonalFragmentx;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import updateinfo.ChangeUserName;

public class UserActivity extends AppCompatActivity {

    private ImageView imgAva, btnBack, btnName;
    private TextView tvName, tvEmail;
    private Button btnSignOut, btnChangeAva, btnUpdateImage;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        InitUi();
        //Trở lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivityx.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        showUserInformation();

        //Đăng xuất
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Đổi ảnh đại diện
        btnChangeAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

                btnChangeAva.setVisibility(View.INVISIBLE);
                btnUpdateImage.setVisibility(View.VISIBLE);
            }
        });
        //Cập nhật ảnh
        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
                btnUpdateImage.setVisibility(View.GONE);
                btnChangeAva.setVisibility(View.VISIBLE);
            }
        });
        //Đổi tên
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ChangeUserName.class);
                startActivity(intent);
            }
        });

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ChangeUserName.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imgAva.setImageURI(uri);
        setUri(uri);
    }

    private void InitUi() {
        imgAva = findViewById(R.id.image_ava);
        btnBack = findViewById(R.id.btn_back);
        btnChangeAva = findViewById(R.id.btn_change_ava);
        btnUpdateImage = findViewById(R.id.btn_update);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        btnSignOut = findViewById(R.id.btn_signout);
        btnName = findViewById(R.id.btn_name);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    @SuppressLint("SetTextI18n")
    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null){
            tvName.setText("Nhập tên");
            tvName.setVisibility(View.VISIBLE);
        }
        else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
        }
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.default_avatar).into(imgAva);
    }

    private void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            return;
        }

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserActivity.this,"Đã cập nhật thành công",Toast.LENGTH_SHORT).show();
                            showUserInformation();
                        }
                    }
                });
    }



}