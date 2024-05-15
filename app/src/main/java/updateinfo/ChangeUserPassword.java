package updateinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umusic.R;
import com.example.umusic.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeUserPassword extends AppCompatActivity {

    private EditText etChangeEmail;
    private Button btnChangeEmail;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (etChangeEmail.getText().toString().isEmpty()){
                    tvError.setText("Không được để trống miền này");
                    tvError.setTextColor(R.color.red);
                }
                else {
                    onClickUpdateProfile();
                }
            }
        });

    }

    private void InitUi() {
        etChangeEmail = findViewById(R.id.et_changepass);
        btnChangeEmail = findViewById(R.id.btn_changepass);
        tvError = findViewById(R.id.tv_error);
    }
    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){
            return;
        }
        String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ChangeUserPassword.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ChangeUserPassword.this,"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
