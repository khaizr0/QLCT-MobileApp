package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.CalendarAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.ListViewPageCalendarAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CalendarDay;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_nguyen;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class CalendarFragment extends Fragment {

    private TextView txtIncome, txtExpense, txtTotal ;
    private Spinner monthSpinner;
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;
    private Calendar calendar = Calendar.getInstance();
    private int currentYear = calendar.get(Calendar.YEAR);
    private int currentMonth = calendar.get(Calendar.MONTH) + 1;
    private ListView listView;

    private UserModel session = UserModel.getSessionUser();
    private int userId = session.getUserId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        monthSpinner = view.findViewById(R.id.monthSpinner);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        txtIncome = view.findViewById(R.id.txtImcome);
        txtExpense = view.findViewById(R.id.txtExpense);
        txtTotal = view.findViewById(R.id.txtTotal);
        listView = view.findViewById(R.id.listview);

        setupMonthSpinner();
        setupCalendarRecyclerView();

        updateCalendar();
        updateListView();

        return view;
    }

    private void setupMonthSpinner() {

        List<String> dateOptions = new ArrayList<>();
        for (int year = 2000; year <= currentYear + 50; year++) {
            for (int month = 1; month <= 12; month++) {
                dateOptions.add(String.format("%02d/%04d", month , year));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dateOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        String currentMonthYear = String.format("%02d/%04d", currentMonth , currentYear);
        int currentIndex = dateOptions.indexOf(currentMonthYear);
        if (currentIndex != -1) {
            monthSpinner.setSelection(currentIndex);
        }
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupCalendarRecyclerView() {
        calendarAdapter = new CalendarAdapter(new ArrayList<>());
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void updateCalendar() {
        String selectedDate = (String) monthSpinner.getSelectedItem();
        String[] parts = selectedDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        IncomeExpenseModel_nguyen model = new IncomeExpenseModel_nguyen(requireContext());

        List<IncomeExpenseModel_nguyen> incomeExpenseList = model.getIncomeExpensesByMonth(userId, month, year);

        Map<String, double[]> dailyIncomeExpenseMap = new HashMap<>();
        for (IncomeExpenseModel_nguyen item : incomeExpenseList) {
            String date = item.getDate();
            double amount = Double.parseDouble(item.getAmount());
            if (!dailyIncomeExpenseMap.containsKey(date)) {
                dailyIncomeExpenseMap.put(date, new double[]{0, 0});
            }
            if (item.getType() == 1) {
                dailyIncomeExpenseMap.get(date)[0] += amount;
            } else {
                dailyIncomeExpenseMap.get(date)[1] += amount;
            }
        }

        List<CalendarDay> days = new ArrayList<>();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add(new CalendarDay("", "", ""));
        }
        for (int i = 1; i <= daysInMonth; i++) {
            String date = String.format("%04d-%02d-%02d", year, month, i);
            String income = dailyIncomeExpenseMap.containsKey(date) && dailyIncomeExpenseMap.get(date)[0] != 0 ?
                    String.format("%.0f", dailyIncomeExpenseMap.get(date)[0]) : "";
            String expense = dailyIncomeExpenseMap.containsKey(date) && dailyIncomeExpenseMap.get(date)[1] != 0 ?
                    String.format("%.0f", dailyIncomeExpenseMap.get(date)[1]) : "";
            days.add(new CalendarDay(String.valueOf(i), income, expense));
        }

        double totalIncome = 0;
        double totalExpense = 0;
        for (double[] values : dailyIncomeExpenseMap.values()) {
            totalIncome += values[0];
            totalExpense += values[1];
        }

        calendarAdapter.setDays(days);

        txtIncome.setText(String.format("%.0fđ", totalIncome));
        txtExpense.setText(String.format("%.0fđ", totalExpense));
        txtTotal.setText(String.format("%.0fđ", totalIncome - totalExpense));
    }


    private void updateListView() {
        String selectedDate = (String) monthSpinner.getSelectedItem();
        String[] parts = selectedDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        IncomeExpenseModel_nguyen model = new IncomeExpenseModel_nguyen(requireContext());

        List<IncomeExpenseModel_nguyen> incomeExpenseList = model.getIncomeExpensesByMonth(userId, month, year);

        Map<Integer, CategoriesModel> categoryMap = new HashMap<>();
        List<CategoriesModel> categories = CategoriesModel.getCategoriesByTypeAndUserId(requireContext(), 0);
        categories.addAll(CategoriesModel.getCategoriesByTypeAndUserId(requireContext(), 1));
        for (CategoriesModel category : categories) {
            categoryMap.put(category.getCategoryId(), category);
        }

        ListViewPageCalendarAdapter adapter = new ListViewPageCalendarAdapter(requireContext(), incomeExpenseList, categoryMap);
        listView.setAdapter(adapter);
    }

}
