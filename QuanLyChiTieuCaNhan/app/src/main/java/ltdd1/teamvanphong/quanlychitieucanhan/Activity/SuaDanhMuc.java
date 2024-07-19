package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ColorSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.IconSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.ColorItem;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IconItem;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class SuaDanhMuc extends AppCompatActivity {

    private Spinner spinnerIcon;
    private Spinner spinnerColor;
    private ImageView iconPreview;
    private EditText txtCategoryName;
    private int currentType;
    private int userId;
    private ImageView icBack;
    private LinearLayout btnSave;
    private ImageButton btnDelete;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_danh_muc);

        // Retrieve user ID from session
        UserModel session = UserModel.getSessionUser();
        userId = session.getUserId();

        icBack = findViewById(R.id.btn_back);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerIcon = findViewById(R.id.spinner_icon);
        spinnerColor = findViewById(R.id.spinner_color);
        iconPreview = findViewById(R.id.icon_preview);
        txtCategoryName = findViewById(R.id.txt_category_name);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        // Setup icon spinner
        setupIconSpinner();

        // Setup color spinner
        setupColorSpinner();

        // Get categoryId from Intent
        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        // Load category data from database
        loadCategoryData(categoryId);

        // Set listener for save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });

        // Set listener for delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory();
            }
        });
    }

    // Load category data from database
    private void loadCategoryData(int categoryId) {
        // Query database for category data
        ExpenseDB dbHelper = new ExpenseDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("Categories", null, "CategoryID=?", new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor.moveToFirst()) {
            String categoryName = cursor.getString(cursor.getColumnIndex("CategoryName"));
            String iconName = cursor.getString(cursor.getColumnIndex("IconName"));
            String color = cursor.getString(cursor.getColumnIndex("Color"));

            // Add log to check the values
            Log.d("SuaDanhMuc", "Category Name: " + categoryName);
            Log.d("SuaDanhMuc", "Icon Name: " + iconName);
            Log.d("SuaDanhMuc", "Color: " + color);

            txtCategoryName.setText(categoryName);

            // Select the correct icon in spinner
            for (int i = 0; i < spinnerIcon.getCount(); i++) {
                IconItem iconItem = (IconItem) spinnerIcon.getItemAtPosition(i);
                if (iconItem.getName().equals(iconName)) {
                    spinnerIcon.setSelection(i);
                    break;
                }
            }

            // Select the correct color in spinner
            for (int i = 0; i < spinnerColor.getCount(); i++) {
                ColorItem colorItem = (ColorItem) spinnerColor.getItemAtPosition(i);
                if (colorItem.getColorHex().equals(color)) {
                    spinnerColor.setSelection(i);
                    break;
                }
            }

            // Update icon preview
            updateIconColor();
        }

        cursor.close();
        db.close();
    }

    private void setupIconSpinner() {
        List<IconItem> icons = Arrays.asList(
                new IconItem(R.drawable.ic_cate_food, "Icon 1"),
                new IconItem(R.drawable.ic_cate_cafe, "Icon 2"),
                new IconItem(R.drawable.ic_cate_cart, "Icon 3"),
                new IconItem(R.drawable.ic_cate_plane, "Icon 4"),
                new IconItem(R.drawable.ic_cate_hospital, "Icon 5"),
                new IconItem(R.drawable.ic_cate_book, "Icon 6"),
                new IconItem(R.drawable.ic_cate_house, "Icon 7"),
                new IconItem(R.drawable.ic_cate_phone, "Icon 8"),
                new IconItem(R.drawable.ic_cate_car, "Icon 9"),
                new IconItem(R.drawable.ic_cate_work, "Icon 10"),
                new IconItem(R.drawable.ic_cate_theater, "Icon 11")
        );
        IconSpinnerAdapter iconAdapter = new IconSpinnerAdapter(this, icons);
        spinnerIcon.setAdapter(iconAdapter);

        Log.d("SuaDanhMuc", "Spinner Icon Count: " + spinnerIcon.getCount());

        // Set listener for icon spinner
        spinnerIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIconColor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupColorSpinner() {
        List<ColorItem> colors = Arrays.asList(
                new ColorItem("#FFFFFF", "Trắng"),
                new ColorItem("#FF0000", "Đỏ"),
                new ColorItem("#00FF00", "Xanh lá"),
                new ColorItem("#0000FF", "Xanh dương"),
                new ColorItem("#FFFF00", "Vàng"),
                new ColorItem("#FFA500", "Cam"),
                new ColorItem("#800080", "Tím"),
                new ColorItem("#00FFFF", "Xanh lam"),
                new ColorItem("#FFC0CB", "Hồng"),
                new ColorItem("#808080", "Xám"),
                new ColorItem("#000000", "Đen"),
                new ColorItem("#A52A2A", "Nâu"),
                new ColorItem("#8B4513", "Nâu yên ngựa"),
                new ColorItem("#D2691E", "Sô cô la"),
                new ColorItem("#2E8B57", "Xanh biển"),
                new ColorItem("#4682B4", "Xanh thép"),
                new ColorItem("#DAA520", "Vàng nâu"),
                new ColorItem("#ADFF2F", "Xanh vàng"),
                new ColorItem("#32CD32", "Xanh chanh"),
                new ColorItem("#FFD700", "Vàng kim"),
                new ColorItem("#DC143C", "Đỏ thẫm"),
                new ColorItem("#B22222", "Đỏ gạch"),
                new ColorItem("#E9967A", "Hồng đậm"),
                new ColorItem("#FF4500", "Đỏ cam"),
                new ColorItem("#2F4F4F", "Xám đen"),
                new ColorItem("#008080", "Mòng két"),
                new ColorItem("#40E0D0", "Ngọc lam"),
                new ColorItem("#EE82EE", "Tím violet"),
                new ColorItem("#F5DEB3", "Lúa mì"),
                new ColorItem("#9ACD32", "Xanh vàng nhạt"),
                new ColorItem("#5F9EA0", "Xanh lính"),
                new ColorItem("#7FFF00", "Xanh nõn chuối"),
                new ColorItem("#DCDCDC", "Xám nhạt"),
                new ColorItem("#FFE4B5", "Màu mocca"),
                new ColorItem("#7B68EE", "Màu xanh đậm"),
                new ColorItem("#8B0000", "Đỏ tối"),
                new ColorItem("#00BFFF", "Xanh đậm"),
                new ColorItem("#9400D3", "Tím đậm"),
                new ColorItem("#8B008B", "Tím sẫm")
        );
        ColorSpinnerAdapter colorAdapter = new ColorSpinnerAdapter(this, colors);
        spinnerColor.setAdapter(colorAdapter);

        // Set listener for color spinner
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIconColor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateIconColor() {
        IconItem selectedIcon = (IconItem) spinnerIcon.getSelectedItem();
        ColorItem selectedColor = (ColorItem) spinnerColor.getSelectedItem();
        iconPreview.setImageResource(selectedIcon.getIconResId());
        iconPreview.setColorFilter(Color.parseColor(selectedColor.getColorHex()));
    }

    private void saveCategory() {
        String categoryName = txtCategoryName.getText().toString().trim();
        IconItem selectedIcon = (IconItem) spinnerIcon.getSelectedItem();
        ColorItem selectedColor = (ColorItem) spinnerColor.getSelectedItem();

        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        CategoriesModel category = new CategoriesModel();
        category.setCategoryName(categoryName);
        category.setIconName(getResources().getResourceEntryName(selectedIcon.getIconResId()));
        category.setColor(selectedColor.getColorHex());
        category.setType(currentType);
        category.setUserId(userId);

        // Lưu danh mục vào cơ sở dữ liệu
        ExpenseDB dbHelper = new ExpenseDB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("CategoryName", category.getCategoryName());
        values.put("Color", category.getColor());
        values.put("IconName", category.getIconName());
        values.put("Type", category.getType());
        values.put("UserID", category.getUserId());

        long newRowId = db.insert("Categories", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        }

        db.close();
    }


    private void deleteCategory() {
        if (categoryId == -1) {
            Toast.makeText(this, "Category does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        ExpenseDB dbHelper = new ExpenseDB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("Categories", "CategoryID=?", new String[]{String.valueOf(categoryId)});

        db.close();

        Toast.makeText(this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
