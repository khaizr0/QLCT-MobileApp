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

public class SignUp extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextEmail, editTextSDT;
    private Button buttonSignUp;
    private TextView textViewLogin,textViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSDT = findViewById(R.id.editTextSDT);
        buttonSignUp = findViewById(R.id.buttonLogin);
        textViewLogin = findViewById(R.id.textViewLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        buttonSignUp.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String email = editTextEmail.getText().toString();
            String phone = editTextSDT.getText().toString();

            int gender = 1;

            boolean isRegistered = UserModel.registerUser(this, username, email, password, gender, phone);

            if (isRegistered) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, checkInfo_resetPass.class);
                startActivity(intent);
            }
        });
    }
}
