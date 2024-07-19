package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ReportChartMonthListViewAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.ExpenseItem;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_nguyen;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartMonthly extends Fragment {

    private LinearLayout layoutChiTieu;
    private LinearLayout layoutThuNhap;
    private EditText editTextDate;
    private TextView txtTotalIncome, txtTotalExpense, txtTotalIncomeExpense;
    private PieChart pieChart;

    private final Calendar calendar = Calendar.getInstance();
    private IncomeExpenseModel_nguyen incomeExpenseModel;
    private CategoriesModel categoriesModel;
    private UserModel session;
    ListView expenseListView ;

    private int type = 0;
    private int month;
    private int year;

    public ReportChartMonthly() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        incomeExpenseModel = new IncomeExpenseModel_nguyen(context);
        categoriesModel = new CategoriesModel(context);
        session = UserModel.getSessionUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_chart_monthly, container, false);

        layoutChiTieu = view.findViewById(R.id.layout_chi_tieu);
        layoutThuNhap = view.findViewById(R.id.layout_thu_nhap);
        editTextDate = view.findViewById(R.id.editTextDate);
        txtTotalIncome = view.findViewById(R.id.txtTotalIncome);
        txtTotalExpense = view.findViewById(R.id.txtTotalExpense);
        txtTotalIncomeExpense = view.findViewById(R.id.txtTotalIncomeExpense);
        expenseListView = view.findViewById(R.id.expenseListView); // Khởi tạo expenseListView

        pieChart = view.findViewById(R.id.pie_chart);

        int userID = session.getUserId();

        layoutChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.selectedColor));
                layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
                Toast.makeText(getContext(), "Đã chọn Chi tiêu", Toast.LENGTH_SHORT).show();

                type = 0;

                loadTxtView();
            }
        });

        layoutThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutThuNhap.setBackgroundColor(getResources().getColor(R.color.selectedColor));
                layoutChiTieu.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
                Toast.makeText(getContext(), "Đã chọn Thu nhập", Toast.LENGTH_SHORT).show();

                type = 1;

                loadTxtView();
            }
        });

        layoutChiTieu.performClick();

        loadTxtView();
        loadEditTextDate();
        loadPieChart(userID, month, year);
        editTextDate.setOnClickListener(v -> showMonthYearPickerDialog());

        return view;
    }


    private void loadTxtView() {
        int userID = session.getUserId();

        extractMonthYear(); // Lấy month và year từ editTextDate

        String totalIncome = incomeExpenseModel.getSumIncomeForUser(userID, month, year);
        String totalExpense = incomeExpenseModel.getSumExpenseForUser(userID, month, year);

        // Check if the returned values are null or empty, if so, set them to "0"
        if (totalIncome == null || totalIncome.isEmpty()) {
            totalIncome = "0";
        }
        if (totalExpense == null || totalExpense.isEmpty()) {
            totalExpense = "0";
        }

        int totalIncomeValue = Integer.parseInt(totalIncome);
        int totalExpenseValue = Integer.parseInt(totalExpense);
        int totalIncomeExpense = totalIncomeValue - totalExpenseValue;

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());

        txtTotalIncome.setText(numberFormat.format(totalIncomeValue));
        txtTotalExpense.setText(numberFormat.format(totalExpenseValue));
        txtTotalIncomeExpense.setText(numberFormat.format(totalIncomeExpense));

        loadPieChart(userID, month, year);
        loadExpenseListView(userID, month, year, type);
    }


    private void loadEditTextDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
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

        int initialValue = (currentYear - 2000) * 12 + (currentMonth - 1);
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

    private void loadPieChart(int userID, int month, int year) {
        List<PieEntry> entries = new ArrayList<>();
        List<String> categories = categoriesModel.getExpenseOrIncomeCategoriesForUser(userID, month, year, type);
        List<Integer> amounts = incomeExpenseModel.getExpenseOrIncomeAmountsForUser(userID, month, year, type);

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


    private void extractMonthYear() {
        String dateStr = editTextDate.getText().toString();
        String[] parts = dateStr.split("/");
        if (parts.length == 2) {
            month = Integer.parseInt(parts[0]);
            year = Integer.parseInt(parts[1]);
        }
    }
    private void loadExpenseListView(int userID, int month, int year, int type) {
        List<Map<String, String>> categories = categoriesModel.getExpenseOrIncomeCategoriesForUser_ReportChartMonth(userID, month, year, type);
        List<Integer> amounts = incomeExpenseModel.getExpenseOrIncomeAmountsForUser(userID, month, year, type);

        List<ExpenseItem> expenseItems = new ArrayList<>();
        Map<String, CategoriesModel> categoryMap = new HashMap<>(); // Create a map for CategoriesModel

        for (int i = 0; i < categories.size(); i++) {
            String categoryName = categories.get(i).get("categoryName");
            String iconName = categories.get(i).get("iconName");
            int amount = amounts.get(i);

            // Fetch the CategoriesModel object
            CategoriesModel category = categoriesModel.getCategoryByName(categoryName);
            if (category != null) {
                categoryMap.put(categoryName, category);
            }

            ExpenseItem item = new ExpenseItem(categoryName, String.valueOf(amount), iconName);
            expenseItems.add(item);
        }

        ReportChartMonthListViewAdapter adapter = new ReportChartMonthListViewAdapter(getContext(), expenseItems, categoryMap);
        expenseListView.setAdapter(adapter);
    }


}
