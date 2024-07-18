package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
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

import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_vinh;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class Other_report_balance extends Fragment {

    BarChart barChart;
    TextView[] monthTextViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_report_balance, container, false);

        // Initialize the toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);  // Remove default title

        barChart = view.findViewById(R.id.barChart);

        monthTextViews = new TextView[]{
                view.findViewById(R.id.thang1_sodu),
                view.findViewById(R.id.thang2_sodu),
                view.findViewById(R.id.thang3_sodu),
                view.findViewById(R.id.thang4_sodu),
                view.findViewById(R.id.thang5_sodu),
                view.findViewById(R.id.thang6_sodu),
                view.findViewById(R.id.thang7_sodu),
                view.findViewById(R.id.thang8_sodu),
                view.findViewById(R.id.thang9_sodu),
                view.findViewById(R.id.thang10_sodu),
                view.findViewById(R.id.thang11_sodu),
                view.findViewById(R.id.thang12_sodu)
        };

        // Set click listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // Set the custom title
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Báo cáo thay đổi số dư");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            float expense = monthlyExpenses.getOrDefault("Tháng " + i, 0);
            float income = monthlyIncome.getOrDefault("Tháng " + i, 0);
            float balance = income - expense;
            entries.add(new BarEntry(i, new float[]{balance}));
            monthTextViews[i-1].setText(String.valueOf(balance));
        }

        BarDataSet set = new BarDataSet(entries, "Số dư");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setStackLabels(new String[]{"Số dư"});

        BarData data = new BarData(set);
        barChart.setData(data);
        barChart.invalidate();

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