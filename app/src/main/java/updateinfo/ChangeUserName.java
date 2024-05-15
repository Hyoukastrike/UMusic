package updateinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umusic.R;
import com.example.umusic.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeUserName extends AppCompatActivity {
    private EditText etChangeName;
    private Button btnChangeName;
    private TextView tvError;
    private ImageView btnBack;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);

        InitUi();

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etChangeName.getText().toString().isEmpty()){
                    tvError.setText("Không được để trống miền này");
                    tvError.setTextColor(R.color.red);
                }
                else {
                    onClickUpdateProfile();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void InitUi() {
        etChangeName = findViewById(R.id.et_changename);
        btnChangeName = findViewById(R.id.btn_changename);
        tvError = findViewById(R.id.tv_error);
        btnBack = findViewById(R.id.btn_back);
    }

    private void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            return;
        }

        String strName = etChangeName.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ChangeUserName.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChangeUserName.this,"Thay đổi tên thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}