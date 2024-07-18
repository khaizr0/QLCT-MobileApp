package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

//Tien
public class checkInfo_resetPass extends AppCompatActivity {
    private EditText editTextUsername, editTextEmail;
    private Button buttonConfirm;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info_reset_pass);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        textViewLogin = findViewById(R.id.textViewLogin);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty()) {
                    Toast.makeText(checkInfo_resetPass.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (UserModel.validateUser(checkInfo_resetPass.this, username, email)) {
                        Intent intent = new Intent(checkInfo_resetPass.this, EnterNewPassword.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(checkInfo_resetPass.this, "Tên đăng nhập hoặc email không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(checkInfo_resetPass.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
