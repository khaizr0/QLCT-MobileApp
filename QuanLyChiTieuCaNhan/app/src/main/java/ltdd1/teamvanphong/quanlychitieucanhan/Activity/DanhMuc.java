package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class DanhMuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_muc);

        ImageView icBack = findViewById(R.id.ic_back);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout addCategoryLayout = findViewById(R.id.addCategoryLayout);
        addCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhMuc.this, ThemDanhMuc.class);
                startActivity(intent);
            }
        });
    }
}
