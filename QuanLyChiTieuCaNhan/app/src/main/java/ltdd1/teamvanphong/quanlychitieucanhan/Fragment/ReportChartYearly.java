package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartYearly extends Fragment {

    EditText editTextDate;
    LinearLayout layoutChiTieu;
    LinearLayout layoutThuNhap;

    public ReportChartYearly() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_chart_yearly, container, false);

        editTextDate = view.findViewById(R.id.editTextDate);
        layoutChiTieu = view.findViewById(R.id.layout_chi_tieu);
        layoutThuNhap = view.findViewById(R.id.layout_thu_nhap);

        loadEditTextDate();

        editTextDate.setOnClickListener(v -> showYearPickerDialog());

        layoutChiTieu.setOnClickListener(v -> {
            layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.selectedColor));
            layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
            Toast.makeText(getContext(), "Đã chọn Chi tiêu", Toast.LENGTH_SHORT).show();
            // Xử lý logic khi chọn Chi tiêu
        });

        layoutThuNhap.setOnClickListener(v -> {
            layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.selectedColor));
            layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
            Toast.makeText(getContext(), "Đã chọn Thu nhập", Toast.LENGTH_SHORT).show();
            // Xử lý logic khi chọn Thu nhập
        });

        return view;
    }

    void loadEditTextDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        editTextDate.setText(currentDate);
    }

    private void showYearPickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_year_picker, null);
        final NumberPicker numberPicker = view.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1900);
        numberPicker.setMaxValue(2100);
        numberPicker.setValue(currentYear);
        numberPicker.setWrapSelectorWheel(false);

        builder.setView(view);
        builder.setTitle("Chọn năm");
        builder.setPositiveButton("Chọn", (dialog, which) -> {
            int selectedYear = numberPicker.getValue();
            editTextDate.setText(String.valueOf(selectedYear));
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
