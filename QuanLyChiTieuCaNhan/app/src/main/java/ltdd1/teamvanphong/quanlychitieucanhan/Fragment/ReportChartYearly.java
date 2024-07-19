package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ExpenseListAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_nguyen;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartYearly extends Fragment {

    private EditText editTextDate;
    private LinearLayout layoutChiTieu;
    private LinearLayout layoutThuNhap;
    private PieChart pieChart;
    private ListView listViewExpenses;

    private final Calendar calendar = Calendar.getInstance();
    private IncomeExpenseModel_nguyen incomeExpenseModel;
    private CategoriesModel categoriesModel;
    private UserModel session;

    private int type = 0;
    private int year;

    public ReportChartYearly() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        incomeExpenseModel = new IncomeExpenseModel_nguyen(context);
        categoriesModel = new CategoriesModel(context);
        session = UserModel.getSessionUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_chart_yearly, container, false);

        editTextDate = view.findViewById(R.id.editTextDate);
        layoutChiTieu = view.findViewById(R.id.layout_chi_tieu);
        layoutThuNhap = view.findViewById(R.id.layout_thu_nhap);
        pieChart = view.findViewById(R.id.pie_chart);
        listViewExpenses = view.findViewById(R.id.listViewExpenses);

        layoutChiTieu.setOnClickListener(v -> {
            layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.selectedColor));
            layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
            Toast.makeText(getContext(), "Đã chọn Chi tiêu", Toast.LENGTH_SHORT).show();

            type = 0;
            loadChartData();
        });

        layoutThuNhap.setOnClickListener(v -> {
            layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.selectedColor));
            layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
            Toast.makeText(getContext(), "Đã chọn Thu nhập", Toast.LENGTH_SHORT).show();

            type = 1;
            loadChartData();
        });

        loadEditTextDate();
        editTextDate.setOnClickListener(v -> showYearPickerDialog());
        loadChartData();

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
            loadChartData(); // Load data when year is selected
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void loadChartData() {
        String yearString = editTextDate.getText().toString();

        if (yearString.isEmpty()) {
            Toast.makeText(getContext(), "Year is not set", Toast.LENGTH_SHORT).show();
            return;
        }

        int userID = session.getUserId();
        year = Integer.parseInt(yearString);

        List<PieEntry> entries = new ArrayList<>();
        List<String> categories = categoriesModel.getExpenseOrIncomeCategoriesForUser(userID,0, year, type);
        List<Integer> amounts = incomeExpenseModel.getExpenseOrIncomeAmountsForUser(userID, 0,year, type);

        for (int i = 0; i < categories.size(); i++) {
            entries.add(new PieEntry(amounts.get(i), categories.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, type == 0 ? "Chi tiêu" : "Thu nhập");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);
        dataSet.setValueFormatter(new PercentFormatter(pieChart));

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate(); // refresh

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
    }
//    private void loadListData() {
//        List<IncomeExpenseModel_nguyen> list = incomeExpenseModel.getIncomeExpensesByMonth(session.getUserId(), calendar.get(Calendar.YEAR), type);
//        Map<Integer, CategoriesModel> categoryMap = categoriesModel.getAllCategoriesAsMap();
//        ExpenseListAdapter adapter = new ExpenseListAdapter(getContext(), list, categoryMap);
//        listViewExpenses.setAdapter(adapter);
//    }
}
