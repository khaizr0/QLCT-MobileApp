package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

//Tien
public class EnterNewPassword extends AppCompatActivity {
    private EditText editTextNewPassword, editTextConfirmPassword;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_password);

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        String username = getIntent().getStringExtra("username");

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = editTextNewPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(EnterNewPassword.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(EnterNewPassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    if (UserModel.updatePassword(EnterNewPassword.this, username, newPassword)) {
                        Toast.makeText(EnterNewPassword.this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EnterNewPassword.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EnterNewPassword.this, "Đặt lại mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
