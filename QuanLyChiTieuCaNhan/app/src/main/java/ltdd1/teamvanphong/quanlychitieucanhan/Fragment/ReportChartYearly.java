package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartYearly extends Fragment {
    EditText editTextDate;

    public ReportChartYearly() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_chart_yearly, container, false);

        addController(view);
        loadEditTextDate();

        editTextDate.setOnClickListener(view1 -> showYearPickerDialog());
        return view;
    }

    void loadEditTextDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");;
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

    void addController(View view){
        editTextDate = view.findViewById(R.id.editTextDate);
    }
}