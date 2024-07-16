package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ColorSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.IconSpinnerAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ThemDanhMuc extends AppCompatActivity {

    private Spinner spinnerIcon;
    private Spinner spinnerColor;
    private ImageView iconPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);

        spinnerIcon = findViewById(R.id.spinner_icon);
        spinnerColor = findViewById(R.id.spinner_color);
        iconPreview = findViewById(R.id.icon_preview);

        // Populate icon spinner
        List<IconItem> icons = Arrays.asList(
                new IconItem(R.drawable.baseline_add_box_24, "Icon 1"),
                new IconItem(R.drawable.baseline_calendar_month_24, "Icon 2"),
                new IconItem(R.drawable.baseline_currency_exchange_24, "Icon 3")
        );
        IconSpinnerAdapter iconAdapter = new IconSpinnerAdapter(this, icons);
        spinnerIcon.setAdapter(iconAdapter);

        // Populate color spinner
        List<ColorItem> colors = Arrays.asList(
                new ColorItem("#FFFFFF", "White"),
                new ColorItem("#FF0000", "Red"),
                new ColorItem("#00FF00", "Green"),
                new ColorItem("#0000FF", "Blue")
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