package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartMonthly extends Fragment {

    private LinearLayout layoutChiTieu;
    private LinearLayout layoutThuNhap;

    private EditText editTextDate;
    private final Calendar calendar = Calendar.getInstance();


    public ReportChartMonthly() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_chart_monthly, container, false);

        layoutChiTieu = view.findViewById(R.id.layout_chi_tieu);
        layoutThuNhap = view.findViewById(R.id.layout_thu_nhap);
        addController(view);

        // Xử lý sự kiện khi chọn Chi tiêu
        layoutChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChiTieu.setSelected(true);
                layoutThuNhap.setSelected(false);
                Toast.makeText(getContext(), "Đã chọn Chi tiêu", Toast.LENGTH_SHORT).show();
                // Xử lý logic khi chọn Chi tiêu
            }
        });

        // Xử lý sự kiện khi chọn Thu nhập
        layoutThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChiTieu.setSelected(false);
                layoutThuNhap.setSelected(true);
                Toast.makeText(getContext(), "Đã chọn Thu nhập", Toast.LENGTH_SHORT).show();
                // Xử lý logic khi chọn Thu nhập
            }
        });

        loadEditTextDate();
        editTextDate.setOnClickListener(v -> showMonthYearPickerDialog());

        return view;
    }

    private void loadEditTextDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");;
        String currentDate = dateFormat.format(calendar.getTime());
        editTextDate.setText(currentDate);
    }

    private void showMonthYearPickerDialog() {
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_year_picker, null);
        final NumberPicker monthYearPicker = view.findViewById(R.id.numberPicker);

        List<String> monthYearList = new ArrayList<>();
        for (int year = 2000; year <= currentYear + 50; year++) {
            for (int month = 1; month <= 12; month++) {
                monthYearList.add(String.format("%02d/%d", month, year));
            }
        }
        String[] monthYearArray = new String[monthYearList.size()];
        monthYearList.toArray(monthYearArray);

        monthYearPicker.setMinValue(0);
        monthYearPicker.setMaxValue(monthYearList.size() - 1);
        monthYearPicker.setDisplayedValues(monthYearArray);

        int initialValue = (currentYear - 1900) * 12 + (currentMonth - 1);
        monthYearPicker.setValue(initialValue);

        builder.setView(view);
        builder.setTitle("Chọn tháng và năm");
        builder.setPositiveButton("Chọn", (dialog, which) -> {
            String selectedMonthYear = monthYearArray[monthYearPicker.getValue()];
            editTextDate.setText(selectedMonthYear);
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
    private void addController(View view){
        editTextDate = view.findViewById(R.id.editTextDate);
    }
}
