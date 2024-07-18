package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_vinh;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InYear_Tong#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InYear_Tong extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    BarChart barChart;
    TextView totalYear,averageYear, chiTieuYear, thuNhapYear;

    public InYear_Tong() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InYear_Tong.
     */
    // TODO: Rename and change types and number of parameters
    public static InYear_Tong newInstance(String param1, String param2) {
        InYear_Tong fragment = new InYear_Tong();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_year__tong, container, false);
        barChart = view.findViewById(R.id.barChart);
        totalYear = view.findViewById(R.id.totalYear);
        averageYear = view.findViewById(R.id.averageYear);
        thuNhapYear = view.findViewById(R.id.thuNhapYear);
        chiTieuYear = view.findViewById(R.id.chiTieuYear);

        showYearPickerDialog(view);

        return view;
    }
    void showYearPickerDialog(View view){
        Spinner yearSpinner = view.findViewById(R.id.yearSpinner);
        ArrayList<String> years = new ArrayList<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear + 50; i >= 2000; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setSelection(adapter.getPosition(String.valueOf(currentYear)));
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                updateData(selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateData(int year) {
        IncomeExpenseModel_vinh model = new IncomeExpenseModel_vinh(getContext());

        HashMap<String, Integer> monthlyExpenses = model.getMonthlyExpenses(year);
        HashMap<String, Integer> monthlyIncome = model.getMonthlyIncome(year);
        HashMap<String, Integer> yearlySummary = model.getYearlySummary(year);

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            float expense = monthlyExpenses.getOrDefault("Tháng " + i, 0);
            float income = monthlyIncome.getOrDefault("Tháng " + i, 0);
            float totalM = income + expense;
            entries.add(new BarEntry(i, new float[]{totalM}));
        }

        BarDataSet set = new BarDataSet(entries, "Tổng thu nhập và chi tiêu");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setStackLabels(new String[]{"Total"});

        BarData data = new BarData(set);
        barChart.setData(data);
        barChart.invalidate();

        int totalIncome = yearlySummary.get("TotalIncome");
        int totalExpense = yearlySummary.get("TotalExpense");
        int total = totalIncome + totalExpense;
        int average = total / 12;

        thuNhapYear.setText(String.valueOf(totalIncome));
        chiTieuYear.setText(String.valueOf(totalExpense));
        totalYear.setText(String.valueOf(total));
        averageYear.setText(String.valueOf(average));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int monthIndex = (int) value;
                if (monthIndex >= 1 && monthIndex <= 12) {
                    return "Tháng " + monthIndex;
                }
                return "";
            }
        });
        xAxis.setTextColor(Color.RED); // Thay đổi màu chữ của các nhãn trên trục X

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.0f VNĐ", value); // Format currency as needed
            }
        });
        leftAxis.setTextColor(Color.GREEN); // Thay đổi màu chữ của các nhãn trên trục Y bên trái

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setTextColor(Color.BLUE);  // Thay đổi màu chữ của các nhãn trên trục Y bên phải (nếu có)
    }
}