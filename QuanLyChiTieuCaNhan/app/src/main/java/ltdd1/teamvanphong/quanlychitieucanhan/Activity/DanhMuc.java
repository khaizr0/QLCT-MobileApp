package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class DanhMuc extends AppCompatActivity {

    private LinearLayout btnChiTieu;
    private LinearLayout btnThuNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_muc);

        btnChiTieu = findViewById(R.id.btn_chi_tieu);
        btnThuNhap = findViewById(R.id.btn_thu_nhap);

        // Mặc định chọn "Chi tiêu"
        setButtonSelected(btnChiTieu);
        setButtonUnselected(btnThuNhap);

        btnChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnChiTieu);
                setButtonUnselected(btnThuNhap);
            }
        });

        btnThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnThuNhap);
                setButtonUnselected(btnChiTieu);
            }
        });

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

    private void setButtonSelected(LinearLayout button) {
        button.setBackgroundColor(getResources().getColor(R.color.selectedColor));
    }

    private void setButtonUnselected(LinearLayout button) {
        button.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
    }
}
