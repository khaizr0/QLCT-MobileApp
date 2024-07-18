package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ColorSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.IconSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.ColorItem;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IconItem;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ThemDanhMuc extends AppCompatActivity {

    private Spinner spinnerIcon;
    private Spinner spinnerColor;
    private ImageView iconPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);

        ImageView icBack = findViewById(R.id.btn_back);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerIcon = findViewById(R.id.spinner_icon);
        spinnerColor = findViewById(R.id.spinner_color);
        iconPreview = findViewById(R.id.icon_preview);

        //icon spinner
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

        //color spinner
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
                new ColorItem("#FFE4B5", "Da bò"),
                new ColorItem("#FFDEAD", "Trắng navajo"),
                new ColorItem("#FFDAB9", "Hồng đào"),
                new ColorItem("#FFE4E1", "Hồng nhạt"),
                new ColorItem("#FFFACD", "Vàng chanh"),
                new ColorItem("#F0E68C", "Vàng khaki")
        );

        ColorSpinnerAdapter colorAdapter = new ColorSpinnerAdapter(this, colors);
        spinnerColor.setAdapter(colorAdapter);

        // Set listeners for spinners
        spinnerIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIconColor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        // Update the iconPreview with the selected icon and color
        iconPreview.setImageResource(selectedIcon.getIconResId());
        iconPreview.setColorFilter(Color.parseColor(selectedColor.getColorHex()));
    }
}