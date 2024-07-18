package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.CategoriesAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class DanhMuc extends AppCompatActivity {

    private LinearLayout btnChiTieu;
    private LinearLayout btnThuNhap;
    private RecyclerView categoryList;
    private CategoriesAdapter categoriesAdapter;
    private int currentType = 0;  // 0 cho chi tiêu, 1 cho thu nhập
    private static final int REQUEST_ADD_CATEGORY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        btnChiTieu = findViewById(R.id.btn_chi_tieu);
        btnThuNhap = findViewById(R.id.btn_thu_nhap);
        categoryList = findViewById(R.id.categoryList);

        // Set up RecyclerView
        categoryList.setLayoutManager(new LinearLayoutManager(this));
        categoriesAdapter = new CategoriesAdapter(this, CategoriesModel.getCategoriesByTypeAndUserId(this, currentType, getUserId()));
        categoryList.setAdapter(categoriesAdapter);

        // Mặc định chọn "Chi tiêu"
        setButtonSelected(btnChiTieu);
        setButtonUnselected(btnThuNhap);

        btnChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnChiTieu);
                setButtonUnselected(btnThuNhap);
                currentType = 0;
                updateCategoryList();
            }
        });

        btnThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnThuNhap);
                setButtonUnselected(btnChiTieu);
                currentType = 1;
                updateCategoryList();
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
                intent.putExtra("CURRENT_TYPE", currentType);
                intent.putExtra("USER_ID", getUserId());
                startActivityForResult(intent, REQUEST_ADD_CATEGORY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CATEGORY && resultCode == RESULT_OK) {
            // Reload category list
            updateCategoryList();
        }
    }

    private void updateCategoryList() {
        List<CategoriesModel> categoryListData = CategoriesModel.getCategoriesByTypeAndUserId(this, currentType, getUserId());
        categoriesAdapter.setCategoryList(categoryListData);
    }

    private void setButtonSelected(LinearLayout button) {
        button.setBackgroundResource(R.color.selectedColor);
    }

    private void setButtonUnselected(LinearLayout button) {
        button.setBackgroundResource(R.color.unselectedColor);
    }

    private int getUserId() {
        return 1;
    }
}
